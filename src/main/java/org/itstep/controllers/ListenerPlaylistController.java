package org.itstep.controllers;

import org.itstep.model.ListenerAlbum;
import org.itstep.model.ListenerPlaylist;
import org.itstep.security.ListenerDetails;
import org.itstep.services.AlbumService;
import org.itstep.services.ListenerPlaylistService;
import org.itstep.services.ListenerService;
import org.itstep.services.PlaylistService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/listenerPlaylists")
public class ListenerPlaylistController {


    private final ListenerPlaylistService listenerPlaylistService;
    private final ListenerService listenerService;
    private final PlaylistService playlistService;

    public ListenerPlaylistController(ListenerPlaylistService listenerPlaylistService, ListenerService listenerService, PlaylistService playlistService) {
        this.listenerPlaylistService = listenerPlaylistService;
        this.listenerService = listenerService;
        this.playlistService = playlistService;
    }


    @DeleteMapping("/{id}/{id2}")
    public String delete(@PathVariable("id") int id2, @PathVariable("id2") int id){
        playlistService.findById(id);
        listenerService.findById(id2);
        listenerPlaylistService.delete(listenerPlaylistService.findByListenerAndPlaylist(listenerService.findById(id2), playlistService.findById(id)));

        String redirectUrl = "redirect:/listeners/" + id2+"/albums";
        return redirectUrl;
    }

    @PostMapping("/addPlaylistToListener")
    public String addSongToListener(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                    @ModelAttribute("listenerPlaylist") ListenerPlaylist listenerPlaylist
            , @RequestParam("playlistId") int playlistId

    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        //  model.addAttribute("listener",listenerDetails.getListener());

        listenerPlaylist.setPlaylist(playlistService.findById(playlistId));
        listenerPlaylist.setListener(listenerDetails.getListener());
        listenerPlaylistService.save(listenerPlaylist);
        String redirectUrl = "redirect:/playlists/" + playlistId;
        return redirectUrl;
    }
}
