package net.gamebacon.entitymodelapi.util.builder.model;

import net.gamebacon.entitymodelapi.api.EntityAPI;
import net.gamebacon.entitymodelapi.model.Model;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

    public ItemStack build(EntityAPI api) {

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        meta.getPersistentDataContainer().set(new NamespacedKey(api.getPlugin(), "mainKey"), PersistentDataType.STRING, model.toString());

        item.setItemMeta(meta);
        return model.getItem();
    }

}
