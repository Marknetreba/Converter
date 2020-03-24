package models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.TreeMap;

public class Currency {

    @JsonProperty("base")
    String base;
    @JsonProperty("date")
    String date;
    @JsonProperty("rates")
    TreeMap<String, Double> rates;
    @JsonProperty("time_last_updated")
    int time_last_updated;

    public Currency() {
    }

    public Currency(String base, String date, TreeMap<String, Double> rates, int time_last_updated) {
        this.base = base;
        this.date = date;
        this.rates = rates;
        this.time_last_updated = time_last_updated;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TreeMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(TreeMap<String, Double> rates) {
        this.rates = rates;
    }

    public int getTime_last_updated() {
        return time_last_updated;
    }

    public void setTime_last_updated(int time_last_updated) {
        this.time_last_updated = time_last_updated;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates='" + rates + '\'' +
                ", time_last_updated=" + time_last_updated +
                '}';
    }
}
