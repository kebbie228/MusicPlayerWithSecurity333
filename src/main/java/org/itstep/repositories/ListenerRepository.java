package org.itstep.Repository;

import org.itstep.model.Listener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenerRepository  extends JpaRepository<Listener,Integer> {
}
