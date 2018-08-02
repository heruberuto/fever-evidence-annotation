package uk.ac.sheffield.nlp.fever.annotation;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;


public class SMTPTest {

    public static void main(String args[]) throws Exception {

        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);

        email.setAuthenticator(new DefaultAuthenticator("j.thorne@sheffield.ac.uk", "d7arvfGy"));
        email.setTLS(true);

        email.setFrom("j.thorne@sheffield.ac.uk");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("james@jthorne.co.uk");
        email.send();

    }
}
