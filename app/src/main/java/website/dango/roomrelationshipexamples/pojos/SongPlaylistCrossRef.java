package website.dango.roomrelationshipexamples.pojos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.*;


@Entity( primaryKeys = { "playlistName", "songName" },
        indices = { @Index( value = "songName" ) },
        foreignKeys = {
                @ForeignKey(
                        entity = Playlist.class,
                        parentColumns = "playlistName",
                        childColumns = "playlistName",
                        onDelete = CASCADE

                ),
                @ForeignKey(
                        entity = Song.class,
                        parentColumns = "songName",
                        childColumns = "songName",
                        onDelete = CASCADE
                )
        } )
public class SongPlaylistCrossRef {


    @NonNull
    public String playlistName;

    @NonNull
    public String songName;

    public SongPlaylistCrossRef( @NonNull String playlistName, @NonNull String songName ) {
        this.playlistName = playlistName;
        this.songName = songName;
    }
}
