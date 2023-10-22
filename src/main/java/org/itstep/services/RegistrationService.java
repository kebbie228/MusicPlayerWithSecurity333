package org.itstep.services;

import org.itstep.model.Listener;
import org.itstep.repositories.ListenerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {
    private final ListenerRepository listenerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(ListenerRepository listenerRepository, PasswordEncoder passwordEncoder) {
        this.listenerRepository = listenerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
     public void register(Listener listener) {
        listener.setPassword(passwordEncoder.encode(listener.getPassword()));
        listener.setRole("ROLE_USER");
        listenerRepository.save(listener);
     }

    @Transactional
    public void register2(Listener listener) {
        listener.setPassword(passwordEncoder.encode(listener.getPassword()));
        listener.setRole("ROLE_ARTIST");
        listenerRepository.save(listener);
    }
}
