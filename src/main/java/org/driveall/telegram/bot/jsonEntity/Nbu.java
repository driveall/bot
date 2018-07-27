package org.driveall.telegram.bot.jsonEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Nbu {
    @JsonProperty
    private int r030;//currency code
    @JsonProperty
    private String txt;//currency name
    @JsonProperty
    private double rate;//currency rate
    @JsonProperty
    private String cc;//currency code

    public Nbu() {
    }

    public Nbu(int r030, String txt, double rate, String cc) {
        this.r030 = r030;
        this.txt = txt;
        this.rate = rate;
        this.cc = cc;
    }

    public int getR030() {
        return r030;
    }

    public void setR030(int r030) {
        this.r030 = r030;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Override
    public String toString() {
        String out = "";
        switch (cc) {
            case "USD":
                out += "доллар: " + rate;
                break;
            case "EUR":
                out += "евро: " + rate;
                break;
            case "JPY":
                out += "йена: " + rate;
                break;
        }
        out += "грн.";
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nbu that = (Nbu) o;
        return r030 == that.r030 &&
                Double.compare(that.rate, rate) == 0 &&
                Objects.equal(txt, that.txt) &&
                Objects.equal(cc, that.cc);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(r030, txt, rate, cc);
    }
}
