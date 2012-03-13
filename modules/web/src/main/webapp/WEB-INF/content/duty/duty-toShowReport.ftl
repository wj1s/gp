<#assign report=JspTaglibs["/WEB-INF/runqian/runqianReport4.tld"] />
<html>
	<body>
		<@report.html name="report1" reportFileName="${reportFileName}"
					funcBarLocation="bottom" params="${runqianParams}" needPrint="yes"
					needOfflineInput="yes" needSaveAsExcel="yes"
					needImportExcel="yes" needSaveAsPdf="yes" generateParamForm="no"
					exceptionPage="${request.contextPath}/index/reportError.ftl" needPageMark="yes"
					pageMarkLabel="页号{currpage}/{totalPage}" displayNoLinkPageMark="yes" width="-1"
					firstPageLabel="<img src='${request.contextPath}/img/firstpage.gif' border=no style='vertical-align:middle'>" 
					prevPageLabel="<img src='${request.contextPath}/img/prevpage.gif' border=no style='vertical-align:middle'>" 
					nextPageLabel="<img src='${request.contextPath}/img/nextpage.gif' border=no style='vertical-align:middle'>" 
					lastPageLabel="<img src='${request.contextPath}/img/lastpage.gif' border=no style='vertical-align:middle'>" 
					printLabel="<img src='${request.contextPath}/img/rq_print.gif' border=no style='vertical-align:middle'>" 
					excelLabel="<img src='${request.contextPath}/img/excel.gif' border=no style='vertical-align:middle'>" 
					pdfLabel="<img src='${request.contextPath}/img/pdf.gif' border=no style='vertical-align:middle'>" />
	</body>
</html>
