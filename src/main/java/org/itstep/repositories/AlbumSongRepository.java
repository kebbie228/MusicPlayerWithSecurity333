package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlbumSongRepository extends JpaRepository<AlbumSong,Integer> {
    AlbumSong findByAlbumAndSong(Album album, Song song);
    //    PlaylistSong findByPlaylist(Playlist playlist);
    boolean existsByAlbumIdAndSongId(int albumId, int songId);

}
