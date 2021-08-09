package net.gamebacon.entitymodelapi.model;

import net.gamebacon.entitymodelapi.event.Action;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Model {
    private final UUID uuid = UUID.randomUUID();
    private final ItemStack item;
    private final String identifier;

    private Action<PlayerInteractAtEntityEvent> interactEntityEvent;
    private Action<PlayerInteractEvent> interactEvent;
    private boolean placeable;
    private boolean portable;

    public Model(String identifier, ItemStack item) {
        this.identifier = identifier;
        this.item = item;
    }


    public Action<PlayerInteractEvent> getInteractEvent() {
        return interactEvent;
    }

    public void setInteractEvent(Action<PlayerInteractEvent> interactEvent) {
        this.interactEvent = interactEvent;
    }

    public Action<PlayerInteractAtEntityEvent> getInteractEntityEvent() {
        return interactEntityEvent;
    }

    public void setInteractEntityEvent(Action<PlayerInteractAtEntityEvent> interactEntityEvent) {
        this.interactEntityEvent = interactEntityEvent;
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

    public void setPlaceable(boolean b) {
        this.placeable = b;
    }

    public boolean isPlaceable() {
        return placeable;
    }

    public void setPortable(boolean b) {
        this.portable = b;
    }

    public boolean isPortable() {
        return portable;
    }
}
