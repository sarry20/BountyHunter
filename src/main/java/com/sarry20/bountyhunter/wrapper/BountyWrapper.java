package com.sarry20.bountyhunter.wrapper;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class BountyWrapper implements ConfigurationSerializable {

    private final Location location;
    private final String notUsefulCurrently;
    private final int id;
    private final String type;

    public BountyWrapper(Map<String, Object> map) {

        location = (Location) map.get("location");
        notUsefulCurrently = (String) map.get("useful");
        id = (int) map.get("id");
        type = (String) map.get("type");
    }

    public BountyWrapper(Location location, String notUsefulCurrently, int id,String type) {
        this.location = location;
        this.notUsefulCurrently = notUsefulCurrently;
        this.id = id;
        this.type = type;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("location", location);
        map.put("useful", notUsefulCurrently);
        map.put("id", id);
        map.put("type", type);
        return map;
    }

    public Location getLocation() {
        return location;
    }

    public String getNotUsefulCurrently() {
        return notUsefulCurrently;
    }

    public String getType() { return type; }
}
