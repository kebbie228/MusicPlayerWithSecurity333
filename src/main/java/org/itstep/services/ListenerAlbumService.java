package org.itstep.services;


import org.itstep.model.*;
import org.itstep.repositories.ListenerAlbumRepository;
import org.springframework.stereotype.Service;

@Service
public class ListenerAlbumService {
   private final ListenerAlbumRepository listenerAlbumRepository;

    public ListenerAlbumService(ListenerAlbumRepository listenerAlbumRepository) {
        this.listenerAlbumRepository = listenerAlbumRepository;
    }


    public ListenerAlbum findByListenerAndAlbum (Listener listener, Album album){
        return listenerAlbumRepository.findByListenerAndAlbum(listener,album);
    }
    public void delete(ListenerAlbum listenerAlbum){
        listenerAlbumRepository.delete(listenerAlbum);
    }
    public void save(ListenerAlbum listenerAlbum){
        listenerAlbumRepository.save(listenerAlbum);
    }

    public boolean hasAlbum(int listenerId, int albumId) {
        return listenerAlbumRepository.existsByListenerIdAndAlbumId(listenerId, albumId);
    }

}
