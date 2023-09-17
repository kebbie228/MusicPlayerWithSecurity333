package org.itstep.security;


import org.itstep.model.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Neil Alishev
 */
public class ListenerDetails implements UserDetails {
    private final Listener listener;


    public ListenerDetails(Listener listener) {
        this.listener = listener;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(listener.getRole()));
    }

    @Override
    public String getPassword() {
        return this.listener.getPassword();
    }

    @Override
    public String getUsername() {
        return this.listener.getListenerName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Нужно, чтобы получать данные аутентифицированного пользователя
    public Listener getListener() {
        return this.listener;
    }
}