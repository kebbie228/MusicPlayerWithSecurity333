package org.itstep.repositories;

import org.itstep.model.Album;
import org.itstep.model.Artist;
import org.itstep.model.Listener;
import org.itstep.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {

    List<Playlist> findByPlaylistNameContainingIgnoreCase(String firstLetters);

    List<Playlist> findByListeners(Listener listener);


}
