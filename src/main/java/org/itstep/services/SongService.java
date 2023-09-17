package org.itstep.Service;

import org.itstep.Repository.SongRepository;
import org.itstep.model.Album;
import org.itstep.model.Artist;
import org.itstep.model.Listener;
import org.itstep.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final AlbumService albumService;

    @Autowired
    public SongService(SongRepository songRepository, AlbumService albumService) {
        this.songRepository = songRepository;
        this.albumService = albumService;
    }




    public List<Song> findAll(){
        return songRepository.findAll();
    }
    public Song findById(int id){
        Optional<Song> foundSong= songRepository.findById(id);
        return foundSong.orElse(null);
    }
    public void save(Song song){
        songRepository.save(song);
    }
    public void update(int id, Song updatedSong){
        updatedSong.setId(id);
        songRepository.save(updatedSong);
    }

    public void delete(int id){

       Optional<Song> song= songRepository.findById(id);

   Album album=song.get().getAlbum();
  if( album.getAlbumSongs().size()>1){
      //Удаление песни из альбома если в альбоме несколько песен
      songRepository.deleteById(id);
  }
  else {
      //Удаление песни и альбома если в альбоме одна песня
      albumService.delete(album.getId());
  }

    }

    public List<Song> findByAlbum(Album album){
      return songRepository.findByAlbum(album);
    }


//    public List<Song> findBySongNameStartingWith(String firstLetters){
//        return  songRepository.findBySongNameStartingWith(firstLetters);
//    }
    public List<Song> findBySongNameContainingIgnoreCase(String keyword) {
        return songRepository.findBySongNameContainingIgnoreCase(keyword);
    }
    //dangerous
   public List<Song> findByListeners(Listener listener){
        return songRepository.findByListeners(listener);
   };
}
