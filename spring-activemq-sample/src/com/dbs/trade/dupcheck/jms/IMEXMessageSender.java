package com.dbs.trade.dupcheck.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * The TestMessageSender class uses the injected JMSTemplate to send a message
 * to a specified Queue. In our case we're sending messages to 'TestQueueTwo'
 */
@Service
public class IMEXMessageSender {
	private JmsTemplate jmsTemplate;
	private Queue imexSenderQ;
	private static final Logger logger = Logger.getLogger(IMEXMessageSender.class);

	/**
	 * Sends message using JMS Template.
	 *
	 * @param message
	 *            the message_p
	 * @throws JMSException
	 *             the jMS exception
	 */
	public void sendMessage(String message) throws JMSException {
		logger.debug("About to put message on queue. Queue[" + imexSenderQ.toString() + "] Message[" + message + "]");
		logger.info("About to put message on IMEX Send queue");
		jmsTemplate.convertAndSend(imexSenderQ, message);
		System.out.println("Sent message" + message);
	}

	/**
	 * Sets the jms template.
	 *
	 * @param template
	 *            the jms template
	 */
	public void setJmsTemplate(JmsTemplate tmpl) {
		this.jmsTemplate = tmpl;
	}

	/**
	 * Sets the test queue.
	 *
	 * @param queue
	 *            the new test queue
	 */
	public void setImexSenderQ(Queue queue) {
		this.imexSenderQ = queue;
	}
}