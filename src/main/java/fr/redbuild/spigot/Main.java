package fr.redbuild.spigot;

import net.kyori.adventure.text.minimessage.MiniMessage;
import fr.redbuild.models.spigot.plugin.PluginController;
import fr.redbuild.models.spigot.scoreboard.ScoreBoard;
import fr.redbuild.models.spigot.utils.injector.Injector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public class Main extends PluginController {

    public static Main INSTANCE;
    final int[] origineParcelle = { 6, 6 };
    final int tailleParcelle = 44;
    final int intervalle = 12;
    @Override
    public void onLoad() {
        setPluginPackage("fr.redbuild.spigot");
    }

    @Override
    public void pluginStart() {
        System.out.println("Plugin enable");
        INSTANCE = this;
        new ScoreBoard("main", Injector.getInstance(MiniMessage.class).deserialize("<dark_red>Red<gold>Build"))
                .addLine(2, "grade : Aucun")
                .addLine(1,
                        ChatColor.DARK_RED + "Red" + ChatColor.GOLD + "Build" + ChatColor.WHITE + "." + ChatColor.GOLD
                                + "playit.gg") 
                .addLine(0, ChatColor.RED + "[1.19.4]").registerAll();
        // Injector.getInstance(CodecController.class).registerCodecProvider(CodecRegistries.fromProviders(PojoCodecProvider.builder().register().build()));
         getServer().getScheduler().runTaskTimer(this, () -> {
            Bukkit.broadcast(Injector.getInstance(MiniMessage.class).deserialize("<bold><dark_red>Red<gold>Build <gray> » <yellow>Le serveur est en cours de développement, merci de votre compréhension."));
        }, 0, 6000);
    }

    @Override
    public String dbName() {
        return getConfig().getString("database.name");
    }

    @Override
    public void pluginStop() {
    }

}
