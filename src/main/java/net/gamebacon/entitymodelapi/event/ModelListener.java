package net.gamebacon.entitymodelapi.event;

import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.ASBuilder;
import net.gamebacon.entitymodelapi.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ModelListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        if(Util.isModelItem(item) && event.getHand() == EquipmentSlot.HEAD) {

            Model model = Util.getModel(item);

            if(model.isPlaceable()) {
                ASBuilder.to(event.getClickedBlock().getLocation().add(0, 1, 0))
                        .helmet(item)
                        .build();
            }

            model.getInteractEvent().execute(event);

        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        if(Util.isModel(event.getRightClicked()) && event.getHand() == EquipmentSlot.HAND) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            ItemStack item = armorStand.getEquipment().getHelmet();
            Model model = Util.getModel(item);

            if (model.isPortable() && event.getPlayer().isSneaking() && Util.equip(event.getPlayer(), item)) {
                armorStand.remove();
            } else
                model.getInteractEntityEvent().execute(event);
        }
    }

    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent event) {
        if(Util.isModel(event.getRightClicked()))
            event.setCancelled(true);
    }
}
