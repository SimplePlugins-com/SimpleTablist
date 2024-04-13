package com.simpleplugins.simpletablist.util;

import org.bukkit.Bukkit;

public enum Version {
    v1_8,
    v1_9,
    v1_10,
    v1_11,
    v1_13;

    public boolean isLess() {
        return ordinal() > getVersion().ordinal();
    }

    public Version getVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        for (Version target : Version.values())
            if (version.contains(target.name()))
                return target;

        return Version.v1_13;
    }
}