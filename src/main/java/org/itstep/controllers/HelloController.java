package org.itstep.controllers;

import org.itstep.model.Album;
import org.itstep.security.ListenerDetails;

import org.itstep.services.AlbumService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * @author Neil Alishev
 */
@Controller
public class HelloController {
    private  final AlbumService albumService;

    public HelloController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/hello")
    public String sayHello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());
       // List<Album> albums = albumService.findAll();
        //  albumService.findByAlbumNameContainingIgnoreCase("rock");
          //  model.addAttribute("albums",albumService.findAll());

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