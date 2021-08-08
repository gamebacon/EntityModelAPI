package net.gamebacon.entitymodelapi.model;

import net.gamebacon.entitymodelapi.event.Action;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Model {
    private final UUID uuid;

    private Action<PlayerInteractAtEntityEvent> interactEntityEvent;
    private Action<PlayerInteractEvent> interactEvent;
    private ItemStack item;

    public Model(Material material, UUID uuid) {
        this.item = new ItemStack(material);
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
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

    public ItemStack getItem() {
        return item;
    }
}
