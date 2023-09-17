package org.itstep.Repository;

import org.itstep.model.Listener;
import org.itstep.model.ListenerSong;
import org.itstep.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenerSongRepository extends JpaRepository<ListenerSong,Integer> {
    ListenerSong findByListenerAndSong(Listener listener, Song song);
}
