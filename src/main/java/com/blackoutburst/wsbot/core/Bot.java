package com.blackoutburst.wsbot.core;

import com.blackoutburst.wsbot.Main;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class Bot {
    private static JDA jda;

    public static void init() {
        try {
            jda = JDABuilder.createDefault(Main.BOT_TOKEN)
                    .setActivity(Activity.playing("Your game"))
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(new CommandListener())
                    .build()
                    .awaitReady();

            // Register slash commands
            jda.upsertCommand(new CommandDataImpl("s", "Get player stats")
                    .addOption(OptionType.STRING, "player", "Player name", true)).queue();
            jda.upsertCommand(new CommandDataImpl("lb", "Show the leaderboard")).queue();

            System.out.println("Bot is ready!");
        } catch (InterruptedException e) {
            System.err.println("Error initializing the bot: " + e.getMessage());
            System.exit(1);
        }
    }
}
