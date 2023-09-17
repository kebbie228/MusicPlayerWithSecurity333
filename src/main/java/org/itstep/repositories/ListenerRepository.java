package org.itstep.repositories;

import org.itstep.model.Listener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListenerRepository  extends JpaRepository<Listener,Integer> {
    Optional<Listener> findByListenerName(String listenerName);
}
