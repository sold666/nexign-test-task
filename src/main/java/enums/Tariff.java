package enums;

import org.apache.log4j.Logger;

public enum Tariff {
    UNLIMITED("06", 100, 300, 1),
    MINUTE_BY_MINUTE("03", 0, 0, 1.5),
    NORMAL("11", 0, 100, 0.5);

    private final String index;
    private final int fixedCost;
    private final int includedMinutes;
    private final double perMinuteCost;
    private static final Logger logger = Logger.getLogger(Tariff.class);

    Tariff(String index, int fixedCost, int includedMinutes, double perMinuteCost) {
        this.index = index;
        this.fixedCost = fixedCost;
        this.includedMinutes = includedMinutes;
        this.perMinuteCost = perMinuteCost;
    }

    public String getIndex() {
        return index;
    }

    public static Tariff getByIndex(String index) {
        for (Tariff tariff : Tariff.values()) {
            if (tariff.getIndex().equals(index)) {
                return tariff;
            }
        }
        logger.error("There is no tariff with such an index " + index);
        throw new IllegalArgumentException();
    }

    public int getIncludedMinutes() {
        return includedMinutes;
    }

    public double getPerMinuteCost() {
        return perMinuteCost;
    }

    public int getFixedCost() {
        return fixedCost;
    }

    public double getCost(long duration) {
        double cost;

        switch (this) {
            case UNLIMITED:
                if (duration <= includedMinutes) {
                    cost = 0;
                } else {
                    cost = fixedCost + (duration - includedMinutes) * perMinuteCost;
                }
                break;
            case MINUTE_BY_MINUTE:
                cost = duration * perMinuteCost;
                break;
            case NORMAL:
                if (duration <= includedMinutes) {
                    cost = 0;
                } else {
                    double freeMinutes = includedMinutes;
                    double billedMinutes = duration - includedMinutes;
                    if (freeMinutes > 100) {
                        billedMinutes += freeMinutes - 100;
                        freeMinutes = 100;
                    }
                    cost = billedMinutes * perMinuteCost;
                }
                break;
            default:
                cost = 0;
                break;
        }
        return cost;
    }
}
