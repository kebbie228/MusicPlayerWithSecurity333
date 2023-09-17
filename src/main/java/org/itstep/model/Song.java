package org.itstep.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @Column(name = "song_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "song_name")
    @NotEmpty(message = "Song name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String songName;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "song_year")
    private int songYear;

    @ManyToOne()
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private Artist artist;

    @ManyToOne()
    @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    private Album album;

    @Column(name = "audio_file_path")
    private String audioFilePath;
   @Column(name = "photo_file_path")
   private String photoFilePath;

   @ManyToMany(mappedBy = "songs",cascade = CascadeType.ALL)
   private List<Listener> listeners;

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public Song() {
    }
//потом поменятт так как добавляешь колонки новые


    public Song(int id, String songName, int songYear, Artist artist) {
        this.id = id;
        this.songName = songName;
        this.songYear = songYear;
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongYear() {
        return songYear;
    }

    public void setSongYear(int songYear) {
        this.songYear = songYear;
    }


    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", songYear=" + songYear +
                '}';
    }
}
