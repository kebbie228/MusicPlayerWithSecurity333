package org.itstep.controllers;


import org.itstep.model.Listener;
import org.itstep.model.ListenerSong;
import org.itstep.security.ListenerDetails;
import org.itstep.services.ListenerDetailsService;
import org.itstep.services.ListenerService;
import org.itstep.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/listeners")

public class ListenerController {

    private final ListenerService listenerService;
    private final SongService songService;

    public ListenerController(ListenerService listenerService, SongService songService) {
        this.listenerService = listenerService;
        this.songService = songService;
    }

    @GetMapping()
    public String show(Model model){
        model.addAttribute("listeners",listenerService.findAll());
        return "listener/show";
    }


    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model){
        model.addAttribute("listener",listenerService.findById(id));
      //dangerous
    //    model.addAttribute("songs",songService.findByListeners(listenerService.findById(id)));
        return "listener/index";
    }


    @GetMapping("/{id}/songs")
    public String listenerSong(@PathVariable("id") int id, Model model){
        model.addAttribute("listener",listenerService.findById(id));
        //dangerous
          model.addAttribute("songs",songService.findByListeners(listenerService.findById(id)));
        return "listener/listenerSongs";
    }


    @GetMapping("/new")
    public String newListener(@ModelAttribute("listener") Listener listener){
        return "listener/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("listener") @Valid Listener listener,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "listener/new";

        listenerService.save(listener);
        return "redirect:/listeners";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("listener",listenerService.findById(id));
        return "listener/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("listener") @Valid Listener listener, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "listener/edit";

        listenerService.update(id, listener);
        return "redirect:/listeners";
    }



    @DeleteMapping("/{id}")
    public String delete( @PathVariable("id") int id){
        listenerService.delete(id);
        return "redirect:/listeners";
    }

    @GetMapping("/{id}/addSongs")
    public String addSongsToListener(@PathVariable("id") int id, Model model,  @ModelAttribute("listenerSong") ListenerSong listenerSong){
        model.addAttribute("listener",listenerService.findById(id));
        //dangerous
        model.addAttribute("songs",songService.findAll());
        return "listener/addSongs";
    }



}
