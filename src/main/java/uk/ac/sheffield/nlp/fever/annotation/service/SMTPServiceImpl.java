package uk.ac.sheffield.nlp.fever.annotation.service;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;
import uk.ac.sheffield.nlp.fever.annotation.Config;
import uk.ac.sheffield.nlp.fever.annotation.model.AuthToken;


@Service
public class SMTPServiceImpl implements SMTPService {

    public SMTPServiceImpl() {

    }


    @Override
    public void send(AuthToken token) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);

        email.setAuthenticator(new DefaultAuthenticator(
                Config.getInstance().get("auth.smtp.username"),
                Config.getInstance().get("auth.smtp.password")));
        email.setTLS(true);

        email.setFrom(Config.getInstance().get("auth.smtp.from"));
        email.setSubject("Login Link for FEVER Annotation System");
        email.setMsg("Hi,\n\n" +
                "Your sign in link for the FEVER Annotation System is: http://localhost:8080/login/" + token.getToken() +"" +
                "\n\nIt will expire in 10 minutes, must be used from the browser that you logged in from, and can only be used once." +
                "\n\nRegards\nThe FEVER Team");


        email.addTo(token.getEmail().getEmail());
        email.send();


    }

}
