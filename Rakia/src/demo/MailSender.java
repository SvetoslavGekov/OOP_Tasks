package demo;
import java.io.File;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public final class MailSender {

	private MailSender() {
		
	}
	
	public static void sendEmail(String gmailSender,String senderPassword, String receiver, String content, File attachment) {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmailSender, senderPassword);
            }
          });


        try {
        	//Create message
            Message message = new MimeMessage(session);
            
            //Set From
            message.setFrom(new InternetAddress(gmailSender));
            
            //Set recepient
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(receiver));
            
            //Set subject and body
            message.setSubject("Dark Java Power");
            
            //Create a multipart object for the email
            Multipart multipart = new MimeMultipart();
            
            //Create a text body part and set the email content
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);
            
            //Add the text part to the email
            multipart.addBodyPart(messageBodyPart);
            
            //Check if the attached file is not null and add an attachment if necessary
            if(attachment != null) {
            	BodyPart fileBodyPart = new MimeBodyPart();
                DataSource  source = new FileDataSource(attachment);
                fileBodyPart.setDataHandler(new DataHandler(source));
                fileBodyPart.setFileName(attachment.getName());
                multipart.addBodyPart(fileBodyPart);
            }
            
            // Send the complete message parts
            message.setContent(multipart);
            
            Transport.send(message);

            System.out.println("Done");

		}
		catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
