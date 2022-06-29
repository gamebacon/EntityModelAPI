package net.gamebacon.entitymodelapi.util;

import net.gamebacon.entitymodelapi.event.Events;
import net.gamebacon.entitymodelapi.model.Model;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Service {


    /*
        how work with multiple plugins?
     */
    private final Util util;
    private final Plugin main;
    //todo cache models


    public Service(Plugin plugin) {
        this.main = plugin;
        util = new Util(plugin);
        plugin.getServer().getPluginManager().registerEvents(new Events(this), plugin);
    }

    public boolean isModel(Entity entity) {
        return util.isModelEntity(entity);
    }


    public boolean isModelItem(ItemStack item) {
        return util.isModelItem(item);
    }


    public Model tryGetModel(ItemStack item) {
        return util.getModel(item);
    }

    public Model tryGetModel(Entity entity) {
        final ArmorStand armorStand = (ArmorStand) entity;
        final ItemStack helmet = armorStand.getEquipment().getHelmet();
        Model model = tryGetModel(helmet);
        model.setEntityUUID(armorStand.getUniqueId());
        return model;
    }

    public boolean tryPickup(Player player, Model model) {

        if(!util.canTake(model, player)) {
            return false;
        }

        if(!util.equip(player, model.getItem())) {
            player.sendMessage("No space");
            return false;
        }

        util.removeArmorStand(model);

        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1,1);
        return true;
    }

    public boolean tryUse(Player player, Model model, PlayerInteractAtEntityEvent event) {

        if(!util.canInteractWithModel(player, model)) {
            player.sendMessage("too faaaaaaaaaaaaaaaaaaar away!!!!!!!");
            return false;
        }


        if(model.getInteractCommand() != null) {
            final String commandToExecute = model.getInteractCommand().replace("{PLAYER}", player.getName());
            final CommandSender sender = player.getServer().getConsoleSender();
            main.getServer().dispatchCommand(sender, commandToExecute);
        }

        /*
        if(model.getInteractEntityEvent() != null) {
            model.getInteractEntityEvent().execute(event);
        }
         */

        return true;
    }



    public boolean tryPlaceEntity(Player player, ItemStack item, Block interactBlock, BlockFace facing) {

        final Model model = this.tryGetModel(item);
        final World world = player.getWorld();

        if(!util.canPlaceHere(interactBlock, facing, model)) {
            player.sendMessage("No place here.");
            return false;
        }

        util.unEquip(player);
        util.setOwner(item, player);
        util.placeEntity(interactBlock, facing, player, item, model);
        world.playSound(interactBlock.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_BREAK, 1, .1f);

        return true;
    }


}
