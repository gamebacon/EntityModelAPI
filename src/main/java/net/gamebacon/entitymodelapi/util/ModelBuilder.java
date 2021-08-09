package net.gamebacon.entitymodelapi.util;

import net.gamebacon.entitymodelapi.event.Action;
import net.gamebacon.entitymodelapi.model.Model;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ModelBuilder {

    private final Model model;

    private final ItemMeta meta;
    private final ItemStack item;

    private String name;


    private ModelBuilder(String identifier, Material type) {
        this.item = new ItemStack(type);
        this.model = new Model(identifier, item);

        if(!item.hasItemMeta())
            throw new IllegalArgumentException(String.format("The item: %s does not contain itemmeta!", type.toString()));

        meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Util.namespacedKey, PersistentDataType.STRING, identifier);
    }

    public static ModelBuilder as(String identifier, Material type) {
        return new ModelBuilder(identifier, type);
    }

    public ModelBuilder type(Material material) {
        item.setType(material);
        return this;
    }

    public ModelBuilder durability(int durability) {
        try {
            Damageable damageable = (Damageable) meta;
            damageable.setDamage(durability);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
        return this;
    }



    public ModelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ModelBuilder lore(String... lores) {
        meta.setLore(Arrays.asList(lores));
        return this;
    }

    public ModelBuilder onInteract(final Action<PlayerInteractEvent> action) {
        model.setInteractEvent(action);
        return this;
    }

    public ModelBuilder placeable() {
        model.setPlaceable(true);
        return this;
    }

    public ModelBuilder portable() {
        model.setPortable(true);
        return this;
    }

    public ModelBuilder onEntityInteract(final Action<PlayerInteractAtEntityEvent> action) {
        model.setInteractEntityEvent(action);
        return this;
    }

    public ItemStack bulid() {
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName(name);

        item.setItemMeta(meta);

        if(Util.models.containsKey(model.getIdentifier()))
            throw new IllegalArgumentException(String.format("A model with the id \"%s\" already exists.", model.getIdentifier()));

        Util.models.put(model.getUuid(), model);
        return model.getItem();
    }
}
