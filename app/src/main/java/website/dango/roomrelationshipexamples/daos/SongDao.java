package website.dango.roomrelationshipexamples.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import website.dango.roomrelationshipexamples.pojos.Song;
import website.dango.roomrelationshipexamples.pojos.SongArtistCrossRef;
import website.dango.roomrelationshipexamples.pojos.SongWithArtists;
import website.dango.roomrelationshipexamples.pojos.SongWithPlaylists;

@Dao
public interface SongDao extends BaseDao<Song> {



    @Query( "DELETE FROM song WHERE songName in (:names)" )
    public int deleteSongById( String... names );


    @Query( "SELECT * FROM song" )
    LiveData<List<Song>> getAllSongs();


    @Transaction
    @Query("SELECT * FROM song")
    public LiveData<List<SongWithArtists>> getSongsWithArtists();


    @Transaction
    @Query("SELECT * FROM songartistcrossref")
    public List<SongArtistCrossRef> getSACrossRef();


    @Transaction
    @Query("SELECT * FROM song")
    public LiveData<List<SongWithPlaylists>> getSongsWithPlaylists();


    @Transaction
    @Query( "SELECT * FROM song WHERE songName = :name" )
    public SongWithPlaylists getSongWithPlaylists( String name );


}
