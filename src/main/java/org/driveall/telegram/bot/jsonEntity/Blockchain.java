package org.driveall.telegram.bot.jsonEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Blockchain {
    @JsonProperty
    private float last;
    @JsonProperty
    private String symbol;

    public Blockchain() {
    }

    public Blockchain(float last, String symbol) {
        this.last = last;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blockchain that = (Blockchain) o;
        return Float.compare(that.last, last) == 0 &&
                Objects.equal(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(last, symbol);
    }

    @Override
    public String toString() {
        return "Bitcoin: " + last + symbol;
    }
}
