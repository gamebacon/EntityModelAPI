package net.gamebacon.entitymodelapi.util;

import net.gamebacon.entitymodelapi.event.ModelListener;
import net.gamebacon.entitymodelapi.model.Model;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Util {

    public static final HashMap<UUID, Model> models = new HashMap<>();
    public static final Plugin plugin = JavaPlugin.getProvidingPlugin(ModelBuilder.class);
    public static final NamespacedKey namespacedKey = new NamespacedKey(plugin, Model.class.getPackageName());

    static {
        plugin.getServer().getPluginManager().registerEvents(new ModelListener(), plugin);
    }

    public static boolean isModelItem(ItemStack itemStack) {
        boolean homo = itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING);
        System.out.println(homo);
        return homo;
    }

    public static boolean isModel(Entity rightClicked) {
        return rightClicked.getType() == EntityType.ARMOR_STAND &&
                isModelItem(((ArmorStand) rightClicked).getEquipment().getHelmet());
    }

    public static Model getModel(ItemStack item) {
        UUID uuid = UUID.fromString(item.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING));
        return models.get(uuid);
    }


    public static boolean equip(Player player, ItemStack item) {
        if(player.getInventory().getItemInMainHand() == null) {
            player.getInventory().setItemInMainHand(item);
            return true;
        }
        return false;
    }
}
