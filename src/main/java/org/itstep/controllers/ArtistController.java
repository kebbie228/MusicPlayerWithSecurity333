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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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

    @GetMapping("/profile")
    public String profile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        Artist artist=artistService.findByListener(listenerDetails.getListener());
        model.addAttribute("listener",listenerDetails.getListener());
        model.addAttribute("artist",artist);
        model.addAttribute("albums",albumService.findByArtist(artist));
model.addAttribute("songs",artist.getSongs());

        return "artist/profile";
    }
    @PatchMapping("/editPhotoProfile")
    public String editPhotoProfile(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        Artist artist= listenerDetails.getListener().getArtist();

        if (!imageFile.isEmpty()) {
            File dir = null; //Файловая система
            //dir = new File("src/main/resources/static/album_photo");
            dir = new File("target/classes/static/photo");
            imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
            artist.setPhotoFilePath("photo/"+imageFile.getOriginalFilename());
            System.out.println(artist.getId());
            System.out.println(artist.getNickName());

            artistService.update(artist.getId(),artist);
        }

     //   if (bindingResult.hasErrors()) return "artist/edit";

        return "redirect:/artists/profile";
    }

    @GetMapping("/addSong")
    public String newSong(Model model, @ModelAttribute("song") Song song){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());
        return "artist/addSong";
    }
    @GetMapping("/addAlbum")
    public String newAlbum(Model model,  @ModelAttribute("album") Album album){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
        model.addAttribute("listener",listenerDetails.getListener());
        return "artist/addAlbum";
    }

    @PostMapping("/createAlbum")
    public String createAlbum(@ModelAttribute("album") Album album,
                                 @RequestParam("imageFile") MultipartFile imageFile
                                 ) throws IOException
    {

        if (!imageFile.isEmpty()) {
            File dir = null; //Файловая система
            //dir = new File("src/main/resources/static/album_photo");
            dir = new File("target/classes/static/photo");
            imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
            album.setPhotoFilePath("photo/"+imageFile.getOriginalFilename());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();

        album.setArtist(listenerDetails.getListener().getArtist());
        albumService.save(album);

        String redirectUrl = "redirect:/artists/profile";
        return redirectUrl;
    }

    @DeleteMapping("/deleteSong")
    public String delete4(  @RequestParam("songId") int  songId){
        System.out.println(songId);

        songService.delete(songId);

        String redirectUrl = "redirect:/artists/profile";
        return redirectUrl;
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
