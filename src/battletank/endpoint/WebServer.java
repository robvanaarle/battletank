/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.endpoint;

import battletank.Endpoint;
import battletank.math.Point2D;
import battletank.objects.Tank;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import com.sun.net.httpserver.HttpServer;
import static com.sun.net.httpserver.HttpServer.create;
import java.awt.Color;
import static java.awt.Color.blue;
import static java.awt.Color.green;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.random;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author raarle
 */
public class WebServer implements Runnable {
    protected int port;
    protected Endpoint endpoint;
    protected Gson gson = new Gson();
    protected ArrayList<battletank.objects.Tank> tanks = new ArrayList<>();
    protected Random rand = new Random();
    
    public WebServer(Endpoint endpoint, int port) {
        this.port = port;
        this.endpoint = endpoint;
    }
    
    @Override
    public void run() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/addplayer", new AddPlayerHandler());
            server.createContext("/tick", new TickHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("WebServer started on port " + port);
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
    
    class EndpointHttpHandler implements HttpHandler {        
        protected String getRequestBody(HttpExchange he) throws IOException {
            // read body
            StringBuilder body = new StringBuilder();
            try (InputStreamReader reader = new InputStreamReader(he.getRequestBody(), StandardCharsets.UTF_8)) {
                char[] buffer = new char[256];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    body.append(buffer, 0, read);
                }
                he.getRequestBody().close();

            } 
            return body.toString();
        }
        
        @Override
        public void handle(HttpExchange he) {
            String responseBody = "dummy";
            
            try {
                responseBody = this.getResponseBody(this.getRequestBody(he));
            } catch (Exception e) {
                ErrorResponse response = new ErrorResponse();
                response.error = e.toString();
                responseBody = gson.toJson(response);
                
                System.out.println(e);
            }
            
            try {
                he.sendResponseHeaders(200, responseBody.length());
                he.getResponseBody().write(responseBody.getBytes());
                he.getResponseBody().close();
                he.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        public String getResponseBody(String requestBody) {
            return "Sample response";
        }
    }
    
    class AddPlayerHandler extends EndpointHttpHandler {
        @Override
        public String getResponseBody(String requestBody) {
            AddPlayerResponse response = new AddPlayerResponse();

            System.out.println("AddPlayer: " + requestBody);
            
            AddPlayerRequest request = gson.fromJson(requestBody, AddPlayerRequest.class);
            
            if (request != null) {
                response.id = tanks.size();
                response.name = request.name;
                
                System.out.println("=> New player with name: " + request.name);
                System.out.println();
            } else {
                response.error = "Unable to parse body";
            }

            battletank.Player player = new battletank.Player(request.name);
            player.setPrimaryColor(getRandomColor());
            player.setSecondaryColor(getRandomColor());
            battletank.tankai.EndpointAI tankAI = new battletank.tankai.EndpointAI();
            Tank tank = new Tank(player, tankAI);
            tanks.add(tank);
            
            tank.setLocation(new Point2D(rand.nextInt(endpoint.getArena().getWidth()), rand.nextInt(endpoint.getArena().getHeight())));
            
            
            endpoint.getArena().addObject(tank);
            
            endpoint.refresh();
            return gson.toJson(response);
        }
    }
    
    class TickHandler extends EndpointHttpHandler {
        @Override
        public String getResponseBody(String requestBody) {
            TickResponse response = new TickResponse();

            System.out.println("Tick: " + requestBody);
            
            TickRequest request = gson.fromJson(requestBody, TickRequest.class);
            
            if (request != null) {
                response.players = new Player[1];
            } else {
                response.error = "Unable to parse body";
            }

            // handle commands
            for (int i = 0; i < request.commands.length; i++) {
                Command command = request.commands[i];
                if (command.player_id > tanks.size()) {
                    continue;
                }
                battletank.tankai.EndpointAI tankAI = (battletank.tankai.EndpointAI) tanks.get(command.player_id).getTankAI();
                tankAI.setCommand(command);
            }
            
            endpoint.getArena().tick();
            endpoint.refresh();
            
            
            // get game state
            response.players = new Player[tanks.size()];
            for (int i = 0; i < tanks.size(); i++) {
                Player player = Player.fromTank(tanks.get(i));
                player.id = i;
                
                response.players[i] = player;
            }
            
            return gson.toJson(response);
        }
    }
    
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }
    
    public Color getRandomColor() {
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        return randomColor;
    }
}
