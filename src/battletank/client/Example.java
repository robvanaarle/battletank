package battletank.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Example {
    
    protected String host;
    protected int port;
    protected Socket socket;
    
    public Example() {
    }
    
    public void connect(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        
        this.socket = new Socket(host, port);
    }
    
    public void start() throws IOException {
        
        int index = 0;
        
        for (int i = 0; i < 5; i++) {
        
            while (this.socket.isConnected() && !this.socket.isClosed()) {
                index++;
                
                BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                String line = br.readLine();
                System.out.println("request: " + line);


                if (index == 2) {
                    try {
                        Thread.currentThread().join(1000);
                    } catch (InterruptedException e) {

                    }
                }

                if (index == 3) {
                    this.socket.close();
                    break;
                }

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
                bw.write("pong " + index);
                bw.newLine();
                bw.flush();

                
            }
            
            try {
                Thread.currentThread().join(1000);
                System.out.print("Reconnecting...");
                this.connect(host, port);
                System.out.println("connected");
            } catch (IOException e) {
                System.out.println(e);
            } catch (Exception e2) { }
        }

    }
    
    public static void main(String[] args) throws IOException {
        Example client = new Example();

        System.out.println("Connecting...");
        client.connect("127.0.0.1", 9000);
        System.out.println("success");
        
        client.start();
    }
    
    
}
