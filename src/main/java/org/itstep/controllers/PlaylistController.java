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
    private final ListenerPlaylistService listenerPlaylistService;
    private final SongService songService;
    private final  PlaylistService playlistService;

    public PlaylistController(ListenerSongService listenerSongService, ListenerPlaylistService listenerPlaylistService, SongService songService, PlaylistService playlistService) {
        this.listenerSongService = listenerSongService;
        this.listenerPlaylistService = listenerPlaylistService;
        this.songService = songService;
        this.playlistService = playlistService;
    }
    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model, @ModelAttribute("listenerSong") ListenerSong listenerSong,
                        @ModelAttribute("listenerPlaylist") ListenerPlaylist listenerPlaylist){
        model.addAttribute("playlist",playlistService.findById(id));
        List<Song> songs = songService.findByPlaylist(playlistService.findById(id));
        model.addAttribute("songs",songService.findByPlaylist(playlistService.findById(id)));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
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

        boolean playlistAdded = listenerPlaylistService.hasPlaylist(listenerId,playlistService.findById(id).getId());
        model.addAttribute("playlistAdded", playlistAdded);

        return "playlist/index";
    }
    @GetMapping("/createPlaylist")
    public String newPlaylist(Model model, @ModelAttribute("playlist") Playlist playlist, @ModelAttribute("listenerAlbum") ListenerPlaylist listenerPlaylist){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());
        return "listener/newPlaylist";
    }

    @PostMapping("/createPlaylist")
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
