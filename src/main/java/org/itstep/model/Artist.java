package org.itstep.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @Column(name = "artist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nick_name")
    @NotEmpty(message = "Nick name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String nickName;

    @Column(name = "photo_file_path")
    private String photoFilePath;

    @OneToOne()
    @JoinColumn(name = "listener_id",referencedColumnName ="listener_id")
    private Listener listener;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
  private  List<Song> songs;

    @OneToMany(mappedBy = "artist",cascade = CascadeType.ALL)
    private  List<Album> albums;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public Artist() {
    }


    public Artist(int id, String nickName) {
        this.id = id;
        this.nickName = nickName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }



    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return nickName;

    }
}
