package website.dango.roomrelationshipexamples;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import website.dango.roomrelationshipexamples.ui.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main_activity );

        if( savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, MainFragment.newInstance() )
                    .commitNow();
        }
    }
}
