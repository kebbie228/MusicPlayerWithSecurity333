package org.itstep.Service;

import org.itstep.Repository.AlbumRepository;
import org.itstep.Repository.ArtistRepository;
import org.itstep.Repository.ListenerRepository;
import org.itstep.model.Artist;
import org.itstep.model.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListenerService {
    private final ListenerRepository listenerRepository;

    @Autowired
    public ListenerService(ListenerRepository listenerRepository) {
        this.listenerRepository = listenerRepository;
    }



    public List<Listener> findAll(){
        return listenerRepository.findAll();
    }

    public Listener findById(int id){
        Optional<Listener> foundListener= listenerRepository.findById(id);
        return foundListener.orElse(null);
    }

    public void save(Listener listener){
        listenerRepository.save(listener);
    }
    public void update(int id, Listener updatedListener){
        updatedListener.setId(id);
        listenerRepository.save(updatedListener);
    }

    public void delete(int id){
        listenerRepository.deleteById(id);
    }
}
