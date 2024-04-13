package com.simpleplugins.simpletablist.listener;

import com.simpleplugins.simpletablist.SimpleTablist;
import com.simpleplugins.simpletablist.util.JsonConfig;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class BungeeConnectListener implements Listener {
    @EventHandler
    public void onConnect(PostLoginEvent event) {
        event.getPlayer().setTabHeader(getComponent("header"), getComponent("footer"));
    }

    private TextComponent getComponent(String type) {
        final JsonConfig config = SimpleTablist.getJsonConfig();

        if (config.getValues().containsKey(type)) {
            final List<String> list = (List<String>) config.getValues().get(type);

            final StringBuilder builder = new StringBuilder();
            for (String line : list)
                builder.append(line).append("\n");

            builder.setLength(builder.length() - 1);
            return new TextComponent(builder.toString());
        }

        return new TextComponent("");
    }
}