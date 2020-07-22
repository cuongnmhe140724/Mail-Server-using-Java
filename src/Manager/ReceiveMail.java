package Manager;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cuongnm
 */
public class ReceiveMail {
    
    private String mail;
    private String pass;

    public ReceiveMail() {
    }

    public ReceiveMail(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }
    

    public Message[] loadMail() {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", MailConfig.HOST_NAME);
        props.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", MailConfig.SSL_PORT);

        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, pass);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);

        // compose message
        try {
            Store st = session.getStore(MailConfig.PROVIDER);
            st.connect(MailConfig.HOST_NAME, mail, pass);
            Folder inbox = st.getFolder("INBOX");
            if (inbox == null) {
                System.out.println("No inbox");
                System.exit(1);
            }
            inbox.open(Folder.READ_ONLY);

            Message[] mess = inbox.getMessages();
            return mess;
        } catch (MessagingException e) {
        }
        return null;
    }
}
