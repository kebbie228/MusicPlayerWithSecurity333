package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenerAlbumRepository extends JpaRepository<ListenerAlbum,Integer> {
    ListenerAlbum findByListenerAndAlbum(Listener listener, Album album);

   boolean existsByListenerIdAndAlbumId(int listenerId, int albumId);
}
