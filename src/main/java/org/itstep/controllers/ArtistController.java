package org.itstep.controllers;

import org.itstep.model.Artist;
import org.itstep.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;
@Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping()
    public String show(Model model){
    model.addAttribute("artists",artistService.findAll());
    return "artist/show";
    }


    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model){
        model.addAttribute("artist",artistService.findById(id));
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
