package org.itstep.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "listener")
public class Listener {
        @Id
        @Column(name = "listener_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

    @Column(name = "username")
    @NotEmpty(message = "Song name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
        private String listenerName;

    @Column(name = "role")
    @NotNull
    private String role;
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="listener_song",
            joinColumns = @JoinColumn(name="listener_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )

    private List<Song> songs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="listener_album",
            joinColumns = @JoinColumn(name="listener_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private List<Album> albums;

    public Listener() {
    }

    public Listener(int id, String listenerName) {
        this.id = id;
        this.listenerName = listenerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
