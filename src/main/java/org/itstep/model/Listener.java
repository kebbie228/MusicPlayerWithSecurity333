package org.itstep.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "listener")
public class Listener {
        @Id
        @Column(name = "listener_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

    @Column(name = "listener_name")
    @NotEmpty(message = "Song name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
        private String listenerName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="listener_song",
            joinColumns = @JoinColumn(name="listener_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs;


    public Listener() {
    }

    public Listener(int id, String listenerName) {
        this.id = id;
        this.listenerName = listenerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }
}
