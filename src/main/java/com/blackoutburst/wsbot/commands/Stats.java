package com.blackoutburst.wsbot.commands;

import com.blackoutburst.wsbot.core.CommandListener;
import com.blackoutburst.wsbot.core.RequestManager;
import com.blackoutburst.wsbot.utils.EmbedUtils;
import com.blackoutburst.wsbot.utils.Player;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class Stats implements Command {

    @Override
    public void onExecute(SlashCommandInteractionEvent event) {
        String playerName = event.getOption("player").getAsString();

        try {
            String playerUUID = getPlayerUUID(playerName);
            Player player = RequestManager.getPlayerStats(playerUUID);
            player.setUuid(playerUUID);

            Player.MapStats firstMapStats = player.getMaps().get(0);

            List<Player.MapStats> mapStats = player.getMaps();
            StringSelectMenu.Builder menuBuilder = StringSelectMenu.create("map-selector");

            for (int i = 0; i < mapStats.size(); i++) {
                Player.MapStats map = mapStats.get(i);
                menuBuilder.addOption(map.getName(), String.valueOf(i));
            }

            menuBuilder.setPlaceholder("Select a map")
                    .setMaxValues(1)
                    .setMinValues(1);

            MessageEmbed embed = EmbedUtils.createEmbedWithMapStats(player, firstMapStats);

            ActionRow actionRow = ActionRow.of(menuBuilder.build());

            event.replyEmbeds(embed).setActionRow(actionRow.getComponents()).queue(hook -> {
                hook.retrieveOriginal().queue(msg -> {
                    CommandListener.playerDataByMessageId.put(msg.getId(), player);
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPlayerUUID(String playerName) throws IOException {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        String jsonResponse = RequestManager.sendGet(url);
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        return jsonObject.get("id").getAsString();
    }

    @Override
    public String getName() {
        return "s";
    }
}
