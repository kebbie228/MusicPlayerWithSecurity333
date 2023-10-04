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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private  final AlbumService albumService;
    private final ArtistService artistService;
    private  final ListenerSongService listenerSongService;
    private final ListenerAlbumService listenerAlbumService;
    private final ListenerService listenerService;

private final SongService songService;
    @Autowired
    public AlbumController(AlbumService albumService, ArtistService artistService, ListenerSongService listenerSongService, ListenerAlbumService listenerAlbumService, ListenerService listenerService, SongService songService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.listenerSongService = listenerSongService;
        this.listenerAlbumService = listenerAlbumService;
        this.listenerService = listenerService;
        this.songService = songService;
    }

    @GetMapping()
    public String show(Model model){
        model.addAttribute("albums",albumService.findAll());
        return "album/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model,  @ModelAttribute("listenerSong") ListenerSong listenerSong,
                        @ModelAttribute("listenerAlbum") ListenerAlbum listenerAlbum,@ModelAttribute("playlistSong") PlaylistSong playlistSong){
        model.addAttribute("album",albumService.findById(id));

      List<Song> songs = songService.findByAlbums(albumService.findById(id));
     model.addAttribute("songs",songService.findByAlbums(albumService.findById(id)));

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

        boolean albumAdded = listenerAlbumService.hasAlbum(listenerId,albumService.findById(id).getId());
        model.addAttribute("albumAdded", albumAdded);
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
//konec

        model.addAttribute("playlists",listenerService.findById(listenerDetails.getListener().getId()).getPlaylists());
        //   System.out.println(listenerService.findById(listenerDetails.getListener().getId()).getPlaylists());

        return "album/index";
    }

    @GetMapping("/new")
    public String newAlbum(@ModelAttribute("album") Album album,Model model){
        model.addAttribute("artists",artistService.findAll());
        return "album/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("album") Album album,
                         BindingResult bindingResult,
                         @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (bindingResult.hasErrors())
            return "artist/new";
        if (!imageFile.isEmpty()) {
                    File dir = null; //Файловая система
                    //dir = new File("src/main/resources/static/album_photo");
                    dir = new File("target/classes/static/photo");
                    imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
                    album.setPhotoFilePath("/photo/"+imageFile.getOriginalFilename());
                }
        albumService.save(album);
        return "redirect:/albums";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("album", albumService.findById(id));
        model.addAttribute("artists",artistService.findAll());
        return "album/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("album") @Valid Album album, BindingResult bindingResult,
                         @PathVariable("id") int id,
                         @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (bindingResult.hasErrors())
            return "album/edit";
        // не работает удаление, но фотография меняется
             String oldPhotoFilePath = album.getPhotoFilePath(); // Получите старый путь к фотографии
             File oldPhotoFile = new File("target/classes/static/photo/"+ oldPhotoFilePath);
              oldPhotoFile.delete();

        if (!imageFile.isEmpty()) {
            File dir = null; //Файловая система
            //dir = new File("src/main/resources/static/album_photo");
            dir = new File("target/classes/static/photo");
            imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
            album.setPhotoFilePath("/photo/"+imageFile.getOriginalFilename());
        }

        albumService.update(id, album);
        return "redirect:/albums";
    }


    @GetMapping("/{id}/addSong")
    public String addSong(Model model, @PathVariable("id") int id,
         @ModelAttribute("song")Song song
    ) {
       // model.addAttribute("song", new Song());
        model.addAttribute("album", albumService.findById(id));
        model.addAttribute("artist",artistService.findByAlbums(albumService.findById(id)));
        return "album/addSong";
    }


    @DeleteMapping("/{id}")
    public String delete( @PathVariable("id") int id){
        albumService.delete(id);
        return "redirect:/albums";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "album/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String firstLetters) {
        model.addAttribute("albums", albumService.findByAlbumNameContainingIgnoreCase(firstLetters));
        return "album/search";
    }
//    @PostMapping("/createPlaylist")
//    public String createPlaylist(@ModelAttribute("album") Album album,
//    @RequestParam("imageFile") MultipartFile imageFile,
//     @ModelAttribute("listenerAlbum") ListenerAlbum listenerAlbum) throws IOException
//    {
//        if (!imageFile.isEmpty()) {
//            File dir = null; //Файловая система
//            //dir = new File("src/main/resources/static/album_photo");
//            dir = new File("target/classes/static/photo");
//            imageFile.transferTo(new File(dir.getAbsolutePath()+"/"+imageFile.getOriginalFilename()));
//            album.setPhotoFilePath("photo/"+imageFile.getOriginalFilename());
//        }
//        albumService.save(album);
//        listenerAlbum.setAlbum(album);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        ListenerDetails listenerDetails = (ListenerDetails) authentication.getPrincipal();
//        listenerAlbum.setListener(listenerDetails.getListener());
//        listenerAlbumService.save(listenerAlbum);
//
//        String redirectUrl = "redirect:/albums/" + album.getId();
//        return redirectUrl;
//    }





}
