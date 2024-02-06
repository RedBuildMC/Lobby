package fr.redbuild.spigot.commands.tp;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.commands.Cmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.spigot.Main;

public class SpawnCmd extends Cmd{

    public SpawnCmd() {
        super("spawn", "commande pour se tp au spawn");
    }

    @Override
    public void execute(CommandSender arg0, String arg1, Argument arg2) {
        if(arg0 instanceof Player player){
            double x = Main.INSTANCE.getConfig().getDouble("world.spawn.x");
            double y = Main.INSTANCE.getConfig().getDouble("world.spawn.y");
            double z = Main.INSTANCE.getConfig().getDouble("world.spawn.z");
            float yaw = (float) Main.INSTANCE.getConfig().getDouble("world.spawn.yaw");
            float pitch = (float) Main.INSTANCE.getConfig().getDouble("world.spawn.pitch");
            player.teleport(new Location(player.getWorld(), x, y, z,yaw,pitch));
        }else{
            arg0.sendMessage("Vous devez être un joueur pour executer cette commande");
        }
    }
    
}
