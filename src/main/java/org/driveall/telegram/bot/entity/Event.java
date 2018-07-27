package org.driveall.telegram.bot.entity;

import com.google.common.base.Objects;

import java.time.LocalDateTime;

public class Event {
    private String id;
    private LocalDateTime date;
    private String description;

    public Event() {
    }

    public Event(String id, LocalDateTime date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equal(id, event.id) &&
                Objects.equal(date, event.date) &&
                Objects.equal(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, date, description);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
