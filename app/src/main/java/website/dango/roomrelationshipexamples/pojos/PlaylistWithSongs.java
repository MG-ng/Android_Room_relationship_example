package website.dango.roomrelationshipexamples.pojos;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class PlaylistWithSongs {

    @Embedded
    public Playlist playlist;

    @Relation(
            parentColumn = "playlistName",
            entityColumn = "songName",
            associateBy = @Junction( SongPlaylistCrossRef.class )
    )
    public List<Song> songs;

}
