package org.itstep.controllers;


import org.itstep.model.Album;
import org.itstep.model.Song;
import org.itstep.services.AlbumService;
import org.itstep.services.ArtistService;
import org.itstep.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private  final AlbumService albumService;
    private final ArtistService artistService;
private final SongService songService;
    @Autowired
    public AlbumController(AlbumService albumService, ArtistService artistService, SongService songService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping()
    public String show(Model model){
        model.addAttribute("albums",albumService.findAll());
        return "album/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model){
        model.addAttribute("album",albumService.findById(id));
        model.addAttribute("songs",songService.findByAlbum(albumService.findById(id)));

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
    @GetMapping("/new/playlist")
    public String newPlayList(@ModelAttribute("album") Album album,Model model){
        model.addAttribute("artists",artistService.findAll());
        return "album/newPlaylist";
    }

    @PostMapping("/playlist")
    public String createPlayList(@ModelAttribute("album") Album album,
                         BindingResult bindingResult,
                         @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
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
    //    model.addAttribute("artist",artistService.findByAlbums(albumService.findById(id)));
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


}
