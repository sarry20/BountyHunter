package com.sarry20.bountyhunter;

import com.github.johnnyjayjay.spigotmaps.MapBuilder;
import com.github.johnnyjayjay.spigotmaps.RenderedMap;
import com.github.johnnyjayjay.spigotmaps.rendering.ImageRenderer;
import com.github.johnnyjayjay.spigotmaps.rendering.SimpleTextRenderer;
import com.github.johnnyjayjay.spigotmaps.util.ImageTools;
import com.sarry20.bountyhunter.command.ButtonCommand;
import com.sarry20.bountyhunter.command.LocustCommand;
import com.sarry20.bountyhunter.command.LunaCommand;
import com.sarry20.bountyhunter.command.SolCommand;
import com.sarry20.bountyhunter.listener.ButtonClickListener;
import com.sarry20.bountyhunter.task.Bounty;
import com.sarry20.bountyhunter.wrapper.BountiesWrapper;
import com.sarry20.bountyhunter.wrapper.BountyWrapper;
import com.sarry20.bountyhunter.wrapper.PlayerWrapper;
import de.crowraw.lib.data.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public final class bountyHunter extends JavaPlugin {

  private final PluginDescriptionFile pdffile = getDescription();

  private ConfigHelper configUtil;

  private ConfigHelper playerUtil;

  private ConfigHelper bountiesUtil;

  private final String version = pdffile.getVersion();

  private String Bname;



  private final String name =
      ChatColor.RED + "[" + ChatColor.RED + pdffile.getName() + ChatColor.RED + "]";

  @Override
  public void onEnable() {
    ConfigurationSerialization.registerClass(BountyWrapper.class);
    ConfigurationSerialization.registerClass(PlayerWrapper.class);
    ConfigurationSerialization.registerClass(BountiesWrapper.class);
    this.configUtil = new ConfigHelper("plugins//BountyHunter//config.yml");
    this.playerUtil = new ConfigHelper("plugins//BountyHunter//player.yml");
    this.bountiesUtil = new ConfigHelper("plugins//BountyHunter//bounties.yml");



    getLogger().info(name + ChatColor.WHITE + "the plugin is starting...");
    getLogger().info(name + ChatColor.WHITE + "Version: " + ChatColor.RED + version);

    Bounty bounty = new Bounty(this,100);
    bounty.execute();

    this.registerEvents();
    this.registerCommands();
    this.configUtil.saveConfig();
    this.playerUtil.saveConfig();
    this.bountiesUtil.saveConfig();
    prepareConfig();
    preparePlayer();
    prepareBounties();


  }
  // finds bountywrapper by the string which is provide
  public void deleteFromConfig(String text) {
    List<BountyWrapper> bountyWrapperList =
        (List<BountyWrapper>) this.configUtil.getYamlConfiguration().getList("bounties");

    this.configUtil
        .getYamlConfiguration()
        .set(
            "bounties",
            bountyWrapperList.stream()
                .filter(bountyWrapper -> !bountyWrapper.getNotUsefulCurrently().equals(text))
                .collect(Collectors.toList()));
    this.configUtil.saveConfig();
  }
  public boolean checkIfIsInConfig(Entity entity) {
    List<BountyWrapper> bountyWrapperList =
            (List<BountyWrapper>) this.configUtil.getYamlConfiguration().getList("bounties");


    List<Location> loc = (List<Location>) this.configUtil
            .getYamlConfiguration()
            .get(
                    "bounties",
                    bountyWrapperList.stream()
                            .filter(bountyWrapper -> bountyWrapper.getLocation().equals(entity.getLocation())));
    System.out.println(loc);
    System.out.println(entity.getLocation());
    if(loc.equals(entity.getLocation())){
      return true;
    }else{
      return false;
    }

  }
  public void giveBountyMap(List<String> PlayersRed){
    final int max = PlayersRed.size()-1;
    if(max != 0 && max >=0){
      Random random = new Random();
      int bountied = random.ints(0, max).findFirst().getAsInt();
      Bname = PlayersRed.get(bountied);
      Dimension mapSize = ImageTools.MINECRAFT_MAP_SIZE; // the dimensions of a Minecraft map (in pixels)
      SimpleTextRenderer messageRenderer =
          SimpleTextRenderer.builder()
              .addLines(Bname) // set the lines that will be drawn onto the map
              .startingPoint(
                  new Point(
                      mapSize.width - 85, mapSize.height / 2)) // start in the middle
              .build(); // build the instance
      ImageRenderer backgroundRenderer = ImageRenderer.createSingleColorRenderer(Color.WHITE);
      RenderedMap map = MapBuilder.create() // make a new builder
              .addRenderers(backgroundRenderer,messageRenderer) // add the renderers to this map
              .build(); // build the map
      ItemStack mapItem = map.createItemStack(); // get an ItemStack from this map to work with



      for(Player player : Bukkit.getOnlinePlayers()){
        player.getInventory().addItem(mapItem);
      }

    }
  }

  public void deleteFromPlayer(String text) {
    List<PlayerWrapper> playerWrapperList =
            (List<PlayerWrapper>) this.playerUtil.getYamlConfiguration().getList("players");

    this.playerUtil
            .getYamlConfiguration()
            .set(
                    "players",
                    playerWrapperList.stream()
                            .filter(playerWrapper -> !playerWrapper.getUUID().equals(text))
                            .collect(Collectors.toList()));
    this.playerUtil.saveConfig();
  }
  public void deleteFromBounties(String text) {
    List<BountiesWrapper> bountiesWrapperList =
            (List<BountiesWrapper>) this.playerUtil.getYamlConfiguration().getList("bountiers");
    this.playerUtil
            .getYamlConfiguration()
            .set(
                    "players",
                    bountiesWrapperList.stream()
                            .filter(playerWrapper -> !playerWrapper.getUUID().equals(text))
                            .collect(Collectors.toList()));
    this.playerUtil.saveConfig();
  }
  public void saveInConfig(BountyWrapper bountyWrapper) {
    List<BountyWrapper> bountyWrapperList =
        (List<BountyWrapper>) this.configUtil.getYamlConfiguration().getList("bounties");
    bountyWrapperList.add(bountyWrapper);
    this.configUtil.getYamlConfiguration().set("bounties", bountyWrapperList);
    saveCustomConfig();
  }
  public void saveInPlayers(PlayerWrapper playerWrapper) {
    List<PlayerWrapper> playerWrapperList =
            (List<PlayerWrapper>) this.playerUtil.getYamlConfiguration().getList("players");
    playerWrapperList.add(playerWrapper);
    this.playerUtil.getYamlConfiguration().set("players", playerWrapperList);
    saveCustomConfig();
  }
  public void saveInBounties(BountiesWrapper bountiesWrapper) {
    List<BountiesWrapper> bountiesWrapperList =
            (List<BountiesWrapper>) this.playerUtil.getYamlConfiguration().getList("bountiers");
    bountiesWrapperList.add(bountiesWrapper);
    this.playerUtil.getYamlConfiguration().set("players", bountiesWrapperList);
    saveCustomConfig();
  }
  private void prepareConfig() {
    // creates default when config is empty
    if (this.configUtil.getYamlConfiguration().getList("bounties") == null) {
      BountyWrapper bountyWrapper =
          new BountyWrapper(
              new Location(Bukkit.getWorlds().get(0), 0D, 0D, 0D),
              "test",
              0,"test"); // 0 since it is the first location
      List<BountyWrapper> bountyWrapperList = new ArrayList<>();
      bountyWrapperList.add(bountyWrapper);
      this.configUtil.getYamlConfiguration().set("bounties", bountyWrapperList);
      saveCustomConfig();

    }
  }
  private void preparePlayer() {
    // creates default when config is empty
    if (this.playerUtil.getYamlConfiguration().getList("players") == null) {
      PlayerWrapper playerWrapper =
              new PlayerWrapper("test","24sar213gs",System.currentTimeMillis(),"test");
      List<PlayerWrapper> PlayerWrapperList = new ArrayList<>();
      PlayerWrapperList.add(playerWrapper);
      this.playerUtil.getYamlConfiguration().set("players", PlayerWrapperList);
      saveCustomConfig();

    }
  }

  private void prepareBounties() {
    // creates default when config is empty
    if (this.playerUtil.getYamlConfiguration().getList("bountiers") == null) {
      BountiesWrapper bountiesWrapper =
          new BountiesWrapper("DO", "NOT", 0, "DELETE");
      List<BountiesWrapper> BountiesWrapperList = new ArrayList<>();
      BountiesWrapperList.add(bountiesWrapper);
      this.playerUtil.getYamlConfiguration().set("bountiers", BountiesWrapperList);
      saveCustomConfig();
    }
    }

  public boolean chechCooldown(PlayerWrapper playerWrapper){
    List<PlayerWrapper> playerWrapperList =
            (List<PlayerWrapper>) this.playerUtil.getYamlConfiguration().getList("players");

    boolean isAfter = new Date(System.currentTimeMillis()).after(
            Date.from(new Date(playerWrapper.getEntried()).toInstant().plus(2,ChronoUnit.HOURS)));
    return isAfter;
  }
  @Override
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage(name + ChatColor.WHITE + "Disabling plugin...");
    Bukkit.getConsoleSender()
        .sendMessage(name + ChatColor.WHITE + "Version: " + ChatColor.RED + version);
  }

  public void registerCommands() {
    this.getCommand("BGroup").setExecutor(new ButtonCommand(this));
    this.getCommand("JoinSol").setExecutor(new SolCommand(this));
    this.getCommand("JoinLuna").setExecutor(new LunaCommand(this));
    this.getCommand("JoinSL").setExecutor(new LocustCommand(this));

  }

  public void registerEvents() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new ButtonClickListener(this), this);
  }

  public void saveCustomConfig() {
    try {
      this.configUtil.getYamlConfiguration().save(this.configUtil.getFile());
      this.playerUtil.getYamlConfiguration().save(this.playerUtil.getFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public ConfigHelper getConfigHelper() {
    return configUtil;
  }
  public ConfigHelper getPlayerHelper() {
    return playerUtil;
  }
  public String getBname() {return Bname;}


  public void setBname(String bname) {Bname = bname;}

}
