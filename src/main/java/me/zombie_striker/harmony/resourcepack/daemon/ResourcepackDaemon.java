package me.zombie_striker.harmony.resourcepack.daemon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ResourcepackDaemon implements Runnable{

    private final int port;
    private final Thread thread;
    private boolean running = true;

    public ResourcepackDaemon(int port){
        this.port = port;
        this.thread = new Thread(this);
    }

    public void shutdown() {
        running = false;
        thread.stop();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            while(running){
                Socket socket = ss.accept();
                ResourcepackThread thread = new ResourcepackThread(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
