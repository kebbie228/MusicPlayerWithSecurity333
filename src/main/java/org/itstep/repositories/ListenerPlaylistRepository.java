package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ListenerPlaylistRepository extends  JpaRepository<ListenerPlaylist,Integer> {

    ListenerPlaylist findByListenerAndPlaylist(Listener listener, Playlist playlist);

    boolean existsByListenerIdAndPlaylistId(int listenerId, int playlistId);
}
