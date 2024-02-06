package fr.redbuild.spigot.commands.npc;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.commands.Cmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.commands.defaultarguments.LocationArgument;
import fr.redbuild.models.spigot.commands.defaultarguments.StringArgument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.models.spigot.npc.NPC;

public class NPCCmd extends Cmd {

    public NPCCmd() {
        super("npc", "spawn a npc");
        rc(1, new LocationArgument("location",false));
        rc(4, new StringArgument("name",false));
        setPermission("redbuild.staff");
    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument arg2) {
        if(sender instanceof Player player){
            NPC npc = new NPC(player,arg2.get(1, Location.class));
            npc.setName(arg2.get(4, String.class));
            npc.spawn();
            // player.sendMessage("§aYou have spawn a npc");
            CtMsg.sendMessage("§aYou have spawn a npc", player);
        }
    }
    
}
