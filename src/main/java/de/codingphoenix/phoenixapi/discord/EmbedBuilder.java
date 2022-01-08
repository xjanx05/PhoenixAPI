package de.codingphoenix.phoenixapi.discord;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class EmbedBuilder {
    private String title;
    private Color color;
    private String description;
    private String thumbnail;
    private String author;
    private String image;
    private String footerImage;
    private String footer;
    private final ArrayList<MessageEmbed.Field> fields = new ArrayList<>();

    public static EmbedBuilder getEmbedBuilderByEmbed(MessageEmbed embed) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (embed.getTitle() != null) {
            embedBuilder.setTitle(embed.getTitle());
        }
        if (embed.getColor() != null) {
            embedBuilder.setColor(embed.getColor());
        }
        if (embed.getFooter() != null && embed.getFooter().getText() != null) {
            embedBuilder.setFooter(embed.getFooter().getText());
        }
        if (embed.getImage() != null && embed.getImage().getUrl() != null) {
            embedBuilder.setImage(embed.getImage().getUrl());
        }
        if (embed.getDescription() != null) {
            embedBuilder.setDescription(embed.getDescription());
        }
        if (embed.getAuthor() != null) {
            embedBuilder.setAuthor(embed.getAuthor().getName());
        }
        return embedBuilder;
    }

    public MessageEmbed build() {
        net.dv8tion.jda.api.EmbedBuilder eb = new net.dv8tion.jda.api.EmbedBuilder();
        if (title != null) {
            eb.setTitle(title);
        }
        if (color != null) {
            eb.setColor(color);
        }
        if (thumbnail != null) {
            eb.setThumbnail(thumbnail);
        }
        if (author != null) {
            eb.setAuthor(author);
        }
        if (image != null) {
            eb.setImage(image);
        }
        if (footer != null) {
            if (footerImage != null) {
                eb.setFooter(footer, footerImage);
            } else {
                eb.setFooter(footer);
            }
        }
        if (description != null) {
            eb.setDescription(description);
        }
        for (MessageEmbed.Field field : fields) {
            eb.addField(field);
        }
        return eb.build();
    }

    public String getTitle() {
        return title;
    }

    public EmbedBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public EmbedBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public EmbedBuilder setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public EmbedBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getImage() {
        return image;
    }

    public EmbedBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public String getFooter() {
        return footer;
    }

    public EmbedBuilder setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public String getFooterImage() {
        return footerImage;
    }

    public EmbedBuilder setFooterImage(String footerImage) {
        this.footerImage = footerImage;
        return this;
    }

    public ArrayList<MessageEmbed.Field> getFields() {
        return fields;
    }

    public EmbedBuilder addField(MessageEmbed.Field field) {
        fields.add(field);
        return this;
    }
}
