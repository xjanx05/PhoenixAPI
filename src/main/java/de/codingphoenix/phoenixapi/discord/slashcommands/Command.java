package de.codingphoenix.phoenixapi.discord.slashcommands;


import de.codingphoenix.phoenixapi.discord.color.ColorBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class Command {
    private final String[] keys;

    public Command(String... keys) {
        this.keys = keys;
    }

    public abstract void action(SlashCommandInteractionEvent event);

    public abstract String usage();

    public void sendHelpMessage(SlashCommandInteractionEvent e) {
        e.getTextChannel().sendMessage(usage()).queue();
    }

    public abstract String description();

    public String[] getKeys() {
        return keys;
    }

    public abstract OptionData[] getOptionData();

    public EmbedBuilder getSuccessEmbed(String message) {
        return new EmbedBuilder().setAuthor("✔ Erfolgreich ✔").setDescription("  " + message).setColor(ColorBase.SUCCESCOLOR);
    }


    public EmbedBuilder getErrorEmbed(String message) {
        return new EmbedBuilder().setAuthor("❗ Es ist ein Fehler aufgetreten ❗").setDescription("  `" + message + "`").setColor(ColorBase.ERRORCOLOR);
    }

    public EmbedBuilder getWarnEmbed(String message) {
        return new EmbedBuilder().setAuthor("⚠ Warnung ⚠").setDescription("  `" + message + "`").setColor(ColorBase.WARNCOLOR);
    }

}
