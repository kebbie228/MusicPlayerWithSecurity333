package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong,Integer>{
    PlaylistSong findByPlaylistAndSong(Playlist playlist, Song song);
    boolean existsByPlaylistIdAndSongId(int playlistId, int songId);

}
