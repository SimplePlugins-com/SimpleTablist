package com.simpleplugins.simpletablist.core;

import com.simpleplugins.simpletablist.SimpleTablist;
import com.simpleplugins.simpletablist.listener.SpigotJoinListener;
import com.simpleplugins.simpletablist.util.JsonConfig;
import com.simpleplugins.simpletablist.util.Version;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.logging.Level;

@SoftDependency("PlaceholderAPI")

@Author("Rabiminer")
@Plugin(name = "SimpleTablist", version = "1.0.1")
public class SpigotPluginCore extends JavaPlugin {
    private static SpigotPluginCore instance;

    @Override
    public void onEnable() {
        instance = this;

        final JsonConfig config = new JsonConfig(getDataFolder().toPath().resolve("config.json"));

        try {
            config.reload();
        } catch (FileNotFoundException e) {
            saveResource("config.json", false);
        }

        SimpleTablist.setJsonConfig(config);
        Bukkit.getPluginManager().registerEvents(new SpigotJoinListener(), this);
    }

    public void setTabList(@NotNull Player player, @NotNull String header, @NotNull String footer) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            header = PlaceholderAPI.setPlaceholders(player, header);
            footer = PlaceholderAPI.setPlaceholders(player, footer);
        }

        if (Version.v1_13.isLess()) {
            try {
                Object headerObject = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
                Object footerObject = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");

                final Constructor<?> constructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor();
                final Object packet = constructor.newInstance();

                try {
                    setField(packet, "a", headerObject);
                    setField(packet, "b", footerObject);
                } catch (Exception e) {
                    setField(packet, "header", headerObject);
                    setField(packet, "footer", footerObject);
                }

                final Object handle = player.getClass().getMethod("getHandle").invoke(player);
                final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
                playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.getLogger().log(Level.SEVERE, "Could not send packet");
            }

            return;
        }

        player.setPlayerListHeaderFooter(header, footer);
    }

    private Class<?> getNMSClass(@NotNull String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
    }

    private void setField(@NotNull Object packet, @NotNull String declaredField, @NotNull Object object) throws Exception {
        final Field field = packet.getClass().getDeclaredField(declaredField);
        field.setAccessible(true);
        field.set(packet, object);
    }

    public static SpigotPluginCore getInstance() {
        return instance;
    }
}
