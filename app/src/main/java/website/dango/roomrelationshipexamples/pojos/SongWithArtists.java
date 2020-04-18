package website.dango.roomrelationshipexamples.pojos;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SongWithArtists {

    @Embedded
    public Song song;

    @Relation(
            parentColumn = "songName",
            entityColumn = "artistName",
            associateBy = @Junction( SongArtistCrossRef.class )
    )

    public List<Artist> artists;

}
