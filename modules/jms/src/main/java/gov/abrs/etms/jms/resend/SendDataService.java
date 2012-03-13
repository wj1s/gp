package gov.abrs.etms.jms.resend;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendDataService {

	private static final Log logger = LogFactory.getLog(SendDataService.class);

	private String brokerUrl = "";
	private String mqRept = "";
	private String mqAccd = "";
	private String mqTechAccd = "";
	private String mqTechRept = "";

	public String getMqTechRept() {
		return mqTechRept;
	}

	public void setMqTechRept(String mqTechRept) {
		this.mqTechRept = mqTechRept;
	}

	public String getMqTechAccd() {
		return mqTechAccd;
	}

	public void setMqTechAccd(String mqTechAccd) {
		this.mqTechAccd = mqTechAccd;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public void setMqRept(String mqRept) {
		this.mqRept = mqRept;
	}

	public void setMqAccd(String mqAccd) {
		this.mqAccd = mqAccd;
	}

	public void sendData(JSONObject obj, String type) throws Exception {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Queue queue;
		MessageProducer producer;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, brokerUrl);
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
			if (type.equalsIgnoreCase("ACCD")) {
				queue = new ActiveMQQueue(mqAccd);
			} else if (type.equalsIgnoreCase("REPT")) {
				queue = new ActiveMQQueue(mqRept);
			} else if (type.equalsIgnoreCase("ACCDTECH")) {
				queue = new ActiveMQQueue(mqTechAccd);
			} else {
				queue = null;
			}
			producer = session.createProducer(queue);
			TextMessage messages = session.createTextMessage(obj.toString());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			producer.send(messages);

			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {}
		}
	}

	//支持同时发送多个文件 fileList是文件列表 map中必须有fileName；fileName是完整绝对路径
	public void sendFileData(List<Map> fileList, String type) throws Exception {
		ActiveMQConnectionFactory connectionFactory;
		ActiveMQConnection connection = null;
		ActiveMQSession session;
		Destination queue;
		MessageProducer producer;
		String urlArr[] = brokerUrl.split("failover:");
		brokerUrl = urlArr.length > 1 ? urlArr[1] : brokerUrl;
		String tempIp = brokerUrl.substring(brokerUrl.indexOf("//"), brokerUrl.indexOf("?") - 6);
		brokerUrl = brokerUrl.substring(0, brokerUrl.indexOf("?")) + "?jms.blobTransferPolicy.defaultUploadUrl=http:"
				+ tempIp + ":8161/fileserver/";
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, brokerUrl);
		try {
			// 构造从工厂得到连接对象
			connection = (ActiveMQConnection) connectionFactory.createConnection();
			connection.setCopyMessageOnSend(false);
			// 获取操作连接
			session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			if (type.equalsIgnoreCase("REPTTECH")) {
				queue = session.createQueue("mqTechRept");
			} else if (type.equalsIgnoreCase("ACCDTECH")) {
				queue = new ActiveMQQueue(mqTechAccd);
			} else {
				queue = null;
			}
			producer = session.createProducer(queue);//创建生产者
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);//设置模式
			// 启动
			connection.start();
			Map map;
			for (int i = 0; i < fileList.size(); i++) {
				map = fileList.get(i);
				File file = new File(map.get("fileName") + "");
				BlobMessage blobMessage = session.createBlobMessage(file);//创建消息
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry) it.next();
					//System.out.println(pairs.getKey() + " = " + pairs.getValue());//例子
					if (!(pairs.getKey().toString()).equalsIgnoreCase("fileName")) {
						blobMessage.setStringProperty(pairs.getKey().toString(), pairs.getValue().toString()); //添加属性
					}
				}
				producer.send(blobMessage);//发送消息
			}
			//session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {}
		}
	}
}
