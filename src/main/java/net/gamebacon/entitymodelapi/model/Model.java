package net.gamebacon.entitymodelapi.model;

import net.gamebacon.entitymodelapi.event.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Model extends PlaceableModel {
    private final UUID uuid = UUID.randomUUID();
    private final ItemStack item;

    private Action<PlayerInteractEvent> interactEvent;
    private String identifier;

    public Model(ItemStack item) {
        this.item = item;
    }
    public Action<PlayerInteractEvent> getInteractEvent() {
        return interactEvent;
    }

    public void setInteractEvent(Action<PlayerInteractEvent> interactEvent) {
        this.interactEvent = interactEvent;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
