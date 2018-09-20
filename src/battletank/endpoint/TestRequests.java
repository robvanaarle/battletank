/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.endpoint;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author raarle
 */
public class TestRequests {
    
    public static void testAddPlayer() throws Exception {
        URL url = new URL("http://localhost:9001/addplayer"); // "http://tank2.free.beeceptor.com"
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        
        String request = "{\"name\": \"Rob\"}";
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(request.getBytes());
        out.flush();
        
        con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        
        System.out.println(sb.toString());
        
        out.close();
        br.close();
    }
    
    public static void testTick() throws Exception {
        URL url = new URL("http://localhost:9001/tick"); // "http://tank2.free.beeceptor.com"
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        
        String request = "{\"commands\": [{\"player_id\": 123, \"move\": \"forward\"}]}";
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(request.getBytes());
        out.flush();
        
        con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        
        System.out.println(sb.toString());
        
        out.close();
        br.close();
    }
    
    public static void main(String[] args) throws Exception {
        testAddPlayer();
        //testTick();
    }
}
