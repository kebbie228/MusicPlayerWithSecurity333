package org.itstep.services;



import org.itstep.model.Listener;
import org.itstep.repositories.ListenerRepository;
import org.itstep.security.ListenerDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Service
public class ListenerDetailsService implements UserDetailsService {

    private final ListenerRepository listenerRepository;

    @Autowired
    public ListenerDetailsService(ListenerRepository listenerRepository) {
        this.listenerRepository = listenerRepository;
    }




    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Listener> listener = listenerRepository.findByListenerName(s);

      if (listener.isEmpty()) throw new UsernameNotFoundException("listener not found!");
        return new ListenerDetails(listener.get());
    }
}