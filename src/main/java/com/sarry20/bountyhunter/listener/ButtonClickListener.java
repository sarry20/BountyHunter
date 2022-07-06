package com.sarry20.bountyhunter.listener;

import com.sarry20.bountyhunter.bountyHunter;
import com.sarry20.bountyhunter.wrapper.BountyWrapper;
import com.sarry20.bountyhunter.wrapper.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.Optional;

public class ButtonClickListener implements Listener {

  private bountyHunter plugin;

  public ButtonClickListener(bountyHunter plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onClick(PlayerInteractEvent event) {
    if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
      return;
    }

    Block block = event.getClickedBlock();
    if (block.getType() == Material.STONE_BUTTON) {
      if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        Player player = event.getPlayer();

        List<BountyWrapper> bountyWrapperList =
            (List<BountyWrapper>)
                plugin.getConfigHelper().getYamlConfiguration().getList("bounties");
        for (int i = 0; i <= bountyWrapperList.size() - 1; i++) {
          if (bountyWrapperList.get(i).getLocation().equals(block.getLocation())) {
            if (bountyWrapperList.get(i).getType().equals("red")) {

              List<PlayerWrapper> playerWrapperList =
                  (List<PlayerWrapper>)
                      plugin.getPlayerHelper().getYamlConfiguration().getList("players");
              // any match returns if there is an entire with the following predicts in it (same as
              // filter)

              //              boolean isInListWay2 =
              //                  playerWrapperList.stream()
              //                          .filter(
              //                              playerWrapper ->
              // playerWrapper.getUUID().equals(player.getUniqueId()))
              //                          .count()
              //                      != 0;

              // we dont must use it but we can since pWrapper uses player.getUniqueID() and we also
              // use it so it doesnt really matter ahh ok ok
              String team;

              Optional<PlayerWrapper> maybePlayer =
                  playerWrapperList.stream()
                      .filter(
                          playerWrapper ->
                              playerWrapper.getUUID().equals(player.getUniqueId().toString()))
                      .findAny();
              PlayerWrapper foundWrapper = null;
              if (maybePlayer.isPresent()) {
                 foundWrapper = maybePlayer.get();
              }

              boolean isInList =
                  playerWrapperList.stream()
                      .anyMatch(
                          playerWrapper ->
                              playerWrapper.getUUID().equals(player.getUniqueId().toString()));
              if (isInList) {

                if (plugin.chechCooldown(foundWrapper)) {
                } else {
                  player.sendMessage(
                      plugin
                          .getConfigHelper()
                          .getStringMessage(
                              "&cYou have in cooldown the change of team!",
                              "message.error.cooldown"));
                  return;
                }
                if (foundWrapper.getTeam().equals("red")) {
                  player.sendMessage(
                      plugin
                          .getConfigHelper()
                          .getStringMessage(
                              "&cYou already in this group", "message.error.sameteam"));
                  return;
                }
                plugin.deleteFromPlayer(player.getUniqueId().toString());
              }
              PlayerWrapper pWrapper =
                  new PlayerWrapper(
                      player.getName(),
                      player.getUniqueId().toString(),
                      System.currentTimeMillis(),
                      "red");
              plugin.saveInPlayers(pWrapper);

            } else if (bountyWrapperList.get(i).getType().equals("green")) {
              List<PlayerWrapper> playerWrapperList =
                      (List<PlayerWrapper>)
                              plugin.getPlayerHelper().getYamlConfiguration().getList("players");
              String team;

              Optional<PlayerWrapper> maybePlayer =
                      playerWrapperList.stream()
                              .filter(
                                      playerWrapper ->
                                              playerWrapper.getUUID().equals(player.getUniqueId().toString()))
                              .findAny();
              PlayerWrapper foundWrapper = null;
              if (maybePlayer.isPresent()) {
                foundWrapper = maybePlayer.get();
              }

              boolean isInList =
                      playerWrapperList.stream()
                              .anyMatch(
                                      playerWrapper ->
                                              playerWrapper.getUUID().equals(player.getUniqueId().toString()));
              if (isInList) {

                if (plugin.chechCooldown(foundWrapper)) {
                } else {
                  player.sendMessage(
                          plugin
                                  .getConfigHelper()
                                  .getStringMessage(
                                          "&cYou have in cooldown the change of team!",
                                          "message.error.cooldown"));
                  return;
                }
                if (foundWrapper.getTeam().equals("green")) {
                  player.sendMessage(
                          plugin
                                  .getConfigHelper()
                                  .getStringMessage(
                                          "&cYou already in this group", "message.error.sameteam"));
                  return;
                }
                plugin.deleteFromPlayer(player.getUniqueId().toString());
              }
              PlayerWrapper pWrapper =
                      new PlayerWrapper(
                              player.getName(),
                              player.getUniqueId().toString(),
                              System.currentTimeMillis(),
                              "green");
              plugin.saveInPlayers(pWrapper);
            }
          }
        }
        //        System.out.println(bountyWrapperList.get(1).getLocation());
        //        System.out.println(block.getLocation());

        //                plugin.getConfigHelper()
        //                        .getYamlConfiguration()
        //                        .set(
        //                                "bounties",
        //                                bountyWrapperList.stream()
        //                                        .filter(bountyWrapper ->
        // !bountyWrapper.getNotUsefulCurrently().equals(text))
        //                                        .collect(Collectors.toList()));

        //            List<String> buttons = new
        // ArrayList<String>(Collections.singleton(config.getString("Buttons.red")));
        //            for(int i = 0; i >=
        // config.getConfigurationSection("Buttons.red").getKeys(false).size();i++){
        //                buttons.add(1,config.getLocation("Buttons.red."+i+".location"));
        //            }
      }
    }
  }
}
