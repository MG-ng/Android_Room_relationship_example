package website.dango.roomrelationshipexamples.pojos;

public enum SongStatus {


    online( 0 ), offline( 1 ), downloading( 2 );


    public final int code;

    SongStatus( int code ) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
