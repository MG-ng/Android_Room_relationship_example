package website.dango.roomrelationshipexamples;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import website.dango.roomrelationshipexamples.pojos.Song;
import website.dango.roomrelationshipexamples.pojos.SongStatus;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith( AndroidJUnit4.class )
public class DBTests {
    public static final String TAG = "TEST";


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Repository repo = Repository.getInstance( appContext );


        repo.insertSongs( new Song( "Robot Boy", 0, SongStatus.online ),
                new Song( "Witches", 0, SongStatus.online ) );

        try {
            Thread.sleep( 1000 );
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }

        printLastUpdatedRows();


        repo.deleteSongsByNames( "Robot Boy", "Witches" );

        try {
            Thread.sleep( 1000 );
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }

        assertEquals( "website.dango.roomrelationshipexamples", appContext.getPackageName() );
    }


    private void printLastUpdatedRows() {
        if( Repository.getLastUpdatedRows().getValue() != null ) {

            for( Long id : Repository.getLastUpdatedRows().getValue() ) {
                Log.i( TAG, "Id: " + id );
            }
        } else
            Log.e( TAG, "No updated Rows yet" );
    }

}
