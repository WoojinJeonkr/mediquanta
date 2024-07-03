package com.application.mediquanta.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

	@GetMapping
	public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            switch (statusCode) {
                case 401:
                    return "error/401";
                case 404:
                    return "error/404";
                case 500:
                    return "error/500";
                default:
                    return "error/404";
            }
        }
        return "error/404";
    }
	
}
