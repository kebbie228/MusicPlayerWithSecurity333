package org.itstep.controllers;

import org.itstep.model.ListenerPlaylist;
import org.itstep.model.ListenerSong;
import org.itstep.model.Playlist;
import org.itstep.model.Song;
import org.itstep.security.ListenerDetails;
import org.itstep.services.*;
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
    private final SongService songService;

    public ListenerPlaylistController(ListenerPlaylistService listenerPlaylistService, ListenerService listenerService, PlaylistService playlistService, SongService songService) {
        this.listenerPlaylistService = listenerPlaylistService;
        this.listenerService = listenerService;
        this.playlistService = playlistService;
        this.songService = songService;
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

//    @PostMapping("/addSongToPlaylist")
//    public String addSongToPlaylist(//@PathVariable("id") int id, @PathVariable("id2") int id2,
//                                    @ModelAttribute("song") Song song,
//                                    @RequestParam("songId") int songId,
//                                    @RequestParam("albumId") int albumId,
//                                    @RequestParam("playlistId") int playlistId
//    ) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
//        //  model.addAttribute("listener",listenerDetails.getListener());
//        playlistService.findById(playlistId).getPlaylistSongs().add(songService.findById(songId));
//        song.setSongName(   songService.findById(songId).getSongName());
//        //song.setPlaylist( songService.findById(songId).getPlaylist());
//        song.setPhotoFilePath(songService.findById(songId).getPhotoFilePath());
//        song.setAudioFilePath(songService.findById(songId).getAudioFilePath());
//        song.setArtist(songService.findById(songId).getAudioFilePath().get);
//        song.setSongYear();
//        songService.findById(songId).setPlaylist(playlistService.findById(playlistId));
//        songService.save(songService.findById(songId));
//        String redirectUrl = "redirect:/albums/" + albumId;
//        return redirectUrl;
//    }



}
