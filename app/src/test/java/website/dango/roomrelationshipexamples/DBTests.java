package website.dango.roomrelationshipexamples;

import android.content.Context;
import android.os.Build;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith( RobolectricTestRunner.class )
@Config( sdk = Build.VERSION_CODES.P, manifest = "AndroidManifest.xml" )
public class DBTests {


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();


        Repository repo = Repository.getInstance( appContext );



        assertEquals( "website.dango.roomrelationshipexamples", appContext.getPackageName() );
    }
}
