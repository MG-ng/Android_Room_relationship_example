package website.dango.roomrelationshipexamples.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import website.dango.roomrelationshipexamples.pojos.Artist;

@Dao
public interface ArtistDao extends BaseDao<Artist> {


    @Query( "SELECT * FROM Artist" )
    public LiveData<List<Artist>> getAllArtists();




}
