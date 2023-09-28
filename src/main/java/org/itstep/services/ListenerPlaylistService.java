package org.itstep.services;

import org.itstep.model.*;
import org.itstep.repositories.ListenerPlaylistRepository;
import org.springframework.stereotype.Service;

@Service
public class ListenerPlaylistService {


    private final ListenerPlaylistRepository listenerPlaylistRepository;

    public ListenerPlaylistService(ListenerPlaylistRepository listenerPlaylistRepository) {
        this.listenerPlaylistRepository = listenerPlaylistRepository;
    }


    public ListenerPlaylist findByListenerAndPlaylist (Listener listener, Playlist playlist){
        return listenerPlaylistRepository.findByListenerAndPlaylist(listener,playlist);
    }
    public void delete(ListenerPlaylist listenerPlaylist){
        listenerPlaylistRepository.delete(listenerPlaylist);
    }
    public void save(ListenerPlaylist listenerPlaylist){
        listenerPlaylistRepository.save(listenerPlaylist);
    }

    public boolean hasPlaylist(int listenerId, int playlistId) {
        return listenerPlaylistRepository.existsByListenerIdAndPlaylistId(listenerId, playlistId);
    }

}
