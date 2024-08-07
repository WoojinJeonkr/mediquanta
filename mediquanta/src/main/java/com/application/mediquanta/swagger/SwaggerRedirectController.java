package com.application.mediquanta.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/swagger-access")
    public String redirectToSwagger() {
        return "redirect:/mediquanta/swagger-ui";
    }
}
