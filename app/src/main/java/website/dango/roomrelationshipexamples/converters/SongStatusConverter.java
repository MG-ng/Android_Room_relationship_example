package website.dango.roomrelationshipexamples.converters;

import androidx.room.TypeConverter;

import website.dango.roomrelationshipexamples.pojos.SongStatus;


public class SongStatusConverter {


    @TypeConverter
    public static SongStatus toSongStatus( int code ) {
        switch( code ) {
            case 0:
                return SongStatus.online;
            case 1:
                return SongStatus.offline;
            case 2:
                return SongStatus.downloading;
        }
        throw new IllegalArgumentException( "no matching code" );
    }

    @TypeConverter
    public static int fromSongStatus( SongStatus status ) {
        if( status != null ) {
            return status.getCode();
        } else {
            return 0;
        }
    }

}
