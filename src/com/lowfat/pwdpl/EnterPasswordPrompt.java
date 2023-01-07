package com.lowfat.pwdpl;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnterPasswordPrompt implements Prompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.RED + "Enter the server password to play!";
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context) {
        return true;
    }

    @Override
    public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
        Conversable conversable = context.getForWhom();
        if (!(conversable instanceof Player)) {
            // ???
            return null;
        }
        Player player = (Player) conversable;
        if (input.equals(PasswordPlugin.SERVER_PASSWORD)) {
            Events.unauthorized.remove(player.getUniqueId());
            player.setGameMode(GameMode.SURVIVAL);
            player.sendTitle(ChatColor.GREEN + "AUTHORIZED", "thank you!");
            return null;
        }
        player.sendTitle(ChatColor.RED + "INCORRECT", "please enter the correct password!");
        return new EnterPasswordPrompt();
    }
}
