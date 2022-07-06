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

public class LunaCommand implements CommandExecutor {
    private bountyHunter plugin;

    public LunaCommand(bountyHunter plugin){
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
                 if (bountyWrapperList.get(i).getType().equals("luna")) {
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
                                return false;
                            }
                            if (foundWrapper.getTeam().equals("luna")) {
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
                                        "luna");
                        plugin.saveInPlayers(pWrapper);
                    }

                }
            }

        }else{

        }
        return false;

    }
}

