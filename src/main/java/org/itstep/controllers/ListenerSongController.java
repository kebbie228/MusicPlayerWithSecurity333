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

    @DeleteMapping("/{id}/{id2}/album")
    public String delete2( @PathVariable("id") int id, @PathVariable("id2") int id2,  @RequestParam("albumId") int  albumId ){
        songService.findById(id);
        listenerService.findById(id2);
        listenerSongService.delete(listenerSongService.findByListenerAndSong(songService.findById(id),listenerService.findById(id2)));

        String redirectUrl = "redirect:/albums/" + albumId;
        return redirectUrl;
    }

    @DeleteMapping("/{id}/{id2}/playlist")
    public String delete3( @PathVariable("id") int id, @PathVariable("id2") int id2,  @RequestParam("playlistId") int  playlistId ){
        songService.findById(id);
        listenerService.findById(id2);
        listenerSongService.delete(listenerSongService.findByListenerAndSong(songService.findById(id),listenerService.findById(id2)));

        String redirectUrl = "redirect:/playlists/" + playlistId;
        return redirectUrl;
    }


    @DeleteMapping("/{id}/{id2}/song")
    public String delete4( @PathVariable("id") int id, @PathVariable("id2") int id2 ){
        songService.findById(id);
        listenerService.findById(id2);
        listenerSongService.delete(listenerSongService.findByListenerAndSong(songService.findById(id),listenerService.findById(id2)));
        String redirectUrl = "redirect:/listeners/" + id2+"/songs";
        return redirectUrl;
    }
    @DeleteMapping("/{id}")
    public String delete5( @PathVariable("id") int id){
        songService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        listenerService.findById(listenerDetails.getListener().getId());
        listenerSongService.delete(listenerSongService.findByListenerAndSong(songService.findById(id),listenerService.findById((listenerDetails.getListener().getId()))));
        String redirectUrl = "redirect:/songs/" + id;
        return redirectUrl;
    }


//add song to listener
    //    @PostMapping("/addSongToListener/{id}/{id2}")

    @PostMapping("/addSongToListener3")
    public String addSongToListener3(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                     @ModelAttribute("listenerSong") ListenerSong listenerSong,
                                     @RequestParam("songId") int songId,
                                     @RequestParam("artistId") int artistId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        //  model.addAttribute("listener",listenerDetails.getListener());

        listenerSong.setSong(songService.findById(songId));
        listenerSong.setListener(listenerDetails.getListener());
        listenerSongService.save(listenerSong);
        String redirectUrl = "redirect:/artists/" + artistId;
        return redirectUrl;
    }

    @PostMapping("/addSongToListener2")
    public String addSongToListener2(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                     @ModelAttribute("listenerSong") ListenerSong listenerSong,
                                    @RequestParam("songId") int songId
                  ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
         //  model.addAttribute("listener",listenerDetails.getListener());

        listenerSong.setSong(songService.findById(songId));
        listenerSong.setListener(listenerDetails.getListener());
        listenerSongService.save(listenerSong);
        String redirectUrl = "redirect:/songs/" + songId;
        return redirectUrl;
    }
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
    @PostMapping("/addSongToListener4")
    public String addSongToListener4(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                     @ModelAttribute("listenerSong") ListenerSong listenerSong,
                                     @RequestParam("songId") int songId,
                                     @RequestParam("playlistId") int playlistId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        //  model.addAttribute("listener",listenerDetails.getListener());

        listenerSong.setSong(songService.findById(songId));
        listenerSong.setListener(listenerDetails.getListener());
        listenerSongService.save(listenerSong);
        String redirectUrl = "redirect:/playlists/" + playlistId;
        return redirectUrl;
    }


}
