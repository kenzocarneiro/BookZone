package fr.insacvl.asl.bcn.bookzone.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class Error404Controller implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "error/404";
    }

}

