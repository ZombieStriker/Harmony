package me.zombie_striker.harmony.resourcepack.daemon;

import me.zombie_striker.harmony.Harmony;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourcepackThread implements Runnable{

    private final Thread thread;
    private final Socket socket;

    public ResourcepackThread(Socket socket){
        this.socket = socket;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream(), "8859_1" ) );
            OutputStream out = socket.getOutputStream();
            PrintWriter pout = new PrintWriter( new OutputStreamWriter( out, "8859_1" ), true );
            String request = in.readLine();
            //onClientRequest( this, request );

            Matcher get = Pattern.compile("GET /?(\\S*).*").matcher( request );
            if ( get.matches() ) {
                request = get.group(1);
                File result = requestFileCallback( this, request );
                if ( result == null ) {
                    pout.println( "HTTP/1.0 400 Bad Request" );
                    //onRequestError( this, 400 );
                } else {
                    try {
                        // Writes zip files specifically; Designed for resource pack hosting
                        out.write( "HTTP/1.0 200 OK\r\n".getBytes() );
                        out.write( "Content-Type: application/zip\r\n".getBytes() );
                        out.write( ( "Content-Length: " + result.length() + "\r\n" ).getBytes() );
                        out.write( ( "Date: " + new Date().toGMTString() + "\r\n" ).getBytes() );
                        out.write( "Server: MineHttpd\r\n\r\n".getBytes() );
                        FileInputStream fis = new FileInputStream ( result );
                        byte [] data = new byte [ 64*1024 ];
                        for( int read; ( read = fis.read( data ) ) > -1; ) {
                            out.write( data, 0, read );
                        }
                        out.flush();
                        fis.close();
                        //onSuccessfulRequest( this, request );
                    } catch ( FileNotFoundException e ) {
                        pout.println( "HTTP/1.0 404 Object Not Found" );
                        //onRequestError( this, 404 );
                    }
                }
            } else {
                pout.println( "HTTP/1.0 400 Bad Request" );
                //onRequestError( this, 400 );
            }
            socket.close();
        } catch ( IOException e ) {
            System.out.println( "I/O error " + e );
        }
    }

    private File requestFileCallback(ResourcepackThread resourcepackThread, String request) {
        return Harmony.getInstance().getManager().getResourcepackFile();
    }
}
