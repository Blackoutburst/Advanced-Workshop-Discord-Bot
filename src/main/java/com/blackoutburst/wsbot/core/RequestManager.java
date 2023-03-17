package com.blackoutburst.wsbot.core;

import com.blackoutburst.wsbot.Main;
import com.blackoutburst.wsbot.utils.Player;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestManager {
    public static String sendGet(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();
        return content.toString();
    }

    public static Player getPlayerStats(String uuid) throws IOException {
        String urlString = Main.API_HOST + "/user?token=" + Main.API_TOKEN + "&uuid=" + uuid;
        String jsonResponse = sendGet(urlString);
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, Player.class);
    }
}
