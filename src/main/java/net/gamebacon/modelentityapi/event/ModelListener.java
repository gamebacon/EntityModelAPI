package net.gamebacon.modelentityapi.event;

import net.gamebacon.modelentityapi.model.Model;
import net.gamebacon.modelentityapi.util.Util;
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

        Model model = (Model) item;
        Bukkit.broadcastMessage(model.getItemMeta().getDisplayName());
        model.getInteractEvent().execute(event);
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        if(!Util.isModel(event.getRightClicked())) return;

        ArmorStand armorStand = (ArmorStand) event.getRightClicked();


    }
}
