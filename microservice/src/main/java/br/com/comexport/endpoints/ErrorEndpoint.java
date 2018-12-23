package br.com.comexport.endpoints;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorEndpoint implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String error() {
        return "";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}

