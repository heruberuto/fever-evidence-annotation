package uk.ac.sheffield.nlp.fever.annotation.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PingController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void getStatus() {

    }

}
