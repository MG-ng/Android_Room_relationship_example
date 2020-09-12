package website.dango.roomrelationshipexamples.pojos;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SongWithPlaylists {

    @Embedded
    public Song song;

    @Relation(
            parentColumn = "songName",
            entityColumn = "playlistName",
            associateBy = @Junction( SongPlaylistCrossRef.class )
    )
    public List<Playlist> playlists;

}
