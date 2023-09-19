package org.itstep.repositories;

import org.itstep.model.Album;
import org.itstep.model.Listener;
import org.itstep.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Integer> {

    List<Album> findByAlbumNameContainingIgnoreCase(String firstLetters);
    List<Album> findByListeners(Listener listener);

}
