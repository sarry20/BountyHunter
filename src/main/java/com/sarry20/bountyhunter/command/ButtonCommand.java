package com.sarry20.bountyhunter.command;

import com.sarry20.bountyhunter.bountyHunter;
import com.sarry20.bountyhunter.wrapper.BountyWrapper;

import com.sarry20.bountyhunter.wrapper.PlayerWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class ButtonCommand implements CommandExecutor {

  private bountyHunter plugin;

  public ButtonCommand(bountyHunter plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if (!(sender instanceof Player)) {
      sender.sendMessage(
          plugin.getConfigHelper().getStringMessage("§cYou must be a player!", "message.not"));
      return false;
    } else {

      if (args.length >= 1) {
        Player player = (Player) sender;
        String name = args[1].toLowerCase();

        List<Entity> entities = player.getNearbyEntities(5,2,5);
//        if (entities.size() <= 1) {
//          player.sendMessage(
//              plugin
//                  .getConfigHelper()
//                  .getStringMessage("§cThere is no block in your view!", "message.not.block"));
//          return false;
//        }

        for(Entity entity : entities){
          boolean isCitizensNPC = entity.hasMetadata("NPC");
        if(isCitizensNPC && !plugin.checkIfIsInConfig(entity)){
          if (args[0].equalsIgnoreCase("Sol")) {

            int size = plugin.getConfigHelper().getYamlConfiguration().getList("bounties").size();
            BountyWrapper bountyWrapper = new BountyWrapper(entity.getLocation(), name, size,"sol");
            plugin.saveInConfig(bountyWrapper);
            return true;

          }else if(args[0].equalsIgnoreCase("Luna")){

            int size = plugin.getConfigHelper().getYamlConfiguration().getList("bounties").size();
            BountyWrapper bountyWrapper = new BountyWrapper(entity.getLocation(), name, size,"luna");
            plugin.saveInConfig(bountyWrapper);
            return true;

          }else if(args[0].equalsIgnoreCase("Locust")){

            int size = plugin.getConfigHelper().getYamlConfiguration().getList("bounties").size();
            BountyWrapper bountyWrapper = new BountyWrapper(entity.getLocation(), name, size,"locust");
            plugin.saveInConfig(bountyWrapper);
            return true;

          }else{
            sender.sendMessage(plugin
                    .getConfigHelper()
                    .getStringMessage("§cYou need to add valid args","message.incorrect.args"));
            sender.sendMessage(
                    plugin
                            .getConfigHelper()
                            .getStringMessage(
                                    "§c/BGroup red or green and a name ex: /BGroup green button1", "message.syntax"));
          }
          }
        }
      } else {
        sender.sendMessage(
            plugin
                .getConfigHelper()
                .getStringMessage("§cYou need to add more args", "message.more.args"));
        sender.sendMessage(
            plugin
                .getConfigHelper()
                .getStringMessage(
                    "§c/BGroup red or green and a name ex: /BGroup green button1", "message.syntax"));
      }
    }
    return true;
  }
}
