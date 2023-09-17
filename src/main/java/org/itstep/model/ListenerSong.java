package org.itstep.model;

import javax.persistence.*;

@Entity
@Table(name = "listener_song", uniqueConstraints = {@UniqueConstraint(columnNames = {"listener_id", "song_id"})} )
public class ListenerSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listener_id")
    private Listener listener;
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
