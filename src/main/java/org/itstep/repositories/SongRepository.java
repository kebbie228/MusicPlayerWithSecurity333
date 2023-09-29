package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song,Integer> {
    List<Song> findByAlbum(Album album);
   // List<Song> findByPlaylist(Playlist playlist);

    //dangerous
    List<Song> findByListeners(Listener listener);

  List<Song> findBySongNameContainingIgnoreCase(String firstLetters);
    List<Song> findByArtist(Artist artist);


    //  List<Song> findBySongNameContainingIgnoreCase(String keyword);

}
