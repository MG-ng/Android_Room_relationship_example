package website.dango.roomrelationshipexamples.ui;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import website.dango.roomrelationshipexamples.Repository;
import website.dango.roomrelationshipexamples.pojos.Artist;
import website.dango.roomrelationshipexamples.pojos.Playlist;
import website.dango.roomrelationshipexamples.pojos.PlaylistWithSongs;
import website.dango.roomrelationshipexamples.pojos.Song;
import website.dango.roomrelationshipexamples.pojos.SongPlaylistCrossRef;
import website.dango.roomrelationshipexamples.pojos.SongWithArtists;
import website.dango.roomrelationshipexamples.pojos.SongWithPlaylists;

public class MainViewModel extends AndroidViewModel {
    private final String TAG = getClass().getSimpleName();

    private final Repository repo;


    private LiveData<List<Playlist>> allPlaylists;
    private LiveData<List<Song>> allSongs;

    private MutableLiveData<Song> selectedSong = new MutableLiveData<>();
    private MutableLiveData<Playlist> selectedPlaylist = new MutableLiveData<>();




    public MainViewModel( Application application ) {
        super( application );

        repo = Repository.getInstance( application );

        allPlaylists = repo.getAllPlaylists();
        allSongs = repo.getAllSongs();
    }

    public LiveData<Playlist> getSelectedPlaylist() {
        return selectedPlaylist;
    }

    LiveData<List<Playlist>> getAllPlaylists() {
        return allPlaylists;
    }

    LiveData<Song> getSelectedSong() {
        return selectedSong;
    }

    LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }


    LiveData<List<SongWithArtists>> getSongsWithArtists(){
        return repo.getSongsWithArtists();
    }
    LiveData<List<SongWithPlaylists>> getSongsWithPlaylists(){
        return repo.getSongsWithPlaylists();
    }

    LiveData<SongWithPlaylists> getSongWithPlaylists() {
        return repo.getSongWithPlaylists();
    }

    LiveData<PlaylistWithSongs> getPlaylistWithSongs() {
        return repo.getPlaylistWithSongs();
    }


    void insertSong( Song song ) {
        repo.insertSongs( song );
    }

    void insertArtists( List<Artist> artist ) {
        repo.insertArtists( artist.toArray( new Artist[]{} ) );
    }

    void insertPlaylist( Playlist playlist ) {
        repo.insertPlaylist( playlist );
    }

    void setSelectedSong( Song song ) {
        selectedSong.postValue( song );
    }

    void setSelectedPlaylist( Playlist playlist ) {
        selectedPlaylist.postValue( playlist );
    }

    void setSelectedQuerySong( Song song ) {
        repo.setSongForPlaylistSearch2( song );
    }

    void setSelectedQueryPlaylist( Playlist playlist ) {
        repo.setPlaylistForSongSearch( playlist );
    }



    void saveRelSongPlaylist() {
        if( selectedPlaylist.getValue() == null || selectedSong.getValue() == null ) {
            Log.e( TAG, "NULL!!" );
            return;
        }
        repo.insertRelSongPlaylist( new SongPlaylistCrossRef(
                selectedPlaylist.getValue().playlistName,
                selectedSong.getValue().songName ) );
    }


    void saveSongWithArtists( Song song, Artist... artists ) {
        repo.insertSongWithArtistsAndRel( song, artists );
    }

}
