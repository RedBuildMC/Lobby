package fr.redbuild.spigot.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.Gui.GuiBuilder;
import fr.redbuild.models.spigot.Gui.ItemBuilder;
import fr.redbuild.models.spigot.bungeecord.ServerManager;
import fr.redbuild.models.spigot.mode.BuildMode;
import fr.redbuild.models.spigot.packet.PacketUtils;
import fr.redbuild.models.spigot.region.RegionController;
import fr.redbuild.spigot.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;

public class PlayerListener implements Listener {
    @Autowired
    private BuildMode buildMode;

    @Autowired
    private RegionController regionController;

    @Autowired
    private ServerManager serverManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Component header = Component.Serializer.fromJson("[\"\",{\"text\":\"caca\",\"obfuscated\":true,\"color\":\"dark_purple\"},{\"text\":\" \\u2583\\u2585\\u2587\\u2589 \",\"color\":\"gray\"},{\"text\":\"Red\",\"color\":\"dark_red\"},{\"text\":\"Build \",\"color\":\"gold\"},{\"text\":\"\\u2589\\u2587\\u2585\\u2583 \",\"color\":\"gray\"},{\"text\":\"caca\",\"obfuscated\":true,\"color\":\"dark_purple\"}]");
        Component footer = Component.Serializer.fromJson("{\"text\":\"Bienvenue sur RedBuild !\",\"color\":\"gray\"}");
        PacketUtils.sendPacket(event.getPlayer(), new ClientboundTabListPacket(header, footer));
        event.getPlayer().teleport(new Location(Bukkit.getWorld(Main.INSTANCE.getConfig().getString("lobby.name")), Main.INSTANCE.getConfig().getDouble("lobby.spawn.x"), Main.INSTANCE.getConfig().getDouble("lobby.spawn.y"), Main.INSTANCE.getConfig().getDouble("lobby.spawn.z"), Main.INSTANCE.getConfig().getInt("lobby.spawn.yaw"), Main.INSTANCE.getConfig().getInt("lobby.spawn.pitch")));
        event.getPlayer().getInventory().setItem(4,new ItemBuilder(Material.CLOCK, "<gold>Menu").desc("Cliquer pour ouvrir le Menu").onInteract((player,action,e) -> {
            openMenu(player);
            e.setCancelled(true);
        }).onDrop(((player, ignored, e) -> {
            openMenu(player);
            e.setCancelled(true);
        })).onClick((e, p) -> {
            e.setCancelled(true);
            openMenu(p);
        }).build());
    }
    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event){
        ItemStack item = new ItemBuilder(Material.CLOCK, "<gold>Menu").desc("Cliquer pour ouvrir le Menu").onInteract((player,action,e) -> {
            openMenu(player);
            e.setCancelled(true);
        }).onDrop(((player, ignored, e) -> {
            openMenu(player);
            e.setCancelled(true);
        })).onClick((e, p) -> {
            e.setCancelled(true);
            openMenu(p); 
        }).build();
        if (event.getPlayer().getLocation().getWorld().getName().equals(Main.INSTANCE.getConfig().getString("lobby.name"))){
            event.getPlayer().getInventory().setItem(4,item);
        }else{
            if(event.getPlayer().getInventory().contains(item))
                event.getPlayer().getInventory().remove(item);
        }
    }

    public void openMenu(Player player){
            Material comingMaterial = Material.STRUCTURE_VOID;
            String comingName = "<red>Coming soon...";
            new GuiBuilder("<gold>Menu").rows(6).fillSides(Material.ORANGE_STAINED_GLASS_PANE)
            .item(new ItemBuilder(Material.BARRIER, "Report").desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(0))
            .item(new ItemBuilder(Material.END_CRYSTAL, "Support").desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(8))
            .item(new ItemBuilder(Material.WRITTEN_BOOK, "<blue>Discord").desc("<green>Cliquez pour rejoindre notre discord !").setPos(53).onClick((e, p) -> {
                e.setCancelled(true);
                p.closeInventory();
                p.sendMessage("§aCliquez sur ce lien pour rejoindre notre discord : §9https://discord.gg/Xbauh7TvwT");
             } ))
            .item(new ItemBuilder(Material.GOLD_NUGGET, "<yellow>Boutique").desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(45))
            .item(Material.ORANGE_STAINED_GLASS_PANE,13, "")
            .item(Material.ORANGE_STAINED_GLASS_PANE,40, "")
            .item(Material.ORANGE_STAINED_GLASS_PANE,19, "")
            .item(Material.ORANGE_STAINED_GLASS_PANE,25, "")
            .item(Material.ORANGE_STAINED_GLASS_PANE,28, "")
            .item(Material.ORANGE_STAINED_GLASS_PANE,34, "")
            .item(new ItemBuilder(Material.GREEN_CONCRETE, "<green>Espace BETA").desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(10))
            .item(new ItemBuilder(Material.ENDER_EYE, "<gold>Joueur : <green>tous").desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(16))
            .item(new ItemBuilder(Material.PLAYER_HEAD, "Profil").skullOwner(player.getName()).desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(22))
            .item(comingMaterial, 29, comingName)
            .item(comingMaterial, 30, comingName)
            .item(new ItemBuilder(Material.GRASS_BLOCK, "<green>PlotWorld").desc("Click to join the plotworld").setPos(31).onClick(() -> {
                serverManager.connectPlayer(Main.INSTANCE.getConfig().getString("server-lobby"), player);
            }))
            .item(comingMaterial, 32, comingName)
            .item(comingMaterial, 33, comingName)
            .item(new ItemBuilder(Material.ENDER_CHEST, "<gold>Loot box").desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(37))
            .item(new ItemBuilder(Material.LIGHT, "Suggestion").desc("<red>Cette fonctionnalité n'est pas encore disponible.").setPos(43))
            .open(player);
    }

    @EventHandler
    public void onEntityExplose(EntityExplodeEvent event) {
        if (buildMode.isSafeRegion(regionController.getRegion(event.getLocation())))
            event.setCancelled(true);
    }
}
