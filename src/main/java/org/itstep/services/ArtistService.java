package org.itstep.services;


import org.itstep.model.Album;
import org.itstep.model.Artist;
import org.itstep.repositories.AlbumRepository;
import org.itstep.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private  final AlbumRepository albumRepository;
    private  final SongService songService;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, AlbumRepository albumRepository, SongService songService) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.songService = songService;
    }

    public List<Artist> findAll(){
        return artistRepository.findAll();
    }
    public Artist findById(int id){
        Optional<Artist> foundArtist= artistRepository.findById(id);
        return foundArtist.orElse(null);
    }

    public void save(Artist artist){
        artistRepository.save(artist);
    }
    public void update(int id, Artist updatedArtist){
        updatedArtist.setId(id);
        artistRepository.save(updatedArtist);
    }

    public void delete(int id){
        artistRepository.deleteById(id);
    }

    public Artist findByAlbums(Album album){
      return artistRepository.findByAlbums(album);
    }

    public List<Artist> findByNickNameContainingIgnoreCase(String firstLetters){
        return  artistRepository.findByNickNameContainingIgnoreCase(firstLetters);
    }
}
