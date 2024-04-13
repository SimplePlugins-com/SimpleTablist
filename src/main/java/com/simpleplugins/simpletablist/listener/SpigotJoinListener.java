package com.simpleplugins.simpletablist.listener;

import com.simpleplugins.simpletablist.SimpleTablist;
import com.simpleplugins.simpletablist.core.SpigotPluginCore;
import com.simpleplugins.simpletablist.util.JsonConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class SpigotJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        SpigotPluginCore.getInstance().setTabList(event.getPlayer(), getText("header"), getText("footer"));
    }

    private String getText(String type) {
        final JsonConfig config = SimpleTablist.getJsonConfig();

        if (config.getValues().containsKey(type)) {
            final List<String> list = (List<String>) config.getValues().get(type);

            final StringBuilder builder = new StringBuilder();
            for (String line : list)
                builder.append(line).append("\n");

            builder.setLength(builder.length() - 1);
            return builder.toString();
        }

        return "";
    }
}