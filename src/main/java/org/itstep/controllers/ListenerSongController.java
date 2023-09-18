package org.itstep.controllers;


import org.itstep.model.ListenerSong;
import org.itstep.security.ListenerDetails;
import org.itstep.services.ListenerService;
import org.itstep.services.ListenerSongService;
import org.itstep.services.SongService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/listenerSongs")
public class ListenerSongController {
    private final ListenerSongService listenerSongService;
    private final ListenerService listenerService;
    private final SongService songService;

    public ListenerSongController(ListenerSongService listenerSongService, ListenerService listenerService, SongService songService) {
        this.listenerSongService = listenerSongService;
        this.listenerService = listenerService;
        this.songService = songService;
    }

//delete song in listener
    @DeleteMapping("/{id}/{id2}")
    public String delete( @PathVariable("id") int id, @PathVariable("id2") int id2){
        songService.findById(id);
        listenerService.findById(id2);
        listenerSongService.delete(listenerSongService.findByListenerAndSong(songService.findById(id),listenerService.findById(id2)));

        String redirectUrl = "redirect:/listeners/" + id2+"/songs";
        return redirectUrl;
    }

//add song to listener
    //    @PostMapping("/addSongToListener/{id}/{id2}")
    @PostMapping("/addSongToListener")
    public String addSongToListener(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                     @ModelAttribute("listenerSong") ListenerSong listenerSong,
                                    @RequestParam("songId") int songId
                                    , @RequestParam("albumId") int albumId
                  ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
         //  model.addAttribute("listener",listenerDetails.getListener());

        listenerSong.setSong(songService.findById(songId));
        listenerSong.setListener(listenerDetails.getListener());
        listenerSongService.save(listenerSong);
        String redirectUrl = "redirect:/albums/" + albumId;
        return redirectUrl;
    }




}
