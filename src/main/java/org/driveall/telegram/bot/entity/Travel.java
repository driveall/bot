package org.driveall.telegram.bot.entity;

import com.google.common.base.Objects;

public class Travel {
    private String id;
    private String evtid;
    private String name;

    public Travel() {
    }

    public Travel(String id, String evtid, String name) {
        this.id = id;
        this.evtid = evtid;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvtid() {
        return evtid;
    }

    public void setEvtid(String evtid) {
        this.evtid = evtid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Travel travel = (Travel) o;
        return Objects.equal(id, travel.id) &&
                Objects.equal(evtid, travel.evtid) &&
                Objects.equal(name, travel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, evtid, name);
    }

    @Override
    public String toString() {
        return "Travel{" +
                "id='" + id + '\'' +
                ", evtid='" + evtid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
