package pl.polsatgranie.itomsd.socialMedia;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class SocialMedia extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 22940);
        this.getLogger().info("""
                
                ------------------------------------------------------------
                |                                                          |
                |      _  _______        __     __    _____   ____         |
                |     | ||___ ___|      |  \\   /  |  / ____| |  _ \\        |
                |     | |   | |   ___   | |\\\\ //| | | (___   | | \\ \\       |
                |     | |   | |  / _ \\  | | \\_/ | |  \\___ \\  | |  ) )      |
                |     | |   | | | (_) | | |     | |  ____) | | |_/ /       |
                |     |_|   |_|  \\___/  |_|     |_| |_____/  |____/        |
                |                                                          |
                |                                                          |
                ------------------------------------------------------------
                |                 +==================+                     |
                |                 |    SocialMedia   |                     |
                |                 |------------------|                     |
                |                 |        1.0       |                     |
                |                 |------------------|                     |
                |                 |  PolsatGraniePL  |                     |
                |                 +==================+                     |
                ------------------------------------------------------------
                """);
        this.saveDefaultConfig();
        config = this.getConfig();

        this.getCommand("discord").setExecutor(new SocialCommandExecutor("discord"));
        this.getCommand("dc").setExecutor(new SocialCommandExecutor("discord"));
        this.getCommand("youtube").setExecutor(new SocialCommandExecutor("youtube"));
        this.getCommand("yt").setExecutor(new SocialCommandExecutor("youtube"));
        this.getCommand("instagram").setExecutor(new SocialCommandExecutor("instagram"));
        this.getCommand("ig").setExecutor(new SocialCommandExecutor("instagram"));
        this.getCommand("website").setExecutor(new SocialCommandExecutor("website"));
        this.getCommand("www").setExecutor(new SocialCommandExecutor("website"));
        this.getCommand("tiktok").setExecutor(new SocialCommandExecutor("tiktok"));
        this.getCommand("tt").setExecutor(new SocialCommandExecutor("tiktok"));
        this.getCommand("itomsd").setExecutor(((commandSender, command, s, strings) -> {
            commandSender.sendMessage("§a");
            commandSender.sendMessage("§a");
            commandSender.sendMessage("§a§lIToMSD links");
            commandSender.sendMessage("§aDiscord§8: §7https://dc.itomsd.com/");
            commandSender.sendMessage("§aYoutube§8: §7https://www.youtube.com/@IToMSD");
            commandSender.sendMessage("§aWebsite§8: §7https://itomsd.com/");
            commandSender.sendMessage("§aTiktok§8: §7https://www.tiktok.com/@itomsd");
            commandSender.sendMessage("§a");
            commandSender.sendMessage("§a");
            return true;
        }));
        this.getCommand("smreload").setAliases(List.of("socialmediareload"));
        this.getCommand("smreload").setExecutor((sender, command, label, args) -> {
            if (sender.hasPermission("itomsd.socialmedia.reload")) {
                reloadConfig();
                config = getConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString("plugin-reloaded", "Plugin reloaded")));
            } else {
                sender.sendMessage( ChatColor.translateAlternateColorCodes('&',ChatColor.RED + config.getString("no-permission-message")));
            }
            return true;
        });
    }

    private class SocialCommandExecutor implements CommandExecutor {
        private final String platform;

        public SocialCommandExecutor(String platform) {
            this.platform = platform;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String link = config.getString("links." + platform);
                String format = ChatColor.translateAlternateColorCodes('&',config.getString("format", "Link: %link%"));

                if (link != null && !link.isEmpty()) {
                    String message = format.replace("%link%", link);

                    TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));

                    player.spigot().sendMessage(textComponent);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',ChatColor.RED + config.getString("link-no-available")));
                }
                return true;
            }
            return false;
        }
    }
}
