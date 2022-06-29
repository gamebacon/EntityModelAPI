package net.gamebacon.entitymodelapi.util.builder.model;

import net.gamebacon.entitymodelapi.event.Action;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class ModelBuilder extends BaseBuilder<ModelBuilder> {



    protected ModelBuilder(final Material type) {
        super(type);
    }

    public static ModelBuilder from(final Material type) {
        return new ModelBuilder(type);
    }

    public static PlaceableModelBuilder asPlaceable(final Material type) {
        return new PlaceableModelBuilder(type);
    }


    /*
    public ModelBuilder onInteract(final Action<PlayerInteractEvent> action) {
        model.setInteractEvent(action);
        return this;
    }
     */



}
