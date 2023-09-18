package org.itstep.services;


import org.itstep.model.Listener;
import org.itstep.model.ListenerSong;
import org.itstep.model.Song;
import org.itstep.repositories.ListenerSongRepository;
import org.springframework.stereotype.Service;

@Service
public class ListenerSongService {
   private final ListenerSongRepository listenerSongRepository;

    public ListenerSongService(ListenerSongRepository listenerSongRepository) {
        this.listenerSongRepository = listenerSongRepository;
    }

    public ListenerSong findByListenerAndSong ( Song song,Listener listener){
        return listenerSongRepository.findByListenerAndSong(listener,song);
    }
    public void delete(ListenerSong listenerSong){
        listenerSongRepository.delete(listenerSong);
    }
    public void save(ListenerSong listenerSong){
        listenerSongRepository.save(listenerSong);
    }

    public boolean hasSong(int listenerId, int songId) {
        return listenerSongRepository.existsByListenerIdAndSongId(listenerId, songId);
    }

}
