package de.codingphoenix.phoenixapi.discord.slashcommands;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandHandler extends ListenerAdapter {

    private final JDA jda;

    public CommandHandler(JDA jda) {
        this.jda = jda;
    }
    
    public HashMap<String, Command> commands = new HashMap<>();
    private List<net.dv8tion.jda.api.interactions.commands.Command> registeredCommands;
    @Getter
    private CommandListUpdateAction action;


    public void registerCommands(Command command) {
        if (command.description() == null) {
            System.out.println(command.getKeys()[0] + " description is null");
            return;
        }
        if (action == null) {
            action = jda.updateCommands();
        }
        for (String key : command.getKeys()) {
            commands.put(key.toLowerCase(), command);
            if (commandAlreadyExists(key.toLowerCase())) {
                System.out.println("Command already exists! Skipped: " + key);
                return;
            }
            List<CommandData> commandData = new ArrayList<>();
            SlashCommandData slashCommandData = Commands.slash(key.toLowerCase(), command.description());
            if (command.getOptionData() != null && command.getOptionData().length != 0) {
                for (OptionData optionData : command.getOptionData()) {
                    slashCommandData.addOptions(optionData);
                }
            }
            commandData.add(slashCommandData);
            action.addCommands(commandData);
            System.out.println("Registered Command: " + command.getKeys()[0]);
        }
    }

    private boolean commandAlreadyExists(String command) {
        if (registeredCommands == null) {
            registeredCommands = jda.retrieveCommands().complete();
        }
        for (net.dv8tion.jda.api.interactions.commands.Command registeredCommand : registeredCommands) {
            if (registeredCommand.getName().equals(command)) {
                return true;
            }
        }
        return false;
    }

    public void unregisterCommands(Command command) {
        for (String key : commands.keySet()) {
            if (commands.get(key.toLowerCase()).equals(command)) {
                commands.remove(key.toLowerCase());
            }
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) {
            return;
        }
        if (event.getMember() == null) {
            return;
        }
        if (!event.getMember().getId().equals(event.getJDA().getSelfUser().getId())) {
            if (commands.containsKey(event.getName().toLowerCase())) {
                Command command = commands.get(event.getName().toLowerCase());
                    command.action(event);
            } else {
                System.out.println("Command " + event.getName() + " not exists! Executed by " + event.getUser().getName() + "#" + event.getUser().getDiscriminator());
            }
        }
    }
}
