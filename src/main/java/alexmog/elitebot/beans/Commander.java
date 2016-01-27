package alexmog.elitebot.beans;

public class Commander {
    public int id, credits, debt, currentShipId;
    public String name;
    public boolean alive, docked;
    public Rank rank;
    
    public class Rank {
        public int combat, trade, explore, crime, service, empire, federation, power, cqc;
    }
}
