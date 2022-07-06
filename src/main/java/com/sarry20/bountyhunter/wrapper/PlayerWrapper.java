package com.sarry20.bountyhunter.wrapper;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class PlayerWrapper implements ConfigurationSerializable {

        private String name;
        private String UUID;
        private long entried;
        private String team;

    public PlayerWrapper(Map<String, Object> map) {
        name = (String) map.get("name");
        UUID = (String) map.get("UUID");
        entried = (long) map.get("entried");
        team = (String) map.get("team");

    }

    public PlayerWrapper(String name, String UUID, long entried, String team) {
    this.name = name;
    this.UUID = UUID;
    this.entried = entried;
    this.team = team;

    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("UUID",UUID);
        map.put("entried",entried);
        map.put("team",team);
        return map;
    }

    public String getName() {return name;}

    public String getUUID() {return UUID;}

    public long getEntried() {return entried;}

    public String getTeam() {return team;}

    public void setTeam(String team) {this.team = team;}
}
