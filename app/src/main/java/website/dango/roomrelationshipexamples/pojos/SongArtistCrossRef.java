package website.dango.roomrelationshipexamples.pojos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.*;


@Entity( primaryKeys = { "songName", "artistName" },
        indices = { @Index( value = { "artistName", "songName" } ) },
        foreignKeys = {
                @ForeignKey(
                        entity = Artist.class,
                        parentColumns = "artistName",
                        childColumns = "artistName",
                        onDelete = CASCADE

                ),
                @ForeignKey(
                        entity = Song.class,
                        parentColumns = "songName",
                        childColumns = "songName",
                        onDelete = CASCADE
                )
        } )
public class SongArtistCrossRef {


    @NonNull
    public String songName;

    @NonNull
    public String artistName;

    public SongArtistCrossRef( @NonNull String songName, @NonNull String artistName ) {
        this.songName = songName;
        this.artistName = artistName;
    }
}
