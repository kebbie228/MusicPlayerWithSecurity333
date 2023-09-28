package org.itstep.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="playlist")
public class Playlist {

    @Id
    @Column(name="playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "playlist_name")
    @NotEmpty(message = "playlist name should not be empty")
    @Size(min = 2, max = 30, message = "playlist name should be between 2 and 30 characters")
    private String playlistName;

    @Column(name = "photo_file_path")
    private String photoFilePath;

    @OneToMany(mappedBy = "playlist",  cascade = CascadeType.ALL)
    private List<Song> playlistSongs;


    @ManyToMany(mappedBy = "playlists",cascade = CascadeType.ALL)
    private List<Listener> listeners;

    public Playlist() {
    }

    public Playlist(int id, String playlistName, String photoFilePath) {
        this.id = id;
        this.playlistName = playlistName;
        this.photoFilePath = photoFilePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public List<Song> getPlaylistSongs() {
        return playlistSongs;
    }

    public void setPlaylistSongs(List<Song> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }
}
