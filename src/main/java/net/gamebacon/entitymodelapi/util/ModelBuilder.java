package net.gamebacon.entitymodelapi.util;

import net.gamebacon.entitymodelapi.event.Action;
import net.gamebacon.entitymodelapi.model.Model;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.UUID;

public class ModelBuilder {

    private final Model model;
    private final ItemMeta meta;

    private Component name;


    private ModelBuilder(Material material, int durability) {
        this.model = new Model(material, UUID.randomUUID());
        meta = model.getItemMeta();

        Damageable damageable = (Damageable) meta;
        damageable.setDamage(durability);

        meta.getPersistentDataContainer().set(Util.namespacedKey, PersistentDataType.STRING, model.getUuid().toString());
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
    }

    public static ModelBuilder from(Material material, int durability) {
        return new ModelBuilder(material, durability);
    }

    public ModelBuilder name(Component name) {
        this.name = name;
        return this;
    }

    public ModelBuilder lore(Component... name) {
        meta.lore(Arrays.asList(name));
        return this;
    }

    public ModelBuilder onInteract(final Action<PlayerInteractEvent> action) {
        model.setInteractEvent(action);
        return this;
    }

    public ModelBuilder onEntityInteract(final Action<PlayerInteractAtEntityEvent> action) {
        model.setInteractEntityEvent(action);
        return this;
    }
    public Model bulid() {
        meta.displayName(name);
        model.setItemMeta(meta);
        return model;
    }
}
