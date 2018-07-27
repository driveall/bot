package org.driveall.telegram.bot.jsonEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Blockchain {
    @JsonProperty("15m")
    private float fifteenMinutes;
    @JsonProperty
    private float last;
    @JsonProperty
    private float buy;
    @JsonProperty
    private float sell;
    @JsonProperty
    private String symbol;


    public Blockchain() {
    }

    public Blockchain(float fifteenMinutes, float last, float buy, float sell, String symbol) {
        this.fifteenMinutes = fifteenMinutes;
        this.last = last;
        this.buy = buy;
        this.sell = sell;
        this.symbol = symbol;
    }

    public float getLast() {
        return last;
    }

    public void setLast(float last) {
        this.last = last;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getFifteenMinutes() {
        return fifteenMinutes;
    }

    public void setFifteenMinutes(float fifteenMinutes) {
        this.fifteenMinutes = fifteenMinutes;
    }

    public float getBuy() {
        return buy;
    }

    public void setBuy(float buy) {
        this.buy = buy;
    }

    public float getSell() {
        return sell;
    }

    public void setSell(float sell) {
        this.sell = sell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blockchain that = (Blockchain) o;
        return Float.compare(that.fifteenMinutes, fifteenMinutes) == 0 &&
                Float.compare(that.last, last) == 0 &&
                Float.compare(that.buy, buy) == 0 &&
                Float.compare(that.sell, sell) == 0 &&
                Objects.equal(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fifteenMinutes, last, buy, sell, symbol);
    }

    @Override
    public String toString() {
        return "Bitcoin: " + last + symbol;
    }
}
