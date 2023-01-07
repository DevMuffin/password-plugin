package com.lowfat.pwdpl;

import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PasswordPlugin extends JavaPlugin {
    // Effectively final
    public static PasswordPlugin INSTANCE;
    public static ConversationFactory FACTORY;
    public static String SERVER_PASSWORD = "mcadmin";

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig(); // Save the default config if one doesn't already exist.
        SERVER_PASSWORD = this.getConfig().getString("password");
        FACTORY = new ConversationFactory(INSTANCE);
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), INSTANCE);
    }

    @Override
    public void onDisable() {
        // Prevent reload from clearing unauthorized players set by kicking them and
        // forcing them to rejoin.
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Events.unauthorized.contains(player.getUniqueId())) {
                player.kickPlayer("Unauthorized players must enter the server password!");
            }
        }
    }
}
