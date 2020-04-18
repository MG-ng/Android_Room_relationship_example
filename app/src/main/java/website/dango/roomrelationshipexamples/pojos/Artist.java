package website.dango.roomrelationshipexamples.pojos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Artist {


    @NonNull
    @PrimaryKey
    public String artistName;

    public String realName;


    public Artist( @NonNull String artistName, String realName ) {
        this.artistName = artistName;
        this.realName = realName;
    }
}
