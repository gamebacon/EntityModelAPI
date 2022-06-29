package net.gamebacon.entitymodelapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class Logger {

    public void severe(String msg) {
        Bukkit.getLogger().severe(msg);
    }

}
