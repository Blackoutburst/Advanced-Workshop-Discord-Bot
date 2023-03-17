package com.blackoutburst.wsbot.core;

import com.blackoutburst.wsbot.commands.Command;
import com.blackoutburst.wsbot.commands.Leaderboard;
import com.blackoutburst.wsbot.commands.Stats;
import com.blackoutburst.wsbot.utils.EmbedUtils;
import com.blackoutburst.wsbot.utils.Player;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandListener extends ListenerAdapter {
    public static final Map<String, Player> playerDataByMessageId = new HashMap<>();
    private final Map<String, Command> commands = new HashMap<>();

    public CommandListener() {
        registerCommand(new Stats());
        registerCommand(new Leaderboard());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        Command command = commands.get(commandName);

        if (command != null) {
            command.onExecute(event);
        }
    }

    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("map-selector")) {
            int selectedIndex = Integer.parseInt(event.getValues().get(0));
            Player player = playerDataByMessageId.get(event.getMessageId());
            Player.MapStats selectedMapStats = player.getMaps().get(selectedIndex);

            // Generate a new embed with the selected map's stats
            MessageEmbed newEmbed = EmbedUtils.createEmbedWithMapStats(player, selectedMapStats);

            // Create the updated action row with the select menu
            List<Player.MapStats> mapStats = player.getMaps();
            StringSelectMenu.Builder menuBuilder = StringSelectMenu.create("map-selector");

            for (int i = 0; i < mapStats.size(); i++) {
                Player.MapStats map = mapStats.get(i);
                menuBuilder.addOption(map.getName(), String.valueOf(i));
            }

            menuBuilder.setPlaceholder("Select a map")
                    .setMaxValues(1)
                    .setMinValues(1);

            ActionRow actionRow = ActionRow.of(menuBuilder.build());

            event.deferEdit().queue();

            event.getHook().editOriginalEmbeds(newEmbed).queue();
            event.getHook().editOriginalComponents(actionRow).queue();

        }
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

}
