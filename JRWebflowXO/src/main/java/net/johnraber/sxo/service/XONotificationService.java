package net.johnraber.sxo.service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


// @TODO ensure service is a singleton and acts
//  as a pooled stateless EJB so constructor is
// similiar to the init() call in EJB lifecycle
// just managed by the Spring container 
@Service
public class XONotificationService // implements MessageDrivenBean
{
	@Autowired
	@Qualifier("hornetJmsCF")
   private ConnectionFactory connectionFactory;
   
	@Autowired
	@Qualifier("xoNotifyIn")
   private Destination queue;
   
   private Connection connection;
   
   public XONotificationService() throws JMSException
   {
//	      connection = connectionFactory.createConnection();
//	      connection.start();
   }
	 
	 
 public void finalize() throws JMSException {
	      connection.close();
	}
   
   private void initStuff() throws JMSException
   {
	   if( connection == null )
	   {
		   connection = connectionFactory.createConnection();
		   connection.start();
	   }
   }
   
   
   public void sendMessage(String text) throws JMSException {
      Session session = null;
      MessageProducer sender = null;
      
      try {
    	 this.initStuff();
         session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         sender = session.createProducer(queue);
         sender.setDeliveryMode(DeliveryMode.PERSISTENT);
 
         TextMessage response = session.createTextMessage(text);
         sender.send(response);
      } finally {
         try {
            if (sender != null) {
               sender.close();
            }
         } finally {
            if (session != null) {
               session.close();
            }
         }
      }
   }	 
}
