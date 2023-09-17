package org.itstep.services;


import org.itstep.model.Album;
import org.itstep.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
    public List<Album> findAll(){
        return albumRepository.findAll();
    }
    public Album findById(int id){
        Optional<Album> foundAlbum= albumRepository.findById(id);
        return foundAlbum.orElse(null);
    }
    public void save(Album album){
        albumRepository.save(album);
    }
    public void update(int id, Album updatedAlbum){
        updatedAlbum.setId(id);
        albumRepository.save(updatedAlbum);
    }
    public void delete(int id){
        albumRepository.deleteById(id);
    }

    public List<Album> findByAlbumNameContainingIgnoreCase(String firstLetters){
        return  albumRepository.findByAlbumNameContainingIgnoreCase(firstLetters);
    }
}
