package net.gamebacon.modelentityapi;

import net.gamebacon.modelentityapi.model.Model;
import net.gamebacon.modelentityapi.util.ModelBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class Driver {


    public static void main(String[] args) {
        Model model = ModelBuilder.from(Material.GOLDEN_AXE, 2)
                .name(Component.text("Test"))
                .onInteract(event -> {
                    System.out.println("interact custom");
                })
                .bulid();
    }
}
