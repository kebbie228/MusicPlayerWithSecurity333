package org.itstep.controllers;

import org.itstep.model.Album;
import org.itstep.model.Artist;
import org.itstep.model.ListenerAlbum;
import org.itstep.security.ListenerDetails;

import org.itstep.services.AlbumService;
import org.itstep.services.ArtistService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Neil Alishev
 */
@Controller
public class HelloController {
    private  final AlbumService albumService;
    private final ArtistService artistService;

    public HelloController(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping("/hello")
    public String sayHello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());
List<Album> albums= new ArrayList<>();
albums.add(albumService.findById(7));
albums.add(albumService.findById(9));
albums.add(albumService.findById(10));
albums.add(albumService.findById(11));
model.addAttribute("albumsHello", albums);
List<Artist> artists=new ArrayList<>();
artists.add(artistService.findById(3));
artists.add(artistService.findById(6));
artists.add(artistService.findById(7));
artists.add(artistService.findById(2));
model.addAttribute("artistsHello", artists);


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


    @GetMapping("/createPlaylist")
    public String newPlaylist( @ModelAttribute("album") Album album,@ModelAttribute("listenerAlbum") ListenerAlbum listenerAlbum){
        return "listener/newPlaylist";
    }
}