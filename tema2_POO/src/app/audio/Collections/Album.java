package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Album extends AudioCollection {
    private final ArrayList<Song> songs;
    private final Integer releaseYear;
    private final String description;
    private final String name;
    private final String owner;
    public Album(final String name, final Integer releaseYear, final String description,
                 final ArrayList<Song> songs, final String owner) {
        super(name, description);
        this.name = name;
        this.songs = songs;
        this.description = description;
        this.releaseYear = releaseYear;
        this.owner = owner;
    }

    /**
     * Gets number of tracks.
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * Gets track by index.
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }
}
