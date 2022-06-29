package net.gamebacon.entitymodelapi.event;

import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.Service;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    private final Service service;

    public Events(Service service) {
        this.service = service;
    }

    /*
        PLACE ENTITY
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {

        final ItemStack item = event.getItem();
        final Model model = service.tryGetModel(item);

        if(model == null) {
            return;
        }

        /*
            Only allow block interactions with an item & main hand.
         */
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && item != null && event.getHand() == EquipmentSlot.HAND) {

            final Player player = event.getPlayer();
            final Material material = event.getClickedBlock().getType();

            /*
                Check if player is holding the entity/model item
             */
            if(!service.isModelItem(item)) {
                return;
            }

            /*
                Only allow placing on interactable blocks if sneaking.
             */
            if(material.isInteractable() && !player.isSneaking()) {
                return;
            }


            service.tryPlaceEntity(player, item, event.getClickedBlock(), event.getBlockFace());


        }

        /*
            todo IDK ?
        if(model.getInteractEvent() != null)
            model.getInteractEvent().execute(event);
         */

    }

    /*
            USE ENTITY
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {

        if(event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final Model model = service.tryGetModel(event.getRightClicked());

        if(model == null) {
            return;
        }

        final Player player = event.getPlayer();

        if(player.isSneaking()) {
            service.tryPickup(player, model);
        } else {
            service.tryUse(player, model, event);
        }

    }


    /*
        CHANGE ENTITY
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onManipulate(PlayerArmorStandManipulateEvent event) {

        if(service.isModel(event.getRightClicked())) {
            event.setCancelled(true);
        }

    }
}
