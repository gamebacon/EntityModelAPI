package net.gamebacon.modelentityapi.util;

import net.gamebacon.modelentityapi.event.ModelListener;
import net.gamebacon.modelentityapi.model.Model;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Util {

    public static final HashMap<UUID, Model> models = new HashMap<>();
    public static final Plugin plugin = JavaPlugin.getProvidingPlugin(ModelBuilder.class);
    public static final NamespacedKey namespacedKey = new NamespacedKey(plugin, "${name}");

    static {
        plugin.getServer().getPluginManager().registerEvents(new ModelListener(), plugin);
    }

    public static boolean isModelItem(ItemStack itemStack) {
        return itemStack instanceof Model;//itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING);
    }

    public static boolean isModel(Entity rightClicked) {
        return rightClicked.getType() == EntityType.ARMOR_STAND &&
                isModelItem(((ArmorStand) rightClicked).getEquipment().getHelmet());
    }
}
