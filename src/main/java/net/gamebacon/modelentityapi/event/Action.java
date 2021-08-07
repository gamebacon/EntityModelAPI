package net.gamebacon.modelentityapi.event;

import org.bukkit.event.Event;

public interface Action<T extends Event> {
    public void execute(final T event);
}
