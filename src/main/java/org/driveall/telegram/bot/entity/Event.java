package org.driveall.telegram.bot.entity;

import com.google.common.base.Objects;

import java.sql.Timestamp;

public class Event {
    private String id;
    private Timestamp date;
    private String description;
    private int num;

    public Event() {
    }

    public Event(String id, Timestamp date, String description, int num) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return num == event.num &&
                Objects.equal(id, event.id) &&
                Objects.equal(date, event.date) &&
                Objects.equal(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, date, description, num);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", num=" + num +
                '}';
    }
}
