package me.offloner.backpack1;

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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

public final class Backpack1 extends JavaPlugin implements Listener {

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

                // Obtener configuración
                String title = getConfig().getString("vault.title").replace("{player}", player.getName());
                int size = getConfig().getInt("vault.size");

                Inventory inv = Bukkit.createInventory(null, size, title);
                inv.setContents(cargarInventario(player));

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
        if (ChatColor.stripColor(e.getView().getTitle()).startsWith("Caja fuerte de")){
            Player player = (Player) e.getPlayer();
            UUID uuid = player.getUniqueId();
            Inventory inv = e.getInventory();
            ItemStack[] items = e.getInventory().getContents();
            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
            Bukkit.getScheduler().runTask(this, () -> {
                guardarInventario(player, inv.getContents());
            });
        }
    }
    public void guardarInventario(Player player, ItemStack[] items){
        try {
            File file = new File(getDataFolder(), "data/" + player.getUniqueId() + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (int i=0; i<items.length; i++){
                config.set("caja.slot" +i,items[i]);
            }
            config.save(file);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public ItemStack[] cargarInventario(Player player){
        File file = new File(getDataFolder(), "data/" + player.getUniqueId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ItemStack[] contenido = new ItemStack[54];
        for (int i=0; i<contenido.length; i++){
            contenido[i] = config.getItemStack("caja.slot"+i);
        }
        return contenido;
    }
}
