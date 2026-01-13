package com.example.hytale;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import java.util.logging.Level;

public class UiPlugin extends JavaPlugin {
    public UiPlugin(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        getLogger().at(Level.INFO).log("UiPlugin setup");
    }

    @Override
    protected void start() {
        getLogger().at(Level.INFO).log("UiPlugin start");
        getCommandRegistry().registerCommand(new UiTestCommand());
        getCommandRegistry().registerCommand(new UiCommand());
    }

    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("UiPlugin shutdown");
    }
}
