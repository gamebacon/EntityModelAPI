package net.gamebacon.entitymodelapi.api;

import net.gamebacon.entitymodelapi.util.Service;
import org.bukkit.plugin.Plugin;

public class EntityAPI {

    private final Service service;
    private final Plugin plugin;

    public EntityAPI(Plugin plugin) {
        this.plugin = plugin;
        service = new Service(plugin);
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
