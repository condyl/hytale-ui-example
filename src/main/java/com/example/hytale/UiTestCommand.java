package com.example.hytale;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import javax.annotation.Nonnull;

public class UiTestCommand extends CommandBase {
    public UiTestCommand() {
        super("uiplugin", "Confirms that UiPlugin is loaded");
    }

    @Override
    protected void executeSync(@Nonnull CommandContext context) {
        context.sender().sendMessage(Message.raw("UiPlugin ok"));
    }
}
