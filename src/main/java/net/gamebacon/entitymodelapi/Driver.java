package net.gamebacon.entitymodelapi;

import net.gamebacon.entitymodelapi.model.Model;
import net.gamebacon.entitymodelapi.util.ModelBuilder;
import org.bukkit.Material;

public class Driver {


    public static void main(String[] args) {
        Model model = ModelBuilder.from(Material.GOLDEN_AXE, 2)
                .name("Test")
                .onInteract(event -> {
                    System.out.println("interact custom");
                })
                .bulid();
    }
}
