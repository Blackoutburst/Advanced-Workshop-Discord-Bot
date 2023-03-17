package com.blackoutburst.wsbot.commands;

import com.blackoutburst.wsbot.utils.EmbedUtils;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Leaderboard implements Command {

    @Override
    public void onExecute(SlashCommandInteractionEvent event) {
        MessageEmbed leaderboardEmbed = EmbedUtils.createLeaderboardEmbed();
        event.replyEmbeds(leaderboardEmbed).queue();
    }

    @Override
    public String getName() {
        return "lb";
    }
}
