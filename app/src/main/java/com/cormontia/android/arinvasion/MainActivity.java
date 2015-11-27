package com.cormontia.android.arinvasion;

import android.os.Bundle;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

//public class MainActivity extends AppCompatActivity
public class MainActivity extends CardboardActivity implements CardboardView.StereoRenderer
{

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardboardView cardboardView = (CardboardView) findViewById( R.id.view );
        cardboardView.setEGLConfigChooser( 8 , 8, 8, 8, 16, 0 );
        //cardboardView.setRestoreGLStateEnabled( false );
        cardboardView.setRenderer( this );
        setCardboardView( cardboardView );
    }

    @Override
    public void onNewFrame( HeadTransform ht )
    {
        //TODO?+
    }

    @Override
    public void onDrawEye( Eye eye )
    {
        //TODO?+
    }

    @Override
    public void onFinishFrame( Viewport viewport )
    {
        //TODO?+
    }

    @Override
    public void onSurfaceCreated( EGLConfig config )
    {
        //TODO?+
    }

    @Override
    public void onSurfaceChanged( int p1, int p2 )
    {
        //TODO?+
    }

    @Override
    public void onRendererShutdown( )
    {
        //TODO?+
    }
}
