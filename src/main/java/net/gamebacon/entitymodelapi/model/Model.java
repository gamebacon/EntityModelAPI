package net.gamebacon.entitymodelapi.model;

import net.gamebacon.entitymodelapi.event.Action;
import net.gamebacon.entitymodelapi.util.builder.model.PlaceType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Model {

    private final UUID uuid;

    private boolean portable;
    private float minInteractDist = 3f;
    private PlaceType placeType = PlaceType.NONE;
    private float offsetY;
    private String interactCommand;

    private transient UUID entityUUID;
    private transient ItemStack item;
    private Action<PlayerInteractAtEntityEvent> interactEntityEvent;

    public Model(ItemStack itemStack) {
        this.item = itemStack;
        this.uuid = UUID.randomUUID();
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public void setOffsetY(float y) {
        this.offsetY = y;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setPortable(boolean b) {
        this.portable = b;
    }

    public boolean isPortable() {
        return portable;
    }

    public void setMinimumInteractDistance(float minDistance) {
        this.minInteractDist = minDistance;
    }

    public float getMaxInteractDistance() {
        return minInteractDist;
    }

    @Deprecated
    public Action<PlayerInteractAtEntityEvent> getInteractEntityEvent() {
        return interactEntityEvent;
    }

    @Deprecated
    public void setInteractEntityEvent(Action<PlayerInteractAtEntityEvent> interactEntityEvent) {
        this.interactEntityEvent = interactEntityEvent;
    }

    public void setEntityUUID(UUID entityUUID) {
        this.entityUUID = entityUUID;
    }

    public UUID getEntityUUID() {
        return entityUUID;
    }


    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String toString() {
        return String.format("{uuid=\"%s\", portable=\"%s\", minInteractDist=\"%f\", placeType=\"%s\", offsetY=\"%f\", interactCommand=\"%s\"}",
                uuid,
                portable,
                minInteractDist,
                placeType.toString(),
                offsetY,
                interactCommand
        );
    }

    public String getInteractCommand() {
        return interactCommand;
    }

    public void setInteractCommand(String interactCommand) {
        this.interactCommand = interactCommand;
    }
}
