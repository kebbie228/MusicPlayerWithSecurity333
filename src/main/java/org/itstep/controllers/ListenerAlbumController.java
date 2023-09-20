package org.itstep.controllers;


import org.itstep.model.ListenerAlbum;
import org.itstep.model.ListenerSong;
import org.itstep.security.ListenerDetails;
import org.itstep.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/listenerAlbums")
public class ListenerAlbumController {
    private final ListenerAlbumService listenerAlbumService;
    private final ListenerService listenerService;
    private final AlbumService albumService;

    public ListenerAlbumController(ListenerAlbumService listenerAlbumService, ListenerService listenerService, AlbumService albumService) {
        this.listenerAlbumService = listenerAlbumService;
        this.listenerService = listenerService;
        this.albumService = albumService;
    }

//delete song in listener
    @DeleteMapping("/{id}/{id2}")
    public String delete( @PathVariable("id") int id2, @PathVariable("id2") int id){
        albumService.findById(id);
        listenerService.findById(id2);
        listenerAlbumService.delete(listenerAlbumService.findByListenerAndAlbum(listenerService.findById(id2),albumService.findById(id)));

        String redirectUrl = "redirect:/listeners/" + id2+"/albums";
        return redirectUrl;
    }

//add song to listener
    //    @PostMapping("/addSongToListener/{id}/{id2}")


    @PostMapping("/addAlbumToListener")
    public String addSongToListener(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                     @ModelAttribute("listenerAlbum") ListenerAlbum listenerAlbum
                                    , @RequestParam("albumId") int albumId,
                                    @RequestParam("artistId") int artistId
                  ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
         //  model.addAttribute("listener",listenerDetails.getListener());

        listenerAlbum.setAlbum(albumService.findById(albumId));
        listenerAlbum.setListener(listenerDetails.getListener());
        listenerAlbumService.save(listenerAlbum);
        String redirectUrl = "redirect:/artists/" + artistId;
        return redirectUrl;
    }

    @PostMapping("/addAlbumToListener2")
    public String addSongToListener2(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                    @ModelAttribute("listenerAlbum") ListenerAlbum listenerAlbum,
                                     @RequestParam("albumId") int albumId
                                     , @RequestParam("artistId") int artistId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        //  model.addAttribute("listener",listenerDetails.getListener());

        listenerAlbum.setAlbum(albumService.findById(albumId));
        listenerAlbum.setListener(listenerDetails.getListener());
        listenerAlbumService.save(listenerAlbum);
        String redirectUrl = "redirect:/artists/" + artistId;
        return redirectUrl;
    }


}
