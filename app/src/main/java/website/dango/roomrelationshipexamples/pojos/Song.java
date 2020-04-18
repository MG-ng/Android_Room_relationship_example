package website.dango.roomrelationshipexamples.pojos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Song {

    @NonNull
    @PrimaryKey
    public String songName;

    public int listeningCounter;


    public Song( @NonNull String songName, int listeningCounter ) {
        this.songName = songName;
        this.listeningCounter = listeningCounter;
    }

}
