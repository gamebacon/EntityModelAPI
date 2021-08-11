package net.gamebacon.entitymodelapi.util.builder.model;

import net.gamebacon.entitymodelapi.event.Action;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlaceableModelBuilder extends ModelBuilder {

    protected PlaceableModelBuilder(Material mat) {
        super(mat);
    }

    public PlaceableModelBuilder placeOn(PlaceType type) {
        this.model.setPlaceType(type);
        return this;
    }

    public PlaceableModelBuilder portable() {
        model.setPortable(true);
        return this;
    }

    public PlaceableModelBuilder interactDist(float minDistance) {
        model.setMinimumInteractDistance(minDistance);
        return this;
    }

    public PlaceableModelBuilder offsetY(float y) {
        model.setOffsetY(y);
        return this;
    }

    public PlaceableModelBuilder onEntityInteract(final Action<PlayerInteractAtEntityEvent> action) {
        model.setInteractEntityEvent(action);
        return this;
    }



}
