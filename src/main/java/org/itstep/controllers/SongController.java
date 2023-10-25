package org.itstep.controllers;


import org.itstep.model.*;
import org.itstep.security.ListenerDetails;
import org.itstep.services.*;
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

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;
    private final ListenerSongService listenerSongService;
    private final AlbumSongService albumSongService;

    private final ArtistService artistService;
    @Autowired
    public SongController(SongService songService, AlbumService albumService, ListenerSongService listenerSongService, AlbumSongService albumSongService, ArtistService artistService) {
        this.songService = songService;
        this.albumService = albumService;
        this.listenerSongService = listenerSongService;
        this.albumSongService = albumSongService;
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

        boolean songAdded = listenerSongService.hasSong(listenerDetails.getListener().getId(),songService.findById(id).getId());
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
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("audioFile") MultipartFile audioFile
    ) throws IOException
    {
        //   if (bindingResult.hasErrors())
        // return "album/addSong";
        //     Song song=new Song();
       // Album album=albumService.findById(albumId);
        //ис
      //  song.setAlbum(album);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        Artist artist= listenerDetails.getListener().getArtist();

        song.setArtist(artist);
        song.setSongYear(LocalDate.now().getYear());

        if (!audioFile.isEmpty()) {
            File dir = null; //Файловая система
            dir = new File("target/classes/static/");
            audioFile.transferTo(new File(dir.getAbsolutePath()+"/"+audioFile.getOriginalFilename()));
            song.setAudioFilePath(audioFile.getOriginalFilename());
        }

        if (!imageFile.isEmpty()) {
            File dir = null; //Файловая система
            //dir = new File("src/main/resources/static/album_photo");
            dir = new File("target/classes/static/photo");
            imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
            song.setPhotoFilePath("photo/"+imageFile.getOriginalFilename());
        }
        //albumService.update(albumId,album);
        songService.save(song);


        String redirectUrl = "redirect:/artists/profile";
        return redirectUrl;
    }


    @DeleteMapping("/{id}")
    public String delete( @PathVariable("id") int id){
        songService.delete(id);
        return "redirect:/songs";
    }



    @PostMapping("/add2")
    public String addSong2(
            @ModelAttribute("song") Song song,
            @RequestParam("audioFile") MultipartFile audioFile,
            @RequestParam("albumId") int albumId

    ) throws IOException
    {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        Artist artist= listenerDetails.getListener().getArtist();

        song.setArtist(artist);
        song.setSongYear(LocalDate.now().getYear());

        if (!audioFile.isEmpty()) {
            File dir = null; //Файловая система
            dir = new File("target/classes/static/");
            audioFile.transferTo(new File(dir.getAbsolutePath()+"/"+audioFile.getOriginalFilename()));
            song.setAudioFilePath(audioFile.getOriginalFilename());
        }
    Album album = albumService.findById(albumId);
     song.setPhotoFilePath(album.getPhotoFilePath());

        //albumService.update(albumId,album);
        songService.save(song);
        AlbumSong albumSong= new AlbumSong();
        albumSong.setAlbum(album);
        albumSong.setSong(song);
        albumSongService.save(albumSong);
        String redirectUrl = "redirect:/artists/profile/album/"+ albumId;
        return redirectUrl;
    }
}
