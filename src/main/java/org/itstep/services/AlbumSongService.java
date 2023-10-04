package org.itstep.services;

import org.itstep.model.*;
import org.itstep.repositories.AlbumSongRepository;
import org.springframework.stereotype.Service;



@Service
public class AlbumSongService {

    private final AlbumSongRepository albumSongRepository;

    public AlbumSongService(AlbumSongRepository albumSongRepository) {
        this.albumSongRepository = albumSongRepository;
    }


    public AlbumSong findByAlbumAndSong (Song song, Album album){
        return albumSongRepository.findByAlbumAndSong(album,song);
    }
    //    public PlaylistSong findByPlaylist (Playlist playlist){
//        return playlistSongRepository.findByPlaylist(playlist);
//    }
    public void delete(AlbumSong albumSong){
        albumSongRepository.delete(albumSong);
    }
    public void save(AlbumSong albumSong){
        albumSongRepository.save(albumSong);
    }

    public boolean hasSong(int albumId, int songId) {
        return albumSongRepository.existsByAlbumIdAndSongId(albumId, songId);
    }
}
