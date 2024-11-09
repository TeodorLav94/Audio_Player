package app.user.userFiles;

import lombok.Getter;

@Getter
public final class Event {
    private final String name;
    private final String description;
    private final String date;
    public Event(final String name, final String description, final String date) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
