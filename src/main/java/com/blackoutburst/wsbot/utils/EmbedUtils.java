package com.blackoutburst.wsbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class EmbedUtils {

    private static java.util.List<String> splitDescription(String description, int maxLength) {
        List<String> result = new ArrayList<>();

        while (description.length() > maxLength) {
            int lastIndex = description.lastIndexOf("\n", maxLength);
            if (lastIndex == -1) {
                lastIndex = maxLength;
            }
            result.add(description.substring(0, lastIndex));
            description = description.substring(lastIndex).trim();
        }

        result.add(description);
        return result;
    }

    public static MessageEmbed createLeaderboardEmbed() {
        String leaderboardUrl = "https://www.partygame.party/leaderboards";

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Leaderboards")
                .setColor(new Color(255, 165, 0))
                .setDescription(String.format("Please visit the [leaderboard page](%s) to view the full leaderboard. Due to Discord limitations, we apologize for redirecting you to our website.", leaderboardUrl))
                .setFooter("Made by GPT-4", "https://avatars.githubusercontent.com/u/14957082?s=200&v=4");

        return embedBuilder.build();
    }


    public static MessageEmbed createEmbedWithMapStats(Player player, Player.MapStats mapStats) {
        String imageUrl = "https://visage.surgeplay.com/face/512/" + player.getUuid() + "?&time=" + Instant.now().getEpochSecond();
        String craftDescription = mapStats.getCrafts().stream()
                .sorted(Comparator.comparing(Player.Craft::getName))
                .map(craft -> String.format("**%s**: %.3fs", craft.getName(), craft.getTime()))
                .collect(Collectors.joining("\n"));

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(player.getName())
                .setColor(new Color(255, 165, 0))
                .setThumbnail(imageUrl)
                .addField("Total Games", numberFormat.format(player.getGameCount()), true)
                .addField("Total Rounds", numberFormat.format(player.getRoundCount()), true)
                .addBlankField(true)
                .addField("Map", mapStats.getName(), false)
                .addField("Games", numberFormat.format(mapStats.getGameCount()), true)
                .addField("Rounds", numberFormat.format(mapStats.getRoundCount()), true)
                .addField("Time", String.format("%.3fs", mapStats.getTime()), true)
                .addField("1 Minute Crafts", numberFormat.format(mapStats.getM1crafts()), true)
                .addField("90 Seconds Crafts", numberFormat.format(mapStats.getS90crafts()), true)
                .addField("2 Minutes Crafts", numberFormat.format(mapStats.getM2crafts()), true)
                .addField("5 Minutes Crafts", numberFormat.format(mapStats.getM5crafts()), true)
                .addField("All Crafts", String.format("%.3fs", mapStats.getAllCrafts()), true);

        List<String> craftDescriptions = splitDescription(craftDescription, 1024);
        for (int i = 0; i < craftDescriptions.size(); i++) {
            if (i == 0) {
                embedBuilder.addField("Crafts", craftDescriptions.get(i), false);
            } else {
                embedBuilder.addField("", craftDescriptions.get(i), false);
            }
        }

        return embedBuilder
                .setFooter("Made by GPT-4", "https://avatars.githubusercontent.com/u/14957082?s=200&v=4")
                .build();
    }
}
