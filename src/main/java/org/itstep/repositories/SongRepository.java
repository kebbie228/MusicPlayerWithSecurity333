package org.itstep.repositories;

import org.itstep.model.Album;
import org.itstep.model.Listener;
import org.itstep.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song,Integer> {
    List<Song> findByAlbum(Album album);

    //dangerous
    List<Song> findByListeners(Listener listener);

  List<Song> findBySongNameContainingIgnoreCase(String firstLetters);

  //  List<Song> findBySongNameContainingIgnoreCase(String keyword);

}
