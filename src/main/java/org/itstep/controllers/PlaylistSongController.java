package org.itstep.controllers;

import org.itstep.model.PlaylistSong;
import org.itstep.security.ListenerDetails;
import org.itstep.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlistSongs")

public class PlaylistSongController {

    private final ListenerPlaylistService listenerPlaylistService;
    private final ListenerService listenerService;
    private final PlaylistService playlistService;
    private final SongService songService;
    private final PlaylistSongService playlistSongService;

    public PlaylistSongController(ListenerPlaylistService listenerPlaylistService, ListenerService listenerService, PlaylistService playlistService, SongService songService, PlaylistSongService playlistSongService) {
        this.listenerPlaylistService = listenerPlaylistService;
        this.listenerService = listenerService;
        this.playlistService = playlistService;
        this.songService = songService;
        this.playlistSongService = playlistSongService;
    }


    @DeleteMapping("/{id}/{id2}")
    public String delete(@PathVariable("id") int id, @PathVariable("id2") int id2){
        songService.findById(id);
        playlistService.findById(id2);
        playlistSongService.delete(playlistSongService.findByPlaylistAndSong(songService.findById(id), playlistService.findById(id2)));
        String redirectUrl = "redirect:/playlists/" + id2;
        return redirectUrl;
    }


    @PostMapping("/addSongToPlaylist")
    public String addSongToListener(//@PathVariable("id") int id, @PathVariable("id2") int id2,
                                    @ModelAttribute("playlistSong") PlaylistSong playlistSong,
                                    @RequestParam("songId") int songId,
                                    @RequestParam("playlistId") int playlistId,
                                    @RequestParam("albumId") int albumId

    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        //  model.addAttribute("listener",listenerDetails.getListener());

        playlistSong.setSong(songService.findById(songId));
        playlistSong.setPlaylist(playlistService.findById(playlistId));
        playlistSongService.save(playlistSong);
        String redirectUrl = "redirect:/albums/" + albumId;
        return redirectUrl;
    }
}
