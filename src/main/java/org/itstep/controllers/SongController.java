package org.itstep.controllers;


import org.itstep.model.Album;
import org.itstep.model.ListenerSong;
import org.itstep.model.Song;
import org.itstep.security.ListenerDetails;
import org.itstep.services.AlbumService;
import org.itstep.services.ArtistService;
import org.itstep.services.ListenerSongService;
import org.itstep.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;
    private final ListenerSongService listenerSongService;

    private final ArtistService artistService;
    @Autowired
    public SongController(SongService songService, AlbumService albumService, ListenerSongService listenerSongService, ArtistService artistService) {
        this.songService = songService;
        this.albumService = albumService;
        this.listenerSongService = listenerSongService;
        this.artistService = artistService;
    }


    @GetMapping()
    public String show(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "song/show";
    }
    @GetMapping("/play")
    public String play(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "song/player";
    }


    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model, @ModelAttribute("listenerSong") ListenerSong listenerSong) {
        model.addAttribute("song", songService.findById(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());

        boolean songAdded = listenerSongService.hasSong(listenerDetails.getListener().getId(),albumService.findById(id).getId());
        model.addAttribute("songAdded", songAdded);


        return "song/index";
    }
//При условии что альбом создан
//    @GetMapping("/new")
//    public String newSong(@ModelAttribute("song") Song song, Model model){
//        model.addAttribute("artists",artistService.findAll());
//        return "album/new";
//    }

    @PostMapping("/add")
    public String addSong(
            @ModelAttribute("song") Song song,
            //  BindingResult bindingResult,
            @RequestParam("albumId") int albumId,
            @RequestParam("audioFile") MultipartFile audioFile
    ) throws IOException
    {
        //   if (bindingResult.hasErrors())
        // return "album/addSong";
        //     Song song=new Song();
        Album album=albumService.findById(albumId);
        //ис
      //  song.setAlbum(album);
        song.setArtist(artistService.findByAlbums(album));
        song.setSongYear(LocalDate.now().getYear());
        song.setPhotoFilePath(album.getPhotoFilePath());
        if (!audioFile.isEmpty()) {
            File dir = null; //Файловая система
            dir = new File("target/classes/static/");
            audioFile.transferTo(new File(dir.getAbsolutePath()+"/"+audioFile.getOriginalFilename()));
            song.setAudioFilePath(audioFile.getOriginalFilename());
        }
//        if (!imageFile.isEmpty()) {
//            File dir = null; //Файловая система
//            //dir = new File("src/main/resources/static/album_photo");
//            dir = new File("target/classes/static/photo");
//            imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
//            song.setPhotoFilePath("/photo/"+imageFile.getOriginalFilename());
//        }
        //albumService.update(albumId,album);
        songService.save(song);


        return "redirect:/albums";
    }


    @DeleteMapping("/{id}")
    public String delete( @PathVariable("id") int id){
        songService.delete(id);
        return "redirect:/songs";
    }

}
