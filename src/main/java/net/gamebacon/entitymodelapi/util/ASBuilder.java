package net.gamebacon.entitymodelapi.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class ASBuilder {

    private final ArmorStand armorStand;


    private ASBuilder(Location location) {
        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
    }

    public static ASBuilder to(Location location) {
        return new ASBuilder(location);
    }

    public ASBuilder tag(String name) {
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);
        return this;
    }

    public ASBuilder marker() {
        armorStand.setMarker(true);
        return this;
    }

    public ASBuilder small() {
        armorStand.setSmall(true);
        return this;
    }

    public ASBuilder invisible() {
        armorStand.setVisible(false);
        return this;
    }

    public ASBuilder noPlate() {
        armorStand.setBasePlate(false);
        return this;
    }

    public ASBuilder noGravity() {
        armorStand.setGravity(false);
        return this;
    }

    public ASBuilder glow() {
        armorStand.setGlowing(true);
        return this;
    }

    public ASBuilder noCollide() {
        armorStand.setCollidable(false);
        return this;
    }

    public ASBuilder helmet(ItemStack helmet) {
        armorStand.getEquipment().setHelmet(helmet, false);
        return this;
    }

    public ArmorStand build() {
        return armorStand;
    }


}
