package com.sarry20.bountyhunter.listener;

import com.sarry20.bountyhunter.bountyHunter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {
  private bountyHunter plugin;

  public KillListener(bountyHunter plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onKill(PlayerDeathEvent event) {
    Player player = event.getEntity().getPlayer();
    Player killer = event.getEntity().getKiller();
    if (plugin.getBname() != null) {
      if (plugin.getBname().equals(player.getName())) {
        plugin.setBname("");

      }
      if(plugin.getBname().equals(killer.getName())){

      }
    }else{
//    Bukkit.getConsoleSender().sendMessage(plugin.getConfigHelper().getStringMessage("An error ocurred executing the plugin","message.fatal.error"));
    }

  }
}
