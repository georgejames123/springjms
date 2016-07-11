
package com.dbs.trade.dupcheck.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.dbs.trade.dupcheck.dataaccess.StudentJDBCTemplate;

/**
 * Class handles incoming messages
 *
 * @see PointOfIssueMessageEvent
 */
public class IMEXMessageListener implements MessageListener {

	private IMEXMessageSender imexMessageSender;
	private StudentJDBCTemplate studentJDBCTemplate;

	private static final Logger logger = Logger.getLogger(IMEXMessageListener.class);

	/**
	 * Method implements JMS onMessage and acts as the entry point for messages
	 * consumed by Springs DefaultMessageListenerContainer. When
	 * DefaultMessageListenerContainer picks a message from the queue it invokes
	 * this method with the message payload.
	 */
	public void onMessage(Message message) {
		logger.debug("Received message from queue [" + message + "]");

		/* The message must be of type TextMessage */
		if (message instanceof TextMessage) {
			try {
				String msgText = ((TextMessage) message).getText();
				logger.info("About to process IMEX message: " + msgText);

				/* call message sender to put message onto second queue */
				imexMessageSender.sendMessage(msgText);
				studentJDBCTemplate.create(msgText, 101);

			} catch (JMSException jmsEx_p) {
				String errMsg = "An error occurred extracting message";
				logger.error(errMsg, jmsEx_p);
			}
		} else {
			String errMsg = "Message is not of expected type TextMessage";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
	}

	/**
	 * Sets the message sender.
	 *
	 * @param messageSender
	 *            the new message sender
	 */
	public void setImexMessageSender(IMEXMessageSender messageSender) {
		this.imexMessageSender = messageSender;
	}

	public StudentJDBCTemplate getStudentJDBCTemplate() {
		return studentJDBCTemplate;
	}

	public void setStudentJDBCTemplate(StudentJDBCTemplate studentJDBCTemplate) {
		this.studentJDBCTemplate = studentJDBCTemplate;
	}
}
