package org.itstep.repositories;

import org.itstep.model.Album;
import org.itstep.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Integer> {
 Artist findByAlbums(Album album);
 List<Artist> findByNickNameContainingIgnoreCase(String firstLetters);
}
