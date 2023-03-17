package com.blackoutburst.wsbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface Command {
    void onExecute(SlashCommandInteractionEvent event);
    String getName();
}
