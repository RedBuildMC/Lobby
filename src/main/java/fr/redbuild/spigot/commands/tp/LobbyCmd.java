package fr.redbuild.spigot.commands.tp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.bungeecord.ServerManager;
import fr.redbuild.models.spigot.commands.Cmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.spigot.Main;

public class LobbyCmd extends Cmd{
    @Autowired
    private ServerManager serverManager;

    public LobbyCmd() {
        super("lobby", "commande pour se tp au slobby");
        setPermission("redbuild.tp.lobby");// temporaire

    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument arg2) {
        if(sender instanceof Player player){
            serverManager.connectPlayer(Main.INSTANCE.getConfig().getString("server-lobby"), player);
        }else{
            sender.sendMessage("Vous devez être un joueur pour executer cette commande");
        }
    }
    
}
