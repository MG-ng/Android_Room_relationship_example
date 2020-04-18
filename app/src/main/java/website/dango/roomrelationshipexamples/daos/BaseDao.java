package website.dango.roomrelationshipexamples.daos;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

public interface BaseDao<T> {


    /**
     * @param ts to insert
     * @return the rowId(s) inserted
     */
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    public List<Long> insert( T[] ts );

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    public long insert( T t );

    /**
     * @param ts Objects to insert
     * @return the amount of rows updated
     */
    @Update
    public int update( T ts );

    /**
     * @param ts Objects to delete
     * @return the amount of rows deleted
     */
    @Delete
    public int delete( T ts );

}
