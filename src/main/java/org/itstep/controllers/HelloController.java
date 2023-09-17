package org.itstep.controllers;

import org.itstep.security.ListenerDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author Neil Alishev
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        System.out.println(listenerDetails.getListener());
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage() {

        return "admin";
    }
    @GetMapping("/admin/show")
    public String adminShow() {

        return "adminshow";
    }
}