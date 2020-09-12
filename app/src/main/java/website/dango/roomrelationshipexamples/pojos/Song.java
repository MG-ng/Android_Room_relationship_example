package website.dango.roomrelationshipexamples.pojos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import website.dango.roomrelationshipexamples.converters.SongStatusConverter;

@Entity
public class Song {

    @NonNull
    @PrimaryKey
    public String songName;

    public int listeningCounter;

    @TypeConverters( SongStatusConverter.class )
    public SongStatus songStatus;


    public Song( @NonNull String songName, int listeningCounter, SongStatus songStatus ) {
        this.songName = songName;
        this.listeningCounter = listeningCounter;
        this.songStatus = songStatus;
    }

}
