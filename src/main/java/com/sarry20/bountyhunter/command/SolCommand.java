package com.sarry20.bountyhunter.command;

import com.sarry20.bountyhunter.bountyHunter;
import com.sarry20.bountyhunter.wrapper.BountyWrapper;
import com.sarry20.bountyhunter.wrapper.PlayerWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class SolCommand implements CommandExecutor {
    private bountyHunter plugin;

    public SolCommand(bountyHunter plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            List<BountyWrapper> bountyWrapperList =
                    (List<BountyWrapper>)
                            plugin.getConfigHelper().getYamlConfiguration().getList("bounties");
            for (int i = 0; i <= bountyWrapperList.size() - 1; i++) {
                double coordsX = bountyWrapperList.get(i).getLocation().getX() - player.getLocation().getX();
                double coordsY = bountyWrapperList.get(i).getLocation().getY() - player.getLocation().getY();
                double coordsZ = bountyWrapperList.get(i).getLocation().getZ() - player.getLocation().getZ();
                boolean X = coordsX <= 5 && coordsX >= -5;
                boolean Y = coordsY <= 5 && coordsX >= -5;
                boolean Z = coordsZ <=5 && coordsZ >= -5;

                if (X || Y || Z) {
                    if (bountyWrapperList.get(i).getType().equals("sol")) {

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
                            if (!plugin.chechCooldown(foundWrapper)) {
                                player.sendMessage(
                                        plugin
                                                .getConfigHelper()
                                                .getStringMessage(
                                                        "&cYou have in cooldown the change of team!",
                                                        "message.error.cooldown"));
                                return false;
                            }
                            if (foundWrapper.getTeam().equals("sol")) {
                                player.sendMessage(
                                        plugin
                                                .getConfigHelper()
                                                .getStringMessage(
                                                        "&cYou already in this group", "message.error.sameteam"));
                                return false;
                            }
                            plugin.deleteFromPlayer(player.getUniqueId().toString());
                        }
                        PlayerWrapper pWrapper =
                                new PlayerWrapper(
                                        player.getName(),
                                        player.getUniqueId().toString(),
                                        System.currentTimeMillis(),
                                        "sol");
                        plugin.saveInPlayers(pWrapper);

                   } // else if (bountyWrapperList.get(i).getType().equals("green")) {
//                        List<PlayerWrapper> playerWrapperList =
//                                (List<PlayerWrapper>)
//                                        plugin.getPlayerHelper().getYamlConfiguration().getList("players");
//                        String team;
//
//                        Optional<PlayerWrapper> maybePlayer =
//                                playerWrapperList.stream()
//                                        .filter(
//                                                playerWrapper ->
//                                                        playerWrapper.getUUID().equals(player.getUniqueId().toString()))
//                                        .findAny();
//                        PlayerWrapper foundWrapper = null;
//                        if (maybePlayer.isPresent()) {
//                            foundWrapper = maybePlayer.get();
//                        }
//
//                        boolean isInList =
//                                playerWrapperList.stream()
//                                        .anyMatch(
//                                                playerWrapper ->
//                                                        playerWrapper.getUUID().equals(player.getUniqueId().toString()));
//                        if (isInList) {
//
//                            if (plugin.chechCooldown(foundWrapper)) {
//                            } else {
//                                player.sendMessage(
//                                        plugin
//                                                .getConfigHelper()
//                                                .getStringMessage(
//                                                        "&cYou have in cooldown the change of team!",
//                                                        "message.error.cooldown"));
//                                return false;
//                            }
//                            if (foundWrapper.getTeam().equals("green")) {
//                                player.sendMessage(
//                                        plugin
//                                                .getConfigHelper()
//                                                .getStringMessage(
//                                                        "&cYou already in this group", "message.error.sameteam"));
//                                return false;
//                            }
//                            plugin.deleteFromPlayer(player.getUniqueId().toString());
//                        }
//                        PlayerWrapper pWrapper =
//                                new PlayerWrapper(
//                                        player.getName(),
//                                        player.getUniqueId().toString(),
//                                        System.currentTimeMillis(),
//                                        "green");
//                        plugin.saveInPlayers(pWrapper);
//                    }

            }
            }

        }else{

        }
        return false;

    }
}
