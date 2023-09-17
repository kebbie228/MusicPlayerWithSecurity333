package org.itstep.util;

import org.itstep.model.Listener;
import org.itstep.model.Person;
import org.itstep.services.ListenerDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ListenerValidator implements Validator {

    private final ListenerDetailsService listenerDetailsService;

    @Autowired
    public ListenerValidator(ListenerDetailsService listenerDetailsService) {
        this.listenerDetailsService = listenerDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return  Listener.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Listener listener = (Listener) o;

        try {

            listenerDetailsService.loadListenerByListenerName(listener.getListenerName());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок, пользователь не найден
        }

        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}