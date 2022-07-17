package de.codingphoenix.phoenixapi.discord.embed;

import lombok.Getter;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class EmbedBuilder {
    private final ArrayList<MessageEmbed.Field> fields = new ArrayList<>();
    @Getter
    private String title;
    @Getter
    private Color color;
    @Getter
    private String description;
    @Getter
    private String thumbnail;
    @Getter
    private String author;
    @Getter
    private String authorlink;
    @Getter
    private String image;
    @Getter
    private String footerImage;
    @Getter
    private String footer;
    @Getter
    private TemporalAccessor timestamp;

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
            if (authorlink != null) {
                eb.setAuthor(author, authorlink);
            } else {
                eb.setAuthor(author);
            }
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
        if (timestamp != null) {
            eb.setTimestamp(timestamp);
        }
        for (MessageEmbed.Field field : fields) {
            eb.addField(field);
        }
        return eb.build();
    }

    public EmbedBuilder addField(MessageEmbed.Field field) {
        fields.add(field);
        return this;
    }

    public EmbedBuilder addClearField(boolean inline) {
        addField(new MessageEmbed.Field("\u200b", "\u200b", inline));
        return this;
    }

    public EmbedBuilder setTimestamp(TemporalAccessor timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public EmbedBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmbedBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public EmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedBuilder setFooterImage(String footerImage) {
        this.footerImage = footerImage;
        return this;
    }

    public EmbedBuilder setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public EmbedBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public EmbedBuilder setAuthor(String author, String link) {
        this.author = author;
        this.authorlink = link;
        return this;
    }

    public EmbedBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public EmbedBuilder setFooter(String footer) {
        this.footer = footer;
        return this;
    }
}
