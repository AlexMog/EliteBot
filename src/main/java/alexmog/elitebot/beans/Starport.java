package alexmog.elitebot.beans;

public class Starport {
    public String id, name, faction;
    public Commodity[] commodities;
//    public Module[] modules;
    
    public class Commodity {
        public String id, name, cost_mean, homebuy, homesell, consumebuy,
            rare_min_stock, rare_max_stock, market_id, parent_id, categoryname, volumescale;
        public int cost_min, cost_max,
            buyPrice, sellPrice, meanPrice, demandBracket, stockBracket,
            creationQty, consumptionQty, targetStock;
        public float baseCreationQty, baseConsumptionQty, stock, demand, capacity;
    }
}
