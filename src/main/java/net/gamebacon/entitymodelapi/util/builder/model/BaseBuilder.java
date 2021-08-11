package net.gamebacon.entitymodelapi.util.builder.model;

import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public abstract class BaseBuilder<T extends BaseBuilder<T>> {
    protected final Model model;
    protected final ItemStack item;
    protected final ItemMeta meta;


    protected BaseBuilder(Material mat) {
        this.item = new ItemStack(mat);
        this.meta = item.getItemMeta();
        model = new Model(item);
    }

    public T name(String name) {
        meta.setDisplayName(name);
        return (T) this;
    }

    public T lore(String... lores) {
        meta.setLore(Arrays.asList(lores));
        return (T) this;
    }

    public T durability(int durability) {
        try {
            Damageable damageable = (Damageable) meta;
            damageable.setDamage(durability);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
        return (T) this;
    }

    public ItemStack build(final String identifier) {
        model.setIdentifier(identifier);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.getPersistentDataContainer().set(Util.mainKey, PersistentDataType.STRING, identifier);
        item.setItemMeta(meta);

        if(Util.models.containsKey(model.getIdentifier()))
            throw new IllegalArgumentException(String.format("A model with the id \"%s\" already exists.", model.getIdentifier()));

        Util.models.put(model.getIdentifier(), model);
        return model.getItem();
    }

}
