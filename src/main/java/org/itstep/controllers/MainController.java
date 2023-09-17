package org.itstep.controllers;


import org.itstep.services.AlbumService;
import org.itstep.services.ArtistService;
import org.itstep.services.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class MainController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;


    public MainController(AlbumService albumService, ArtistService artistService, SongService songService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "main/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String firstLetters) {
        model.addAttribute("albums", albumService.findByAlbumNameContainingIgnoreCase(firstLetters));
        model.addAttribute("artists", artistService.findByNickNameContainingIgnoreCase(firstLetters));
        model.addAttribute("songs", songService.findBySongNameContainingIgnoreCase(firstLetters));
        return "main/search";
    }
    @GetMapping("/search2")
    public String searchPage2() {
        return "main/search2";
    }

    @PostMapping("/search2")
    public String makeSearch2(Model model, @RequestParam("search-input-sidenav") String firstLetters) {
        model.addAttribute("albums", albumService.findByAlbumNameContainingIgnoreCase(firstLetters));
        model.addAttribute("artists", artistService.findByNickNameContainingIgnoreCase(firstLetters));
        model.addAttribute("songs", songService.findBySongNameContainingIgnoreCase(firstLetters));
        return "main/search2";
    }

}
