package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.security.ListenerDetails;
import org.itstep.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/playlists")
public class PlaylistController {

    private  final ListenerSongService listenerSongService;
    private final PlaylistSongService playlistSongService;
    private final ListenerPlaylistService listenerPlaylistService;
    private final SongService songService;
    private  final  ListenerService listenerService;
    private final  PlaylistService playlistService;

    public PlaylistController(ListenerSongService listenerSongService, PlaylistSongService playlistSongService, ListenerPlaylistService listenerPlaylistService, SongService songService, ListenerService listenerService, PlaylistService playlistService) {
        this.listenerSongService = listenerSongService;
        this.playlistSongService = playlistSongService;
        this.listenerPlaylistService = listenerPlaylistService;
        this.songService = songService;
        this.listenerService = listenerService;
        this.playlistService = playlistService;
    }
    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model, @ModelAttribute("listenerSong") ListenerSong listenerSong,
                        @ModelAttribute("listenerPlaylist") ListenerPlaylist listenerPlaylist,
                        @ModelAttribute("playlistSong") PlaylistSong playlistSong){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();

        model.addAttribute("playlist",playlistService.findById(id));
        List<Song> songs = playlistService.findById(id).getPlaylistSongs();
        model.addAttribute("songs",songs);

        model.addAttribute("listener",listenerDetails.getListener());

        int listenerId = listenerDetails.getListener().getId(); // Получаем идентификатор слушателя

        // Создаем Map для хранения информации о каждой песне (songId -> songAdded)
        Map<Integer, Boolean> songAddedMap = new HashMap<>();

        for (Song song : songs) {
            int songId = song.getId();
            boolean songAdded = listenerSongService.hasSong(listenerId, songId);
            songAddedMap.put(songId, songAdded);
        }

        model.addAttribute("songAddedMap", songAddedMap); // Передаем информацию в модель



//nachalo
        List<Playlist> playlists = listenerService.findById(listenerDetails.getListener().getId()).getPlaylists();
        Map<Integer, Boolean> songInPlaylistMap = new HashMap<>();
        for (Song song : songs) {
            int songId = song.getId();
            boolean songInPlaylist = false;

            for (Playlist playlist : playlists) {
                if (playlist.getPlaylistSongs().contains(song)) {
                    songInPlaylist = true;
                    break;
                }
            }

            songInPlaylistMap.put(songId, songInPlaylist);
        }

        model.addAttribute("songInPlaylistMap", songInPlaylistMap);
        model.addAttribute("playlists",listenerService.findById(listenerDetails.getListener().getId()).getPlaylists());
        System.out.println(songInPlaylistMap);
//konec

        //model.addAttribute("playlists",listenerService.findById(listenerDetails.getListener().getId()).getPlaylists());
     //   System.out.println(playlistService.findById(id).getPlaylistSongs());
        return "playlist/index";
    }
    @GetMapping("/createPlaylist")
    public String newPlaylist(Model model, @ModelAttribute("playlist") Playlist playlist, @ModelAttribute("listenerAlbum") ListenerPlaylist listenerPlaylist){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());
        return "listener/newPlaylist";
    }

    @PatchMapping("/createPlaylist")
    public String createPlaylist(@ModelAttribute("playlist") Playlist playlist,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 @ModelAttribute("listenerPlaylist") ListenerPlaylist listenerPlaylist) throws IOException
    {
        if (!imageFile.isEmpty()) {
            File dir = null; //Файловая система
            //dir = new File("src/main/resources/static/album_photo");
            dir = new File("target/classes/static/photo");
            imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
            playlist.setPhotoFilePath("photo/"+imageFile.getOriginalFilename());
        }
        playlistService.save(playlist);
        listenerPlaylist.setPlaylist(playlist);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        listenerPlaylist.setListener(listenerDetails.getListener());
        listenerPlaylistService.save(listenerPlaylist);

        String redirectUrl = "redirect:/playlists/" + playlist.getId();
        return redirectUrl;
    }



}
