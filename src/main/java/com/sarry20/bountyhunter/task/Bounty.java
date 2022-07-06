package com.sarry20.bountyhunter.task;

import com.github.johnnyjayjay.spigotmaps.MapBuilder;
import com.github.johnnyjayjay.spigotmaps.RenderedMap;
import com.github.johnnyjayjay.spigotmaps.rendering.ImageRenderer;
import com.github.johnnyjayjay.spigotmaps.rendering.SimpleTextRenderer;
import com.github.johnnyjayjay.spigotmaps.util.ImageTools;
import com.sarry20.bountyhunter.bountyHunter;
import com.sarry20.bountyhunter.wrapper.BountiesWrapper;
import com.sarry20.bountyhunter.wrapper.PlayerWrapper;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bounty {
  int taskID;

  long ticks;

  private bountyHunter plugin;

  public Bounty(bountyHunter plugin, long ticks) {
    this.plugin = plugin;
    this.ticks = ticks;
  }

  List<String> PlayersRed = new ArrayList();

  public void execute() {
    BukkitScheduler sh = Bukkit.getServer().getScheduler();
    taskID =
        sh.scheduleSyncRepeatingTask(
            plugin,
            new Runnable() {
              public void run() {

                List<PlayerWrapper> playerWrapperList =
                    (List<PlayerWrapper>)
                        plugin.getPlayerHelper().getYamlConfiguration().getList("players");
                List<BountiesWrapper> bountiesWrapperList =
                        (List<BountiesWrapper>)
                                plugin.getPlayerHelper().getYamlConfiguration().getList("bountiers");
                if (playerWrapperList != null) {
                  for (PlayerWrapper playerWrapper : playerWrapperList) {
                    if (playerWrapper.getTeam().equals("sol") || playerWrapper.getTeam().equals("luna")) {
                      if (Bukkit.getPlayer(playerWrapper.getName()) != null) {
                        BountiesWrapper bountiesWrapper =
                                new BountiesWrapper(playerWrapper.getName(), playerWrapper.getUUID(), 0, playerWrapper.getTeam());

                      }
                    }
                  }
                }
                plugin.giveBountyMap(PlayersRed);
                //                random.nextInt(@IntRange(from = 0,to = max)int i);
              }
            },
            0L,
            ticks);
  }
}
