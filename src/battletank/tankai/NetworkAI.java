package battletank.tankai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.SocketTimeoutException;

public class NetworkAI implements TankAI {
    
    protected Socket socket;
    protected int port;
    
    public boolean accept(int port, int timeout) {
        this.port = port;
        try {
            System.out.println("Waiting for client...");
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(timeout);
            this.socket = serverSocket.accept();
            serverSocket.close();
            System.out.println("connected");
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    @Override
    public void tick(battletank.objects.Tank tank) {
        if (this.socket.isClosed()) {
            if (!this.accept(this.port, 5000)) {
                System.out.println("Could not reconnect");
                return;
            }
        }
        
        
        try {
            
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            System.out.print("ping...");
            bw.write("ping");
            bw.newLine();
            bw.flush();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            
            socket.setSoTimeout(500);
            
            String line = "* no response *";
            try {
                line = br.readLine();
            } catch (SocketTimeoutException ste) {
                
            }
            
            System.out.println("response: " + line);
            
        } catch (Exception e) {
            try {
                this.socket.close();
            } catch (IOException ioe) {
            }
            System.out.println(e);
        }
    }
}

/**
 * Aanpak
 * 
 * 1. Turn-based: oneindig wachten
 * 2. Turn-based: timeout
 *    - indexeer/unieke ids ticks: gooi responses op oude ticks weg
 *    - reconnect with x time
 *    - a maximum of x reconnects
 * 3. Real time
 *    - Per NetworkAI een Thread die elke x tijd de toestand doorstuurt
 *    - Per NetworkAI een Thread die elke x tijd de toestand uitleest en verwerkt
 * 
 * Connectie
 * ? ServerSocket queue?
 * [/] Attempt to reconnect when disconnected
 * [ ] JSON
 * [ ] Accept up to x NetworkAIs, a start button starts the game
 */
