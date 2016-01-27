package alexmog.elitebot.beans;

public class Ship {
    public int id;
    public String name;
    public boolean alive;
    public Starport station;
    public System starsystem;
    public ShipSlot[] modules;
    
    public class ShipSlot {
        public String name;
        public ShipModule module;
    }
    
    public class ShipModule {
        public String id, name;
        public int value, unloaned, health, priority;
        public boolean on, free;
    }
    
    public class Ammo {
        public int clip, hopper;
    }
}
