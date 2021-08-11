package net.gamebacon.entitymodelapi.util;

import net.gamebacon.entitymodelapi.event.ModelListener;
import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.builder.model.ModelBuilder;
import net.gamebacon.entitymodelapi.util.builder.model.PlaceType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Util {

    public static final HashMap<String, Model> models = new HashMap<>();
    public static final Plugin plugin = JavaPlugin.getProvidingPlugin(ModelBuilder.class);
    public static final NamespacedKey mainKey = new NamespacedKey(plugin, Model.class.getPackageName());
    public static final NamespacedKey ownerKey = new NamespacedKey(plugin, Model.class.getPackageName() + ".owner");

    static {
        plugin.getServer().getPluginManager().registerEvents(new ModelListener(), plugin);
    }

    public static boolean isModelItem(ItemStack itemStack) {
        boolean homo = itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(mainKey, PersistentDataType.STRING);
        return homo;
    }

    public static boolean isModelEntity(Entity rightClicked) {
        return rightClicked.getType() == EntityType.ARMOR_STAND &&
                isModelItem(((ArmorStand) rightClicked).getEquipment().getHelmet());
    }

    public static Model getModel(ItemStack item) {
        String id = item.getItemMeta().getPersistentDataContainer().get(mainKey, PersistentDataType.STRING);
        return models.get(id);
    }

    public static boolean canPlace(PlayerInteractEvent event, Model model) {
        boolean canPlace = event.getHand() == EquipmentSlot.HAND &&
                event.getClickedBlock().getRelative(event.getBlockFace()).getType() == Material.AIR &&
                placeable(event.getBlockFace(), model.getPlaceType());

        if(!canPlace) {
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "You can't place that here."));
            event.getPlayer().playSound(event.getPlayer().getLocation(), "misc.deny", 1, 1);
        }

        return canPlace;
    }

    private static boolean placeable(BlockFace face, PlaceType placeType) {
        switch (face) {
            case UP: return placeType == PlaceType.GROUND;
            case DOWN: return placeType == PlaceType.CEILING;
            default: return placeType != PlaceType.NONE;
        }
    }


    public static boolean equip(Player player, ItemStack item) {
        if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.getInventory().setItemInMainHand(item);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1,1);
            return true;
        }
        return false;
    }

    public static Location getASLocation(PlayerInteractEvent event, BlockFace playerFacing, Model model) {
        Location location = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5,.5 + model.getOffsetY(),.5);

        if(model.getPlaceType() == PlaceType.WALL)
            switch (event.getBlockFace()) {
                case NORTH: location.setYaw(180); break;
                case WEST: location.setYaw(90); break;
                case EAST: location.setYaw(-90); break;
            }
        else
            switch(playerFacing) {
                case SOUTH: location.setYaw(180); break;
                case WEST: location.setYaw(270); break;
                case EAST: location.setYaw(90); break;
                default: break;
            }

        return location;
    }

    public static void setOwner(final ItemStack item, final UUID uuid) {
        final ItemMeta meta = item.getItemMeta();
        final PersistentDataContainer container = meta.getPersistentDataContainer();

        if(uuid == null)
            container.remove(ownerKey);
        else
            container.set(ownerKey, PersistentDataType.STRING, uuid.toString());
       item.setItemMeta(meta);
    }

    public static UUID getOwner(final ItemStack item) {
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if(container.has(ownerKey, PersistentDataType.STRING)) {
            return UUID.fromString(container.get(ownerKey, PersistentDataType.STRING));
        }
        return null;
    }

    private static boolean isOwner(ItemStack item, UUID ownerUUID) {
        UUID uuid = getOwner(item);
        return uuid != null && uuid.equals(ownerUUID);
    }


    public static boolean canUse(Player player, Location location, float minDist) {
        boolean use = player.getLocation().distance(location) < minDist;
        if(!use)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "Too far away!"));
        return use;
    }

    public static boolean unEquip(Player player) {
        assert player.getInventory().getItemInMainHand().getType() != Material.AIR;
        player.getInventory().setItemInMainHand(null);
        return true;
    }

    public static void debug(Object o) {
        System.err.println(Color.GREEN + " - " + o);
    }

    public static boolean canTake(final Model model, final ItemStack item, final Player player) {
        return model.isPortable() &&
                player.isSneaking() &&
                isOwner(item, player.getUniqueId());
    }
}
