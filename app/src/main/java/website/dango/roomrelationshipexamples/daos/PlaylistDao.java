package website.dango.roomrelationshipexamples.daos;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import website.dango.roomrelationshipexamples.pojos.Playlist;
import website.dango.roomrelationshipexamples.pojos.PlaylistWithSongs;


@Dao
public interface PlaylistDao extends BaseDao<Playlist> {



        @Query( "SELECT * FROM Playlist" )
        LiveData<List<Playlist>> getAllPlaylists();


        @Query( "SELECT * FROM Playlist ORDER BY playlistName ASC LIMIT 1" )
        Playlist getFirstPlaylist();


        @Transaction
        @Query( "SELECT * FROM Playlist" )
        public List<PlaylistWithSongs> getPlaylistsWithSongs();


}
