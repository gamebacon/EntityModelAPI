package net.gamebacon.entitymodelapi.event;

import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ModelListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();

        if(!Util.isModelItem(item)) return;

        Model model = Util.models.get(Util.getModel(item).getUuid());
        Bukkit.broadcastMessage(model.getItem().getItemMeta().getDisplayName());
        model.getInteractEvent().execute(event);
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        if(!Util.isModel(event.getRightClicked())) return;

        ArmorStand armorStand = (ArmorStand) event.getRightClicked();


    }
}
