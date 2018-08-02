package uk.ac.sheffield.nlp.fever.annotation.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value="/auth")
public class AuthController {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void getStatus() {

    }

}
