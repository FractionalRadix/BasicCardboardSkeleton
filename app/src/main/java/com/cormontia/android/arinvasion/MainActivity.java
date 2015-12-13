package com.cormontia.android.arinvasion;

import android.content.Context;
import android.opengl.GLES20;
import android.os.Bundle;
import android.util.Log;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.khronos.egl.EGLConfig;

class Constants
{
    /** A constant: the size of a `float`, in bytes. */
    public static final int FLOAT_SIZE = 4;
    public static final int COORDS_PER_VERTEX = 3; /* x, y, z */
    public static final int COLORDATA_PER_VERTEX = 4; /* A, R, G, B */
}

public class MainActivity extends CardboardActivity implements CardboardView.StereoRenderer
{
    /** The OpenGL ES program reference. */
    private int program;

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
        //TODO!+ We can compile the shader, but the matrices aren't initialized or bound.
        Log.i( "onSurfaceCreated", "About to call OpenGLESHelper.compileOpenGLESprogram(...)." );
        program = OpenGLESHelper.compileOpenGLESprogram( this );
        Log.i( "onSurfaceCreated", "Returned from call to OpenGLESHelper.compileOpenGLESprogram(...).");
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

class OpenGLESHelper
{
    public static int compileOpenGLESprogram( Context ctx )
    {
        Log.i( "SHADERS", "About to call glCreateProgram( )." );
        int program = GLES20.glCreateProgram();
        Log.i( "SHADERS", "after glCreateProgram." );

        // Load and link the Vertex Shader.
        String vertexShaderCode = OpenGLESHelper.readRawResourceFile( ctx, R.raw.vertex );

        Log.i( "SHADERS", "About to all glCreateShader." );
        int vertexShader = GLES20.glCreateShader( GLES20.GL_VERTEX_SHADER );
        checkGLError( "SHADERS", "after glCreateShader" );
        Log.i( "SHADERS", "after glCreateShader" );

        Log.i( "SHADERS", "About to call glShaderSource." );
        GLES20.glShaderSource( vertexShader, vertexShaderCode );
        checkGLError( "SHADERS", "after glShaderSource." );
        Log.i( "SHADERS", "after glShaderSource( vertexShader, vertexShaderCode )" );

        Log.i( "SHADERS", "About to call glCompileShader." );
        GLES20.glCompileShader( vertexShader );
        checkGLError( "SHADERS", "after compiling vertex shader." );
        Log.i( "SHADERS", "after compiling vertex shader." );

        Log.i( "SHADERS", "About to attach vertexShader to program." );
        GLES20.glAttachShader( program, vertexShader );
        checkGLError( "SHADERS", "after attaching vertex shader to program." );
        Log.i( "SHADERS", "after glAttachShader( program, vertexShader )." );

        // Load and link the Fragment Shader.

        String fragmentShaderCode = OpenGLESHelper.readRawResourceFile( ctx, R.raw.fragment );
        Log.i( "SHADERS", "About to call glCreateShader( GLES20.GL_FRAGMENT_SHADER" );
        int fragmentShader = GLES20.glCreateShader( GLES20.GL_FRAGMENT_SHADER );
        checkGLError( "SHADERS", "After call to glCreateShader( GLES20.GL_FRAGMENT_SHADER" );
        Log.i( "SHADERS", "After call glCreateShader( GLES20.GL_FRAGMENT_SHADER" );

        Log.i( "SHADERS", "About to call glShaderSource( fragmentShader, fragmentShaderCode." );
        GLES20.glShaderSource( fragmentShader, fragmentShaderCode );
        checkGLError( "SHADERS", "After glShaderSource( fragmentShader, fragmentShaderCode." );
        Log.i( "SHADERS", "After glShaderSource( fragmentShader, fragmentShaderCode." );

        Log.i( "SHADERS", "About to call glCompileShader( fragmentShader )." );
        GLES20.glCompileShader( fragmentShader );
        checkGLError( "SHADERS", "After glCompileShader( fragmentShader )." );
        Log.i( "SHADERS", "After glCompileShader( fragmentShader )." );

        Log.i( "SHADERS", "About to call glAttachShader( program, fragmentShader )." );
        GLES20.glAttachShader( program, fragmentShader );
        checkGLError( "SHADERS", "After glAttachShader( program, fragmentShader )." );
        Log.i( "SHADERS", "After glAttachShader( program, fragmentShader )." );


//        System.out.println( vertexShaderCode );
//        System.out.println( fragmentShaderCode );

        // Link and use the program.

        Log.i( "SHADERS", "About to call glLinkProgram." );
        GLES20.glLinkProgram( program );
        checkGLError( "SHADERS", "After linking the program." );
        Log.i( "SHADERS", "after glLinkProgram." );

        Log.i( "SHADERS", "About to call glUseProgram." );
        GLES20.glUseProgram( program );
        checkGLError( "SHADERS", "After calling glUseProgram." );
        Log.i( "SHADERS", "After calling glUseProgram." );

        return program;
    }

    /** Reads the content of the resource file identified by <code>id</code>.
     *
     * @param ctx The Context to which the resource belongs (usually the Main Activity).
     * @param id Resource ID of the file to be read.
     * @return The contents of the resource file, or <code>null</code> if something went wrong.
     */
    public static String readRawResourceFile( Context ctx, int id )
    {
        InputStream vertexShaderInputStream = ctx.getResources( ).openRawResource( id );
        BufferedReader reader = new BufferedReader( new InputStreamReader( vertexShaderInputStream ) );

        StringBuffer res = new StringBuffer( );
        try
        {
            String line = "";
            while ( ( line = reader.readLine( ) ) != null )
            {
                res.append( line ).append( '\n' );
            }

        } catch ( IOException exc )
        {
            //TODO?+
            exc.printStackTrace( );
            res = null;
        }

        return ( res == null ? null : res.toString( ) );
    }

    /**
     * Checks if we've had an error inside of OpenGL ES, and if so what that error is.
     *
     * @param tag Tag to use for the LogCat.
     * @param label Label to report in case of error.
     */
    public static void checkGLError( String tag, String label )
    {
        int error;
        while ( ( error = GLES20.glGetError( ) ) != GLES20.GL_NO_ERROR )
        {
            Log.e( tag, label + ": glError " + error);
            throw new RuntimeException( label + ": glError " + error );
        }
    }

}