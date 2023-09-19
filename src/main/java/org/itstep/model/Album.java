package org.itstep.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="album")
public class Album {
    @Id
    @Column(name="album_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "album_name")
    @NotEmpty(message = "Album name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String albumName;

    @Column(name = "photo_file_path")
    private String photoFilePath;

    @OneToMany(mappedBy = "album",  cascade = CascadeType.ALL)
    private List<Song> albumSongs;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private Artist artist;


    @ManyToMany(mappedBy = "albums",cascade = CascadeType.ALL)
    private List<Listener> listeners;

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public Album() {
    }

    public Album(int id, String albumName, String photoFilePath) {
        this.id = id;
        this.albumName = albumName;
        this.photoFilePath = photoFilePath;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Song> getAlbumSongs() {
        return albumSongs;
    }

    public void setAlbumSongs(List<Song> albumSongs) {
        this.albumSongs = albumSongs;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", albumName='" + albumName + '\'' +
                '}';
    }
}
