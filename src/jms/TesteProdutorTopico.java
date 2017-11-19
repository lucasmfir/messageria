package jms;

import java.io.StringWriter;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;
import javax.jms.Message;
import br.com.caelum.modelo.*;

public class TesteProdutorTopico {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		InitialContext context = new InitialContext(); 

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection conn = factory.createConnection();
        conn.start();

        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Destination fila = (Destination) context.lookup("loja");
        MessageProducer producer = session.createProducer(fila);
        
        
        Pedido pedido = new PedidoFactory().geraPedidoComValores();
        Message msg = session.createObjectMessage(pedido);
        
        //StringWriter writer = new StringWriter();
        //JAXB.marshal(pedido, writer);
        //String xml = writer.toString();

        //Message msg = session.createTextMessage(xml);
        
        //Message msg = session.createTextMessage("teste_topicoS");
        //msg.setBooleanProperty("ebook", true);
        
        producer.send(msg); 

        session.close();
        conn.close();
        context.close();	

	}

}
