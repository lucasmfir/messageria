package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.caelum.modelo.Pedido;

import javax.jms.Message;

public class TesteConsumidorTopicoComercial {

	public static void main(String[] args) throws Exception{
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
		// TODO Auto-generated method stub
		
		InitialContext context = new InitialContext(); 

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection conn = factory.createConnection();
        conn.setClientID("comercial");
        conn.start();

        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Topic topico = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assComercial");
        
        consumer.setMessageListener(new MessageListener(){
        	
        	public void onMessage(Message msg) {
        		
        		ObjectMessage objectMessage = (ObjectMessage)msg;
        		
        		try {
        			Pedido pedido = (Pedido)objectMessage.getObject();
                    System.out.println(pedido.getCodigo());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
        	}

        });
        
        
        new Scanner(System.in).nextLine(); //finaliza programa

        session.close();
        conn.close();
        context.close();
		

	}

}
