package website.dango.roomrelationshipexamples;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import website.dango.roomrelationshipexamples.daos.ArtistDao;
import website.dango.roomrelationshipexamples.daos.SACrossRefDao;
import website.dango.roomrelationshipexamples.daos.SPCrossRefDao;
import website.dango.roomrelationshipexamples.daos.PlaylistDao;
import website.dango.roomrelationshipexamples.daos.SongDao;
import website.dango.roomrelationshipexamples.pojos.Artist;
import website.dango.roomrelationshipexamples.pojos.Playlist;
import website.dango.roomrelationshipexamples.pojos.SongArtistCrossRef;
import website.dango.roomrelationshipexamples.pojos.SongPlaylistCrossRef;
import website.dango.roomrelationshipexamples.pojos.Song;

@Database( entities = { Playlist.class, Song.class, Artist.class, SongPlaylistCrossRef.class, SongArtistCrossRef.class },
        version = 1, exportSchema = false )
public abstract class AppDatabase extends RoomDatabase {

    public static final boolean DEBUG = false;

    private static AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool( NUMBER_OF_THREADS );


    public abstract SongDao songDao();
    public abstract ArtistDao artistDao();
    public abstract PlaylistDao playlistDao();
    public abstract SPCrossRefDao spCrossRefDao();
    public abstract SACrossRefDao saCrossRefDao();


    public static AppDatabase getDatabase( Context context ) {
        if( INSTANCE == null ) {
            synchronized( AppDatabase.class ) {

                INSTANCE = Room.databaseBuilder( context,
                        AppDatabase.class, "test_database" )
                        // TODO: write a migration
                        .fallbackToDestructiveMigration()
                        .addCallback( sRoomDatabaseCallback )
                        .build();
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen( @NonNull SupportSQLiteDatabase db ) {
            super.onOpen( db );
            new PopulateDbAsync( INSTANCE ).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private PlaylistDao playlistDao;
        private SongDao songDao;

        PopulateDbAsync( AppDatabase db ) {
            playlistDao = db.playlistDao();
            songDao = db.songDao();
        }

        @Override
        protected Void doInBackground( final Void... params ) {

            // Add something to the Database every time it is built
            // Only in the test phase!



            return null;
        }
    }

}
