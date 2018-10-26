package uk.ac.sheffield.nlp.fever.annotation.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uk.ac.sheffield.nlp.fever.annotation.Config;
import uk.ac.sheffield.nlp.fever.annotation.auth.AuthException;
import uk.ac.sheffield.nlp.fever.annotation.auth.ReCAPTCHAv2;
import uk.ac.sheffield.nlp.fever.annotation.dao.AuthEmailDAO;
import uk.ac.sheffield.nlp.fever.annotation.dao.AuthEmailDAOImpl;
import uk.ac.sheffield.nlp.fever.annotation.dao.AuthEventDAO;
import uk.ac.sheffield.nlp.fever.annotation.dao.AuthTokenDAO;
import uk.ac.sheffield.nlp.fever.annotation.model.Annotator;
import uk.ac.sheffield.nlp.fever.annotation.model.AuthEmail;
import uk.ac.sheffield.nlp.fever.annotation.model.AuthEvent;
import uk.ac.sheffield.nlp.fever.annotation.model.AuthToken;
import uk.ac.sheffield.nlp.fever.annotation.service.SMTPService;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;


/*
@SuppressWarnings("unused")
@Controller
public class LoginController {

    @Autowired
    private AuthenticationController controller;
    @Autowired
    private SecurityConfig appConfig;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req) {
        logger.debug("Performing login");
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/callback";
        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
                .withAudience(String.format("https://%s/userinfo", appConfig.getDomain()))
                .build();
        return "redirect:" + authorizeUrl;
    }


}
*/


@Controller
public class LoginController {

    @Autowired
    SMTPService smtp;

    @Autowired
    private AuthTokenDAO tokens;

    @Autowired
    private AuthEmailDAO authEmail;

    @Autowired
    private AuthEventDAO authEvents;

    private final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";



    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String getLogin (final HttpServletRequest request){
        if(request.getSession(true).getAttribute("annotator") != null) {
            return "redirect:/login/success";
        }

        if("closed".equals(Config.getInstance().get("closed"))) {
            return "login/login";
        } else {
            return "closed";
        }
    }


    @RequestMapping(value="/login", method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postLogin(final HttpServletRequest request,
                            @RequestBody MultiValueMap<String, String> formParams) {

        if(request.getSession(true).getAttribute("annotator") != null) {
            return "redirect:/login/success";
        }

        if(!ReCAPTCHAv2.isCaptchaValid(formParams.getFirst("g-recaptcha-response"))) {
            //no captcha
            return "redirect:/login";
        }

        if(!formParams.toSingleValueMap().containsKey("email")) {
            //no email
            return "redirect:/login";
        } else if(!AuthEmailDAOImpl.isValidEmailAddress(formParams.toSingleValueMap().get("email"))) {
            //not email
            return "redirect:/login";
        }

        AuthEmail email = null;
        try {
            email = authEmail.getEmail(formParams.toSingleValueMap().get("email").trim());
        } catch (NoResultException notThere) {

            Annotator anno = new Annotator();
            anno.setAuth("_");
            anno.setCreatedDate(Instant.now());



            AuthEmail ae = new AuthEmail();
            ae.setCreatedDate(Instant.now());
            ae.setEmail(formParams.toSingleValueMap().get("email").trim());
            ae.setAnnotator(anno);

            authEmail.save(anno);
            authEmail.save(ae);

            email = ae;
        }


        AuthToken token = new AuthToken();
        token.setCreatedDate(Instant.now());
        token.setExpiresDate(Instant.now().plusSeconds(10*60));
        token.setEmail(email);
        token.setToken(RandomStringUtils.random( 100, CHARS));


        authEmail.save(token);


        try {
            smtp.send(token);
        } catch (EmailException e) {
            e.printStackTrace();
        }


        request.getSession(true).setAttribute("id",token.getId());


        return "login/sent";


    }


    @RequestMapping(value="/login/{token}", method= RequestMethod.GET)
    public String getLoginRequest(final HttpServletRequest req, @PathVariable(name="token") String token) {

        try {
            if(req.getSession().getAttribute("id") == null) {
                throw new AuthException("No session");
            }

            long id = (Long) req.getSession().getAttribute("id");

            AuthToken authToken = tokens.get(id);
            if(!authToken.getToken().equals(token)) {
                throw new AuthException("Mismatch");
            } else if(authToken.getExpiresDate().isBefore(Instant.now())) {
                throw new AuthException("Expired");
            } else if(!authToken.getEvents().isEmpty()) {
                throw new AuthException("Used");
            }


            req.getSession(true).setAttribute("annotator", authToken.getEmail().getAnnotator().getId());


            AuthEvent event = new AuthEvent();
            event.setCreatedDate(Instant.now());
            event.setToken(authToken);

            authEvents.save(event);




        } catch (NoResultException | AuthException notFound) {
            return "redirect:/login/failed";
        }

        return "redirect:/login/success";

    }


    @RequestMapping(value="/login/failed")
    public void getLoginFailed() {

    }

    @RequestMapping(value="/login/success")
    public void getLoginSuccess() {

    }



}