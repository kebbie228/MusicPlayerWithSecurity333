package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.security.ListenerDetails;
import org.itstep.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;
    private final ListenerAlbumService listenerAlbumService;
    private final ListenerSongService listenerSongService;

@Autowired
    public ArtistController(ArtistService artistService, AlbumService albumService, SongService songService, ListenerAlbumService listenerAlbumService, ListenerSongService listenerSongService) {
        this.artistService = artistService;
    this.albumService = albumService;
    this.songService = songService;
    this.listenerAlbumService = listenerAlbumService;
    this.listenerSongService = listenerSongService;
}

    @GetMapping()
    public String show(Model model){
    model.addAttribute("artists",artistService.findAll());
    return "artist/show";
    }


    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model,@ModelAttribute("listenerSong") ListenerSong listenerSong,
                        @ModelAttribute("listenerAlbum") ListenerAlbum listenerAlbum){
        model.addAttribute("artist",artistService.findById(id));
        model.addAttribute("albums",albumService.findByArtist(artistService.findById(id)));
//        List<Song> songs=songService.findByArtist(artistService.findById(id));
//        songs.size();
//        model.addAttribute("songs",songService.findByArtist(artistService.findById(id)));
        List<Song> songs = songService.findByArtist(artistService.findById(id));

// Создаем множество для хранения уникальных названий песен
        Set<String> uniqueSongNames = new HashSet<>();

// Создаем список для хранения песен с уникальными названиями
        List<Song> uniqueSongs = new ArrayList<>();

// Проходимся по всем песням и добавляем их в uniqueSongs, если название уникально
        for (Song song : songs) {
            if (uniqueSongNames.add(song.getSongName())) {
                uniqueSongs.add(song);
            }
        }

        model.addAttribute("songs", uniqueSongs);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());

        Map<Integer, Boolean> songAddedMap = new HashMap<>();

        for (Song song : uniqueSongs) {
            int songId = song.getId();
            boolean songAdded = listenerSongService.hasSong(listenerDetails.getListener().getId(), songId);
            songAddedMap.put(songId, songAdded);
        }
        model.addAttribute("songAddedMap", songAddedMap); // Передаем информацию в модель


        Map<Integer, Boolean> albumAddedMap = new HashMap<>();
    List<Album> albums=  albumService.findByArtist(artistService.findById(id));

        for (Album album : albums) {
            int albumId = album.getId();
            boolean albumAdded = listenerAlbumService.hasAlbum(listenerDetails.getListener().getId(), albumId);
            albumAddedMap.put(albumId, albumAdded);
        }
        model.addAttribute("albumAddedMap", albumAddedMap); // Передаем информацию в модель

        return "artist/index";
    }


    @GetMapping("/new")
    public String newArtist(@ModelAttribute("artist") Artist artist){
        return "artist/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("artist") @Valid Artist artist,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "artist/new";

        artistService.save(artist);
        return "redirect:/artists";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("artist",artistService.findById(id));
        return "artist/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("artist") @Valid Artist artist, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "artist/edit";

        artistService.update(id, artist);
        return "redirect:/artists";
    }

    @DeleteMapping("/{id}")
    public String delete( @PathVariable("id") int id){
    artistService.delete(id);
    return "redirect:/artists";
    }


}
