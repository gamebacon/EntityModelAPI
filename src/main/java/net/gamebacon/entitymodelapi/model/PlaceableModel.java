package net.gamebacon.entitymodelapi.model;

import net.gamebacon.entitymodelapi.event.Action;
import net.gamebacon.entitymodelapi.util.builder.model.PlaceType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public abstract class PlaceableModel {

    private Action<PlayerInteractAtEntityEvent> interactEntityEvent;
    private boolean portable;
    private float minInteractDist = 3f;
    private PlaceType placeType = PlaceType.NONE;
    private float offsetY;


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

    public float getInteractDist() {
        return minInteractDist;
    }

    public Action<PlayerInteractAtEntityEvent> getInteractEntityEvent() {
        return interactEntityEvent;
    }

    public void setInteractEntityEvent(Action<PlayerInteractAtEntityEvent> interactEntityEvent) {
        this.interactEntityEvent = interactEntityEvent;
    }
}
