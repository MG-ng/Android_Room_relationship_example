package website.dango.roomrelationshipexamples.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.LinkedList;
import java.util.List;

import website.dango.roomrelationshipexamples.R;
import website.dango.roomrelationshipexamples.pojos.Artist;
import website.dango.roomrelationshipexamples.pojos.Playlist;
import website.dango.roomrelationshipexamples.pojos.Song;
import website.dango.roomrelationshipexamples.pojos.SongWithArtists;
import website.dango.roomrelationshipexamples.pojos.SongWithPlaylists;

public class MainFragment extends Fragment implements View.OnClickListener {
    private String TAG = getClass().getCanonicalName();

    private MainViewModel mViewModel;

    private List<SongWithArtists> songsWithArtists;
    private EditText etSongName, etSongArtist, etPlaylistName;
    private Button btSelectSong, btSelectPlaylist, btSelectSongOrPlaylist;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.main_fragment, container, false );

        view.findViewById( R.id.saveSong ).setOnClickListener( this );
        etSongName = view.findViewById( R.id.songName );
        etSongArtist = view.findViewById( R.id.songArtist );

        view.findViewById( R.id.savePlaylist ).setOnClickListener( this );
        etPlaylistName = view.findViewById( R.id.playListName );

        view.findViewById( R.id.saveRelation ).setOnClickListener( this );
        btSelectSong = view.findViewById( R.id.selectSong );
        btSelectSong.setOnClickListener( this );

        btSelectPlaylist = view.findViewById( R.id.selectPlaylist );
        btSelectPlaylist.setOnClickListener( this );

        btSelectSongOrPlaylist = view.findViewById( R.id.selectSongOrPlaylist );
        btSelectSongOrPlaylist.setOnClickListener( this );

        return view;
    }

    /**
     * This function is also handling the view of the data…
     * You could change that but, since this is a demo project, it is faster this way
     * And it doesn't really change the architecture because we are already in the view
     * @param savedInstanceState the system is handling that
     */
    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mViewModel = new ViewModelProvider( requireActivity() ).get( MainViewModel.class );


        mViewModel.getAllPlaylists().observe( getViewLifecycleOwner(), playlists -> {
            List<String> entries = new LinkedList<>();
            for( Playlist playlist : playlists ) {
                entries.add( playlist.playlistName );
            }
            logAllEntries( entries );
        } );

        mViewModel.getAllSongs().observe( getViewLifecycleOwner(), songs -> {
            List<String> entries = new LinkedList<>();
            for( Song song : songs ) {
                entries.add( song.songName );
            }
            logAllEntries( entries );
        } );

        mViewModel.getSelectedPlaylist().observe( getViewLifecycleOwner(), playlist -> {
            btSelectPlaylist.setText( playlist.playlistName );
        } );
        mViewModel.getSelectedSong().observe( getViewLifecycleOwner(), song -> {
            btSelectSong.setText( song.songName );
        });

        // view stuff
        mViewModel.getSongWithPlaylists().observe( getViewLifecycleOwner(), songWithPlaylists -> {
            btSelectSongOrPlaylist.setText( songWithPlaylists.song.songName );
            StringBuilder builder = new StringBuilder();
            for( Playlist playlist : songWithPlaylists.playlists ) {
                builder.append( playlist.playlistName );
                builder.append( "\n" );
            }
            ((TextView) getView().findViewById( R.id.databaseData )).setText( builder.toString() );
        } );

        mViewModel.getPlaylistWithSongs().observe( getViewLifecycleOwner(), playlistWithSongs -> {
            btSelectSongOrPlaylist.setText( playlistWithSongs.playlist.playlistName );
            StringBuilder builder = new StringBuilder();
            for( Song song : playlistWithSongs.songs ) {
                builder.append( song.songName );
                builder.append( " by: " );
                if( songsWithArtists == null ) {
                    Log.e( TAG, "NULL :/" );
                    continue;
                }
                for( SongWithArtists songWithArtists : songsWithArtists ) {
                    if( songWithArtists.song.songName.equals( song.songName ) ) {
                        for( Artist artist : songWithArtists.artists ) {
                            builder.append( artist.artistName );
                            builder.append( ", " );
                        }
                    }
                }
                builder.append( "\n" );
            }
            ((TextView) getView().findViewById( R.id.databaseData )).setText( builder.toString() );
        });

        mViewModel.getSongsWithArtists().observe( getViewLifecycleOwner(), list -> {
            songsWithArtists = list;
            for( SongWithArtists songWithArtists : list ) {
                Log.w( TAG, "In the list: " + songWithArtists.song.songName );
                for( Artist artist : songWithArtists.artists ) {
                    Log.w( TAG, artist.artistName );
                }
            }
        } );
        mViewModel.getSongsWithPlaylists().observe( getViewLifecycleOwner(), list -> {
            if( list == null ) {
                Log.e( TAG, "list is null!" );
                return;
            }
            for( SongWithPlaylists songWithPlaylists : list ) {
                Log.w( TAG, songWithPlaylists.song.songName + songWithPlaylists.playlists.size() );
            }
        } );
    }

    private void logAllEntries( List<String> entries ) {
        for( String entry : entries ) {
            Log.w( TAG, entry );
        }
    }

    // Extend the UI to make use of the counter and the real name… but this is a demo
    @Override
    public void onClick( View view ) {

        switch( view.getId() ) {
            case R.id.saveSong:
                Song song = new Song(
                        etSongName.getText().toString(),
                        0
                );

                String[] artists = etSongArtist.getText().toString().split( ", " );
                List<Artist> artistList = new LinkedList<>();
                for( String artistName : artists ) {
                    Artist artist = new Artist(
                            artistName,
                            "Real " + artistName
                    );
                    artistList.add( artist );
                }

                mViewModel.saveSongWithArtists( song, artistList.toArray( new Artist[]{} ) );
                break;
            case R.id.savePlaylist:
                mViewModel.insertPlaylist( new Playlist(
                        etPlaylistName.getText().toString()
                ) );
                break;
            case R.id.saveRelation:
                mViewModel.saveRelSongPlaylist();
                break;
            case R.id.selectSong:
                showSongSelection();
                break;
            case R.id.selectPlaylist:
                showPlaylistSelection();
                break;
            case R.id.selectSongOrPlaylist:
                showBothWithButtons();
                break;
        }

    }
    
    private void showBothWithButtons() {
        new AlertDialog.Builder( getContext() )
                .setTitle( "Select Song" )
                .setItems( songsToCharSequence( mViewModel.getAllSongs().getValue() ), ( dialogInterface, position ) -> {
                    mViewModel.setSelectedQuerySong( mViewModel.getAllSongs().getValue().get( position ) );
                } )
                .setNegativeButton( "Playlist", ( dialogInterface, i ) -> {
                    dialogInterface.dismiss();

                    new AlertDialog.Builder( getContext() )
                            .setTitle( "Select Playlist" )
                            .setItems( playlistToCharSequence( mViewModel.getAllPlaylists().getValue() ), ( innerDialogInterface, position ) -> {
                                mViewModel.setSelectedQueryPlaylist( mViewModel.getAllPlaylists().getValue().get( position ) );
                            } )
                            .show();
                } )
                .show();
    }

    private void showPlaylistSelection() {
        new AlertDialog.Builder( getContext() )
                .setTitle( "Select Playlist" )
                .setItems( playlistToCharSequence( mViewModel.getAllPlaylists().getValue() ), ( dialogInterface, position ) -> {
                    mViewModel.setSelectedPlaylist( mViewModel.getAllPlaylists().getValue().get( position ) );
                } )
                .show();
    }
    private void showSongSelection() {
        new AlertDialog.Builder( getContext() )
                .setTitle( "Select Song" )
                .setItems( songsToCharSequence( mViewModel.getAllSongs().getValue() ), ( dialogInterface, position ) -> {
                    mViewModel.setSelectedSong( mViewModel.getAllSongs().getValue().get( position ) );
                } )
                .show();
    }

    private CharSequence[] playlistToCharSequence( List<Playlist> playlists ) {
        List<CharSequence> tmp = new LinkedList<>();
        for( Playlist playlist : playlists ) {
            tmp.add( playlist.playlistName );
        }
        return tmp.toArray( new CharSequence[]{} );
    }

    private CharSequence[] songsToCharSequence( List<Song> songs ) {
        List<CharSequence> tmp = new LinkedList<>();
        for( Song song : songs ) {
            tmp.add( song.songName );
        }
        return tmp.toArray( new CharSequence[]{} );
    }

}
