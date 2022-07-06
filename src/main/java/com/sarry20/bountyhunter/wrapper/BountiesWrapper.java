package com.sarry20.bountyhunter.wrapper;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class BountiesWrapper implements ConfigurationSerializable {

    private String name;
    private String UUID;
    private int kills;
    private String team;

    public BountiesWrapper(Map<String, Object> map) {
        name = (String) map.get("name");
        UUID = (String) map.get("UUID");
        kills = (int) map.get("kills");
        team = (String) map.get("team");

    }

    public BountiesWrapper(String name, String UUID, int kills, String team) {
        this.name = name;
        this.UUID = UUID;
        this.kills = kills;
        this.team = team;

    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("UUID",UUID);
        map.put("kills",kills);
        map.put("team",team);
        return map;
    }

    public String getName() {return name;}

    public String getUUID() {return UUID;}

    public long getKills() {return kills;}

    public String getTeam() {return team;}

    public void setTeam(String team) {this.team = team;}
}
