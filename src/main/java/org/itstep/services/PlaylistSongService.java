package org.itstep.services;

import org.itstep.model.*;
import org.itstep.repositories.PlaylistSongRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistSongService {

    private final PlaylistSongRepository playlistSongRepository;

    public PlaylistSongService(PlaylistSongRepository playlistSongRepository) {
        this.playlistSongRepository = playlistSongRepository;
    }


    public PlaylistSong findByPlaylistAndSong (Song song, Playlist playlist){
        return playlistSongRepository.findByPlaylistAndSong(playlist,song);
    }
//    public PlaylistSong findByPlaylist (Playlist playlist){
//        return playlistSongRepository.findByPlaylist(playlist);
//    }
    public void delete(PlaylistSong playlistSong){
        playlistSongRepository.delete(playlistSong);
    }
    public void save(PlaylistSong playlistSong){
        playlistSongRepository.save(playlistSong);
    }

    public boolean hasSong(int playlistId, int songId) {
        return playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId);
    }
}
