package net.gamebacon.entitymodelapi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.builder.armorstand.ArmorStandBuilder;
import net.gamebacon.entitymodelapi.util.builder.model.PlaceType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Util {

    private final Plugin plugin;
    public final NamespacedKey mainKey;
    public final NamespacedKey ownerKey;
    private final Gson gson = new GsonBuilder().create();

    public Util(Plugin plugin) {
        this.plugin = plugin;
        mainKey = new NamespacedKey(plugin, "mainKey");
        ownerKey = new NamespacedKey(plugin, "ownerKey");
    }

    public boolean isModelItem(final ItemStack item) {

        if(item == null || !item.hasItemMeta()) {
            return false;
        }

        final PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        return pdc.has(mainKey, PersistentDataType.STRING);
    }

    public boolean isModelEntity(Entity entity) {

        if(entity.getType() != EntityType.ARMOR_STAND) {
            return false;
        }

        ArmorStand armorStand = (ArmorStand) entity;
        ItemStack helmet = armorStand.getEquipment().getHelmet();

        return isModelItem(helmet);
    }

    public boolean canPlaceHere(Block blockPlace, BlockFace face, Model model) {

        if(blockPlace.getRelative(face).getType() != Material.AIR || !canPlaceModelOnFace(face, model)) {
            return false;
        }
        return true;
    }

    public boolean canPlaceModelOnFace(BlockFace face, Model model) {
        switch (face) {
            case UP: return model.getPlaceType() == PlaceType.GROUND;
            case DOWN: return model.getPlaceType() == PlaceType.CEILING;
            default: return model.getPlaceType() != PlaceType.NONE;
        }
    }


    public boolean equip(Player player, final ItemStack item) {
        final PlayerInventory inventory = player.getInventory();

        if(inventory.getItemInMainHand().getType() != Material.AIR) {
            return false;
        }

        inventory.setItemInMainHand(item);
        return true;
    }

    public Location getArmorStandPlaceLocation(Block block, BlockFace face, BlockFace playerFacing, Model model) {
        Location location = block.getRelative(face).getLocation().add(0.5,.5 + model.getOffsetY(),.5);

        if(model.getPlaceType() == PlaceType.WALL)
            switch (face) {
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

    public void setOwner(final ItemStack item, final Player player) {

        if(item == null || !item.hasItemMeta()) {
            throw new IllegalStateException("Item must not be null when setting owner.");
        }

        final ItemMeta meta = item.getItemMeta();
        final UUID uuid = player.getUniqueId();
        final PersistentDataContainer pdc = meta.getPersistentDataContainer();


        if(uuid == null) {
            pdc.remove(ownerKey);
        } else {
            pdc.set(ownerKey, PersistentDataType.STRING, uuid.toString());
        }

       item.setItemMeta(meta);
    }

    public UUID getOwner(final Model model) {
        final ItemStack item = model.getItem();

        if(item == null || !item.hasItemMeta()) {
            throw new IllegalStateException("Item must not be null when getting owner.");
        }

        final ItemMeta meta = item.getItemMeta();
        final PersistentDataContainer pdc = meta.getPersistentDataContainer();
        final String data = pdc.get(ownerKey, PersistentDataType.STRING);
        final UUID uuid = UUID.fromString(data);
        return uuid;
    }


    public boolean canInteractWithModel(Player player, Model model) {
        final ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(model.getEntityUUID());
        final Location location = armorStand.getLocation();

        boolean canUse = player.getLocation().distance(location) < model.getMaxInteractDistance();


        return canUse;
    }

    public void unEquip(Player player) {
        player.getInventory().setItemInMainHand(null);
    }

    public boolean canTake(final Model model, final Player player) {

        if(!model.isPortable())
            return false;

        final UUID owner = getOwner(model);


        if(!player.getUniqueId().equals(owner)) {
            return false;
        }


        return true;
    }

    public void placeEntity(Block block, BlockFace face, Player player, ItemStack item, Model model) {
        final Location armorStandLocation = getArmorStandPlaceLocation(block, face, player.getFacing(), model);
        final String name = item.getItemMeta().getDisplayName();

        final ArmorStand armorStand = ArmorStandBuilder
                .fromLocation(armorStandLocation)
                .helmet(item)
                .noGravity()
                .invisible()
                .displayName(name)
                .summon();

        model.setEntityUUID(armorStand.getUniqueId());
    }


    public void removeArmorStand(Model model) {
        Bukkit.getEntity(model.getEntityUUID()).remove();
    }


    public Model getModel(ItemStack item) {

        if(item == null || !item.hasItemMeta()) {
            return null;
        }

        final ItemMeta meta = item.getItemMeta();
        final PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if(!pdc.has(mainKey, PersistentDataType.STRING)) {
            return null;
        }

        final String json = pdc.get(mainKey, PersistentDataType.STRING);

        Model model = gson.fromJson(json, Model.class);
        model.setItem(item);

        return model;
    }
}
