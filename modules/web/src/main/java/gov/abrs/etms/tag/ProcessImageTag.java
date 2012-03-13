/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package gov.abrs.etms.tag;

import gov.abrs.etms.service.workflow.WorkFlowService;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ProcessImageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private long processInstanceId = -1;

	private byte[] gpdBytes = null;
	private byte[] imageBytes = null;
	private Token currentToken = null;
	private ProcessDefinition processDefinition = null;

	static String currentTokenColor = "#ff9900";
	static String childTokenColor = "blue";
	static String tokenNameColor = "blue";

	@Override
	public void release() {
		processInstanceId = -1;
		gpdBytes = null;
		imageBytes = null;
		currentToken = null;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			initialize();
			retrieveByteArrays();
			if (gpdBytes != null && imageBytes != null) {
				writeTable();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		}
		release();
		return EVAL_PAGE;
	}

	private void retrieveByteArrays() {
		try {
			FileDefinition fileDefinition = processDefinition.getFileDefinition();
			gpdBytes = fileDefinition.getBytes("gpd.xml");
			imageBytes = fileDefinition.getBytes("processimage.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeTable() throws IOException, DocumentException {

		int borderWidth = 0;
		Element rootDiagramElement = DocumentHelper.parseText(new String(gpdBytes)).getRootElement();
		int[] boxConstraint;
		int[] imageDimension = extractImageDimension(rootDiagramElement);
		String imageLink = "processimage?definitionId=" + processDefinition.getId();
		JspWriter jspOut = pageContext.getOut();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		String contextPath = hsr.getContextPath() + "/";
		imageLink = contextPath + imageLink;

		boxConstraint = extractBoxConstraint(rootDiagramElement);

		jspOut.println("<table border=0 cellspacing=0 cellpadding=0 width=" + imageDimension[0] + " height="
				+ imageDimension[1] + ">");

		jspOut.println("  <tr>");
		jspOut.println("    <td width=" + imageDimension[0] + " height=" + imageDimension[1]
				+ " style=\"background-image:url(" + imageLink + ")\" valign=top>");
		jspOut.println("      <table border=0 cellspacing=0 cellpadding=0>");
		jspOut.println("        <tr>");
		jspOut.println("          <td style=\"background-color:transparent;\">");
		jspOut.println("      <table border=0 cellspacing=0 cellpadding=0>");
		jspOut.println("        <tr>");
		jspOut.println("          <td width=" + (imageDimension[0] - 5)
				+ " style=\"background-color:transparent;text-align:right;text-valign:bottom\">");
		jspOut.println("        	<img src='" + contextPath + "img/star_level1.gif'/>表示任务流转的位置");
		jspOut.println("         </td>");
		jspOut.println("        </tr>");
		jspOut.println("      </table>");
		jspOut.println("         </td>");
		jspOut.println("        </tr>");
		jspOut.println("        <tr>");
		jspOut.println("          <td style=\"background-color:transparent;\">");
		jspOut.println("      <table border=0 cellspacing=0 cellpadding=0>");

		jspOut.println("        <tr>");
		jspOut.println("          <td width=" + (boxConstraint[0] - borderWidth) + " height="
				+ (boxConstraint[1] - borderWidth - 20) + " style=\"background-color:transparent;\">&nbsp;</td>");
		jspOut.println("        </tr>");

		jspOut.println("        <tr>");
		jspOut.println("          <td style=\"background-color:transparent;\">&nbsp;</td>");
		jspOut.println("          <td style=\"border-color:#ff9900; border-width:" + borderWidth
				+ "px; border-style:solid; background-color:transparent;\" width=" + boxConstraint[2] + " height="
				+ (boxConstraint[3] + (2 * borderWidth)) + "><img src='" + contextPath + "img/star_level1.gif'/></td>");
		jspOut.println("        </tr>");

		jspOut.println("      </table>");

		jspOut.println("         </td>");
		jspOut.println("        </tr>");
		jspOut.println("      </table>");

		jspOut.println("    </td>");
		jspOut.println("  </tr>");
		jspOut.println("</table>");
	}

	private int[] extractBoxConstraint(Element root) {
		int[] result = new int[4];
		try {
			String nodeName = currentToken.getNode().getName();
			XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
			Element node = (Element) xPath.selectSingleNode(root);
			result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
			result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
			result[2] = Integer.valueOf(node.attribute("width").getValue()).intValue();
			result[3] = Integer.valueOf(node.attribute("height").getValue()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private int[] extractBoxConstraint(Element root, Token token) {
		int[] result = new int[4];
		String nodeName = token.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element) xPath.selectSingleNode(root);
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue()).intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue()).intValue();
		return result;
	}

	private int[] extractImageDimension(Element root) {
		int[] result = new int[2];
		result[0] = Integer.valueOf(root.attribute("width").getValue()).intValue();
		result[1] = Integer.valueOf(root.attribute("height").getValue()).intValue();
		return result;
	}

	private void initialize() {
		WorkFlowService workFlowService = WebApplicationContextUtils.getWebApplicationContext(
				this.pageContext.getServletContext()).getBean(WorkFlowService.class);
		if (this.processInstanceId > 0) {
			ProcessInstance pi = workFlowService.getProcessInstance(processInstanceId + "");
			currentToken = pi.getRootToken();
			processDefinition = pi.getProcessDefinition();
		} else {
			throw new RuntimeException("现实流程图，必须传入的流程实例id");
		}

	}

	private void walkTokens(Token parent, List allTokens) {
		Map children = parent.getChildren();
		if (children != null && children.size() > 0) {
			Collection childTokens = children.values();
			for (Iterator iterator = childTokens.iterator(); iterator.hasNext();) {
				Token child = (Token) iterator.next();
				walkTokens(child, allTokens);
			}
		}

		allTokens.add(parent);
	}

	public void setTask(String id) {
		this.processInstanceId = new Long(id);
	}

}
