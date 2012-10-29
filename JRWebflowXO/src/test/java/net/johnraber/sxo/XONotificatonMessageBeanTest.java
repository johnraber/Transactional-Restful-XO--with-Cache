package net.johnraber.sxo;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.johnraber.sxo.service.XOServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.jboss.annotation.ejb.ResourceAdapter;
 


 
@MessageDriven(activationConfig = {
      @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/test_in") }) // Note the physical name of the queue
//Note: The @ResourceAdapter annotation is only required when the default messaging provider
//is not Apache Active MQ.
//@ResourceAdapter("activemq-rar-5.7.0.rar")
public class XONotificatonMessageBeanTest implements MessageListener {
 
	private static final Logger log = LoggerFactory.getLogger(XONotificatonMessageBeanTest.class);

//   @Resource(mappedName = "java:/activemq/ConnectionFactory")
   @Resource(mappedName = "java:/JmsXA")
   private ConnectionFactory connectionFactory;
//   @Resource(mappedName = "java:/queue/test_out") // Note the mapped name of the queue
//   private Destination queue;
   private Connection connection;
 
   public void init() throws JMSException {
      connection = connectionFactory.createConnection();
      connection.start();
   }
 
   public void destroy() throws JMSException {
      connection.close();
   }
 
 
//   private void sendMessage(String text) throws JMSException {
//      Session session = null;
//      MessageProducer sender = null;
//      try {
//         session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//         sender = session.createProducer(queue);
//         sender.setDeliveryMode(DeliveryMode.PERSISTENT);
// 
//         TextMessage response = session.createTextMessage(text);
//         sender.send(response);
//      } finally {
//         try {
//            if (sender != null) {
//               sender.close();
//            }
//         } finally {
//            if (session != null) {
//               session.close();
//            }
//         }
//      }
//   }
 
    @Override
   public void onMessage(Message message) {
      try {
         init();
         String text = ((TextMessage) message).getText();
//         sendMessage("Reply for '" + text + "'");
        log.info("Retrieving XO Session message from in queue: " + text  );
//		}
      } catch (JMSException e) {
         throw new EJBException("Error in JMS operation", e);
      }
      finally {
        try {
            destroy();
         } catch (JMSException e) {
            throw new EJBException("Error in closing connection", e);
         }
      }
   }
}
