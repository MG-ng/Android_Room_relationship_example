package website.dango.roomrelationshipexamples.pojos;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Playlist {

    @NonNull
    @PrimaryKey
    public String playlistName;

    public Playlist( @NonNull String playlistName ) {
        this.playlistName = playlistName;
    }

    @Override
    public String toString() {
        return "Playlist{ " +
                "playlistName='" + playlistName + '\'' +
                " }";
    }
}
