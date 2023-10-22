package org.itstep.controllers;

import org.itstep.model.Artist;
import org.itstep.model.Listener;

import org.itstep.services.ArtistService;
import org.itstep.services.ListenerService;
import org.itstep.services.RegistrationService;
//import org.itstep.util.ListenerValidator;
import org.itstep.util.ListenerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
   private final ListenerValidator listenerValidator;
    private final RegistrationService registrationService;
    private final ArtistService artistService;
@Autowired
    public AuthController(ListenerValidator listenerValidator,
                          RegistrationService registrationService, ArtistService artistService) {
      this.listenerValidator = listenerValidator;
    this.registrationService = registrationService;
    this.artistService = artistService;
}

    @GetMapping("/login")
    public String loginPage(){
    return "auth/login";
}

@GetMapping("/registration")
public String registrationPage(@ModelAttribute("listener") Listener listener ){
    return "auth/registration";
}
    @PostMapping("/registration")
public String performRegistration(
            @ModelAttribute("listener") @Valid Listener listener,BindingResult bindingResult,
            @RequestParam(value = "registerAsArtist", required = false) boolean registerAsArtist
    ){
        listenerValidator.validate(listener,bindingResult);
//if(bindingResult.hasErrors()) return "/auth/registration";

        if (registerAsArtist) {
            registrationService.register2(listener);
            Artist newArtist= new Artist();
            newArtist.setNickName(listener.getListenerName());
            newArtist.setListener(listener);
            newArtist.setPhotoFilePath("photo/no-avatar.jpg");
            artistService.save(newArtist);
        } else {
            registrationService.register(listener);
        }

      return "redirect:/auth/login";
    }

}

