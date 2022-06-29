package net.gamebacon.entitymodelapi.util.builder.armorstand;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ArmorStandBuilder {

    private final ArmorStand armorStand;


    private ArmorStandBuilder(Location location) {
        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        resetPose();
    }

    public static ArmorStandBuilder fromLocation(Location location) {
        return new ArmorStandBuilder(location);
    }

    public void resetPose() {
        armorStand.setBodyPose(EulerAngle.ZERO);
        armorStand.setLeftArmPose(EulerAngle.ZERO);
        armorStand.setRightArmPose(EulerAngle.ZERO);
        armorStand.setLeftLegPose(EulerAngle.ZERO);
        armorStand.setRightLegPose(EulerAngle.ZERO);
    }

    public ArmorStandBuilder displayName(String name) {
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);
        return this;
    }

    public ArmorStandBuilder marker() {
        armorStand.setMarker(true);
        return this;
    }

    public ArmorStandBuilder small() {
        armorStand.setSmall(true);
        return this;
    }

    public ArmorStandBuilder invisible() {
        armorStand.setVisible(false);
        return this;
    }

    public ArmorStandBuilder noPlate() {
        armorStand.setBasePlate(false);
        return this;
    }

    public ArmorStandBuilder noGravity() {
        armorStand.setGravity(false);
        return this;
    }

    public ArmorStandBuilder glow() {
        armorStand.setGlowing(true);
        return this;
    }

    public ArmorStandBuilder noCollide() {
        armorStand.setCollidable(false);
        return this;
    }

    public ArmorStandBuilder helmet(ItemStack helmet) {
        armorStand.getEquipment().setHelmet(helmet, false);
        return this;
    }

    public ArmorStand summon() {
        return armorStand;
    }


}
