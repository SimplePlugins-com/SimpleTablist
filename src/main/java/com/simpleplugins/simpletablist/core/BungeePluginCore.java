package com.simpleplugins.simpletablist.core;

import com.simpleplugins.simpletablist.SimpleTablist;
import com.simpleplugins.simpletablist.listener.BungeeConnectListener;
import com.simpleplugins.simpletablist.util.JsonConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BungeePluginCore extends Plugin {
    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        final Path path = getDataFolder().toPath().resolve("config.json");
        final JsonConfig config = new JsonConfig(path);

        try {
            config.reload();
        } catch (FileNotFoundException fileNotFoundException) {
            try (InputStream in = getResourceAsStream("config.json")) {
                Files.copy(in, path);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        SimpleTablist.setJsonConfig(config);
        ProxyServer.getInstance().getPluginManager().registerListener(this, new BungeeConnectListener());
    }
}