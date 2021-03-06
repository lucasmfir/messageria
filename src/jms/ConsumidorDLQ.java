package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.jms.Message;

public class ConsumidorDLQ {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		InitialContext context = new InitialContext(); 

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection conn = factory.createConnection();
        conn.start();

        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Destination fila = (Destination) context.lookup("DLQ");
        MessageConsumer consumer = session.createConsumer(fila);
        
        consumer.setMessageListener(new MessageListener(){
        	
        	public void onMessage(Message msg) {
        		System.out.println(msg);
        	}
        });
        
        
        new Scanner(System.in).nextLine(); //finaliza programa

        session.close();
        conn.close();
        context.close();
		

	}

}
