package com.simpleplugins.simpletablist.util;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonConfig {
    @Getter
    private final Map<String, Object> values = new HashMap<>();

    private final Path path;

    public JsonConfig(@NotNull Path path) {
        this.path = path;
    }

    public void reload() throws FileNotFoundException {
        if (Files.notExists(path))
            throw new FileNotFoundException();

        try {
            values.putAll(new GsonBuilder().setPrettyPrinting().create().fromJson(Files.newBufferedReader(path), values.getClass()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}