package com.example.hytale;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UiPage extends InteractiveCustomUIPage<UiPage.PageEventData> {
    private static final String ACTION_CLOSE = "Close";
    private static final String INLINE_UI = loadUiTemplate("/ui/UiPage.ui");

    public UiPage(@Nonnull PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss, PageEventData.CODEC);
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, @Nonnull Store<EntityStore> store) {
        commandBuilder.append("Pages/PluginListPage.ui");
        commandBuilder.set("#PluginList.Visible", false);
        commandBuilder.set("#SearchOptions.Visible", false);
        commandBuilder.set("#PluginName.Text", "UiPlugin");
        commandBuilder.set("#PluginIdentifier.Text", "UiPlugin");
        commandBuilder.set("#PluginVersion.Text", "1.0.0");
        commandBuilder.set("#PluginDescription.Text", "");
        commandBuilder.appendInline("#Content", INLINE_UI);
        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#CloseButton", EventData.of("Action", ACTION_CLOSE));
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull PageEventData data) {
        if (!ACTION_CLOSE.equals(data.getAction())) {
            return;
        }
        Player playerComponent = store.getComponent(ref, Player.getComponentType());
        if (playerComponent == null) {
            return;
        }
        playerComponent.getPageManager().setPage(ref, store, Page.None);
    }

    public static class PageEventData {
        public static final BuilderCodec<PageEventData> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PageEventData.class, PageEventData::new).append(new KeyedCodec<String>("Action", Codec.STRING), (entry, s) -> {
            entry.action = s;
        }, entry -> entry.action).add()).build();
        @Nullable
        private String action;

        @Nullable
        public String getAction() {
            return this.action;
        }
    }

    private static String loadUiTemplate(String resourcePath) {
        try (InputStream input = UiPage.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("UI template not found: " + resourcePath);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read UI template: " + resourcePath, e);
        }
    }
}
