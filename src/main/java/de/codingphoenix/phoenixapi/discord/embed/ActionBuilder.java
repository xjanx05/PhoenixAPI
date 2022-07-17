package de.codingphoenix.phoenixapi.discord.embed;

import net.dv8tion.jda.api.interactions.components.ActionComponent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class ActionBuilder {
    private final MessageAction messageAction;
    private final ArrayList<ActionComponent> actionComponents = new ArrayList<>();
    private final Collection<ActionRow> rows = new HashSet<>();

    public ActionBuilder(MessageAction messageAction) {
        this.messageAction = messageAction;
    }

    public ActionBuilder addAction(ActionComponent actionComponent) {
        actionComponents.add(actionComponent);
        return this;
    }

    public ActionBuilder nextRow() {
        Collection<ActionComponent> buildActionComponents = new HashSet<>();
        for (ActionComponent actionComponent : actionComponents) {
            buildActionComponents.add(actionComponent);
            if (buildActionComponents.size() >= actionComponent.getMaxPerRow() || (!buildActionComponents.isEmpty() && buildActionComponents.size() >= buildActionComponents.stream().findFirst().get().getMaxPerRow())) {
                rows.add(ActionRow.of(actionComponents));
                buildActionComponents.clear();
            }
        }
        if (!buildActionComponents.isEmpty()) {
            rows.add(ActionRow.of(actionComponents));
        }
        actionComponents.clear();
        return this;
    }

    public MessageAction build() {
        if (actionComponents.isEmpty()) {
            return messageAction;
        }
        Collection<ActionComponent> buildActionComponents = new HashSet<>();
        Collection<ActionRow> actionRows = new HashSet<>(rows);
        for (ActionComponent actionComponent : actionComponents) {
            if (buildActionComponents.size() >= actionComponent.getMaxPerRow() || (!buildActionComponents.isEmpty() && buildActionComponents.size() >= buildActionComponents.stream().findFirst().get().getMaxPerRow())) {
                actionRows.add(ActionRow.of(actionComponents));
                buildActionComponents.clear();
            }
            buildActionComponents.add(actionComponent);
        }
        if (!buildActionComponents.isEmpty()) {
            actionRows.add(ActionRow.of(buildActionComponents));
        }
        return messageAction.setActionRows(actionRows);
    }
}
