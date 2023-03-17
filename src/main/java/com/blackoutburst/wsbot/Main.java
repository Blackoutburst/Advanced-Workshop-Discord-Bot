package com.blackoutburst.wsbot;

import com.blackoutburst.wsbot.core.Bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static String BOT_TOKEN;
    public static String API_TOKEN;
    public static String API_HOST;

    public static void main(String[] args) {
        readEnvFile();
        Bot.init();
    }

    private static void readEnvFile() {
        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream(".env")) {
            properties.load(inputStream);
            BOT_TOKEN = properties.getProperty("BOT_TOKEN");
            API_TOKEN = properties.getProperty("API_TOKEN");
            API_HOST = properties.getProperty("API_HOST");
        } catch (IOException e) {
            System.err.println("Error reading .env file: " + e.getMessage());
            System.exit(1);
        }
    }
}
