package me.offloner.vaultsplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class VaultsPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println("Plugin has been enabled!");
        File dataFolder = new File(getDataFolder().getAbsolutePath(),"data");
        if (!dataFolder.exists()) dataFolder.mkdir();
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin has been disabled!");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("caja") || command.getName().equalsIgnoreCase("vault")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Get Config.yml
                String title = getConfig().getString("vault.title").replace("{player}", player.getName());
                int size = getConfig().getInt("vault.size");

                // multiple between 9 and 54
                if (size < 9 || size > 54 || size % 9 != 0) {
                    size = 54;
                }
                Inventory inv = Bukkit.createInventory(null, size, title);
                inv.setContents(loadInventory(player, size));

                player.playSound(player.getLocation(),Sound.BLOCK_ENDER_CHEST_OPEN,1.0f,1.0f);

                player.openInventory(inv);
            } else {
                String msg = getConfig().getString("vault.messageConsole");
                sender.sendMessage(msg);
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onClose (InventoryCloseEvent e){
        if (!(e.getPlayer() instanceof Player)) return;

        String titleConfig = getConfig().getString("vault.title").replace("{player}", e.getPlayer().getName());
        String titleConfigWithoutColor = ChatColor.stripColor(titleConfig);
        String titleInventory = ChatColor.stripColor(e.getView().getTitle());

        if (titleInventory.equals(titleConfigWithoutColor)) {
            Player player = (Player) e.getPlayer();
            UUID uuid = player.getUniqueId();
            Inventory inv = e.getInventory();
            ItemStack[] items = e.getInventory().getContents();
            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
            Bukkit.getScheduler().runTask(this, () -> {
                saveInventory(player, inv.getContents());
            });
        }
    }
    public void saveInventory(Player player, ItemStack[] items){
        try {
            File file = new File(getDataFolder(), "data/" + player.getUniqueId() + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (int i=0; i<items.length; i++){
                config.set("vault.slot" +i,items[i]);
            }
            config.save(file);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public ItemStack[] loadInventory(Player player, int size){
        File file = new File(getDataFolder(), "data/" + player.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        ItemStack[] contenido = new ItemStack[size];
        for (int i=0; i<size; i++){
            contenido[i] = config.getItemStack("vault.slot"+i);
        }
        return contenido;
    }
}
