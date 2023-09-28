package org.itstep.services;

import org.itstep.model.Album;
import org.itstep.model.Artist;
import org.itstep.model.Listener;
import org.itstep.model.Playlist;
import org.itstep.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }


    public List<Playlist> findAll(){
        return playlistRepository.findAll();
    }
    public Playlist findById(int id){
        Optional<Playlist> foundAlbum= playlistRepository.findById(id);
        return foundAlbum.orElse(null);
    }

    public void save(Playlist playlist){
        playlistRepository.save(playlist);
    }
    public void update(int id, Playlist updatedPlaylist){
        updatedPlaylist.setId(id);
        playlistRepository.save(updatedPlaylist);
    }
    public void delete(int id){
        playlistRepository.deleteById(id);
    }

    public List<Playlist> findByPlaylistNameContainingIgnoreCase(String firstLetters){
        return  playlistRepository.findByPlaylistNameContainingIgnoreCase(firstLetters);
    }

    public List<Playlist> findByListeners(Listener listener){
        return playlistRepository.findByListeners(listener);
    };

}
