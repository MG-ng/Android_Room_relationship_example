package website.dango.roomrelationshipexamples;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import website.dango.roomrelationshipexamples.daos.ArtistDao;
import website.dango.roomrelationshipexamples.daos.PlaylistDao;
import website.dango.roomrelationshipexamples.daos.SACrossRefDao;
import website.dango.roomrelationshipexamples.daos.SPCrossRefDao;
import website.dango.roomrelationshipexamples.daos.SongDao;
import website.dango.roomrelationshipexamples.pojos.Artist;
import website.dango.roomrelationshipexamples.pojos.Playlist;
import website.dango.roomrelationshipexamples.pojos.PlaylistWithSongs;
import website.dango.roomrelationshipexamples.pojos.Song;
import website.dango.roomrelationshipexamples.pojos.SongArtistCrossRef;
import website.dango.roomrelationshipexamples.pojos.SongPlaylistCrossRef;
import website.dango.roomrelationshipexamples.pojos.SongWithArtists;
import website.dango.roomrelationshipexamples.pojos.SongWithPlaylists;

import static website.dango.roomrelationshipexamples.AppDatabase.databaseExecutor;
import static website.dango.roomrelationshipexamples.AppDatabase.getDatabase;

public class Repository {
    private static final String TAG = "control repo";

    private AppDatabase db;
    private static Repository instance = null;

    private SongDao songDao;
    private ArtistDao artistDao;
    private PlaylistDao playlistDao;
    private SPCrossRefDao spCrossRefDao;
    private SACrossRefDao saCrossRefDao;

    private static MutableLiveData<Integer> lastUpdatedRows = new MutableLiveData<>();

    private LiveData<List<Song>> allSongs;
    private LiveData<List<Artist>> allArtists;
    private LiveData<List<Playlist>> allPlaylists;
    private LiveData<List<SongWithArtists>> songsWithArtists;
    private LiveData<List<SongWithPlaylists>> songsWithPlaylists;

    private MutableLiveData<SongWithPlaylists> songWithPlaylists = new MutableLiveData<>();
    private MutableLiveData<PlaylistWithSongs> playlistWithSongs = new MutableLiveData<>();

    public static Repository getInstance( Context context ) {
        if( instance == null ) {
            instance = new Repository( context );
        }
        return instance;
    }

    private Repository( Context context ) {
        db = getDatabase( context );

        songDao = db.songDao();

        playlistDao = db.playlistDao();
        spCrossRefDao = db.spCrossRefDao();
        saCrossRefDao = db.saCrossRefDao();
        artistDao = db.artistDao();

        allSongs = songDao.getAllSongs();
        allArtists = artistDao.getAllArtists();
        allPlaylists = playlistDao.getAllPlaylists();

        songsWithArtists = songDao.getSongsWithArtists();
        songsWithPlaylists = songDao.getSongsWithPlaylists();
    }

    public LiveData<List<SongWithArtists>> getSongsWithArtists() {
        return songsWithArtists;
    }

    public LiveData<List<SongWithPlaylists>> getSongsWithPlaylists() {
        return songsWithPlaylists;
    }



    // TODO: Option 1
    public void setSongForPlaylistSearch( Song song ) {
        databaseExecutor.execute( () -> {
            List<SongWithPlaylists> list = songDao.getSongsWithPlaylists().getValue();
            if( list == null ) return;
            for( SongWithPlaylists tmp : list ) {
                if( song.songName.equals( tmp.song.songName ) ) {
                    songWithPlaylists.postValue( tmp );
                }
            }
        } );
    }

    // TODO: Option 2
    public void setSongForPlaylistSearch2( Song song ) {
        databaseExecutor.execute( () -> {
            SongWithPlaylists result = songDao.getSongWithPlaylists( song.songName );
            songWithPlaylists.postValue( result );
        } );
    }

    public void setPlaylistForSongSearch( Playlist playlist ) {
        databaseExecutor.execute( () -> {
            List<PlaylistWithSongs> list = playlistDao.getPlaylistsWithSongs();
            for( PlaylistWithSongs tmp : list ) {
                if( playlist.playlistName.equals( tmp.playlist.playlistName ) ) {
                    playlistWithSongs.postValue( tmp );
                }
            }
        } );
    }

    public LiveData<SongWithPlaylists> getSongWithPlaylists() {
        return songWithPlaylists;
    }

    public LiveData<PlaylistWithSongs> getPlaylistWithSongs() {
        return playlistWithSongs;
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    public LiveData<List<Playlist>> getAllPlaylists() {
        return allPlaylists;
    }


    public void deleteSongsByIds( String... names ) {
        databaseExecutor.execute( () -> {
            lastUpdatedRows.postValue( songDao.deleteSongById( names ) );
        } );
    }


    public void insertSongs( Song... songs ) {
        databaseExecutor.execute( () -> {
            Log.w( TAG, "Insert Song(s): " + songs.length + songDao.insert( songs ) );
        } );
    }

    public void insertArtists( Artist... artists ) {
        databaseExecutor.execute( () -> {
            Log.w( TAG, "Insert Artist(s): " + artists.length + artistDao.insert( artists ) );
        } );
    }


    public void insertPlaylist( Playlist playlist ) {
        databaseExecutor.execute( () -> {
            Log.w( TAG, "Insert Playlist: " + playlist.playlistName +
                    playlistDao.insert( playlist ) );
        } );
    }


    public void insertRelSongPlaylist( SongPlaylistCrossRef crossRef ) {
        databaseExecutor.execute( () -> {
            Log.w( TAG, "Insert CrossRef: " + crossRef.songName +
                    crossRef.playlistName + spCrossRefDao.insert( crossRef ) );
        } );
    }


    /**
     * You need this function to check that the relation is inserted after the individual objects
     *
     * Really: Whenever you want to insert in specific order, for example because one object has foreign keys,
     *  you really want to make sure that the background tasks are not mixing the order up!
     *
     * @param song to insert
     * @param artists to insert (including the refs)
     */
    public void insertSongWithArtistsAndRel( Song song, Artist... artists ) {
        databaseExecutor.execute( () -> {
            songDao.insert( song );
            artistDao.insert( artists );
            for( Artist artist : artists ) {
                saCrossRefDao.insert( new SongArtistCrossRef(
                        song.songName,
                        artist.artistName
                ) );
            }
        } );
    }


}