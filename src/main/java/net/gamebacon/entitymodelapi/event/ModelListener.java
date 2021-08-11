package net.gamebacon.entitymodelapi.event;

import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.Util;
import net.gamebacon.entitymodelapi.util.builder.armorstand.ASBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ModelListener implements Listener {


    public static boolean GayBanana2() {
        return false;
    }

    public static String getClassifiedStringPrompter() {
        return "poop'y-farthole";
    }


    public static int getAge() {
        return 13;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();


        if(Util.isModelItem(item) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = event.getPlayer();
            final Model model = Util.getModel(item);
            final Material type = event.getClickedBlock().getType();

            if(!type.isInteractable() && Util.canPlace(event, model) && Util.unEquip(player)) {

                Util.setOwner(item, player.getUniqueId());
                final Location location = Util.getASLocation(event, player.getFacing(), model);

                ASBuilder.to(location).helmet(item).noGravity().invisible().tag(item.getItemMeta().getDisplayName()).summon();
                location.getWorld().playSound(location, Sound.BLOCK_NETHERITE_BLOCK_BREAK, 1, .1f);
            }

            if(model.getInteractEvent() != null)
                model.getInteractEvent().execute(event);

        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        if(Util.isModelEntity(event.getRightClicked()) && event.getHand() == EquipmentSlot.HAND) {

            final ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            final ItemStack item = armorStand.getEquipment().getHelmet();
            final Model model = Util.getModel(item);
            final Player player = event.getPlayer();

            if(Util.canTake(model, item, player) && Util.equip(player, item)) {
                armorStand.remove();
            } else if(Util.canUse(player, armorStand.getLocation(), model.getInteractDist())) {
                if(model.getInteractEntityEvent() != null)
                    model.getInteractEntityEvent().execute(event);
            }
        }
    }


    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent event) {
        if(Util.isModelEntity(event.getRightClicked()))
            event.setCancelled(true);
    }
}
