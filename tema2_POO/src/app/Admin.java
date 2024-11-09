package app;

import app.audio.Collections.*;
import app.audio.Files.*;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.user.userFiles.Announcement;
import app.user.userFiles.Event;
import app.user.userFiles.Merch;
import fileio.input.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The type Admin.
 */
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Host> hosts = new ArrayList<>();
    private static ArrayList<Album> albums = new ArrayList<>();
    private static ArrayList<Event> events = new ArrayList<>();
    private static ArrayList<Merch> merch = new ArrayList<>();
    private static ArrayList<Announcement> announcements = new ArrayList<>();

    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin() {
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static List<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    public static List<Host> getHosts() {
        return new ArrayList<>(hosts);
    }

    public static List<Album> getAlbums() {
        return new ArrayList<>(albums);
    }

    public static List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public static List<Merch> getMerch() {
        return new ArrayList<>(merch);
    }

    public static List<Announcement> getAnnouncements() {
        return new ArrayList<>(announcements);
    }
    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.isConnectionStatus()) {
                user.simulateTime(elapsed);
            }
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Gets top 5 albums
     */
    public static List<String> getTop5Albums() {
        ArrayList<String> top5Albums = new ArrayList<>();
        ArrayList<Album> allAlbums = new ArrayList<>(albums);
        Collections.sort(allAlbums, Comparator.comparing(Album::getName));
        for (int i = 1; i <= 5; i++) {
            int sumaMax = -1;
            Album nextAdd = null;
            for (Album album : allAlbums) {
                int suma = 0;
                for (Song song : album.getSongs()) {
                    suma += song.getLikes();
                }
                if (suma > sumaMax) {
                    sumaMax = suma;
                    nextAdd = album;
                }
            }
            if (nextAdd != null) {
                top5Albums.add(nextAdd.getName());
                allAlbums.remove(nextAdd);
            }
        }
        return top5Albums;
    }

    /**
     * Gets top 5 artists
     */
    public static List<String> getTop5Artists() {
        ArrayList<String> top5Artists = new ArrayList<>();
        ArrayList<Artist> allArtists = new ArrayList<>(artists);
        Collections.sort(allArtists, Comparator.comparing(Artist::getName));
        for (int i = 1; i <= 5; i++) {
            int sumaMax = -1;
            Artist nextAdd = null;
            for (Artist artist : allArtists) {
                int suma = 0;
                for (Album album : albums) {
                    if (album.getOwner().equals(artist.getName())) {
                        for (Song song : album.getSongs()) {
                            suma += song.getLikes();
                        }
                    }
                }
                if (suma > sumaMax) {
                    sumaMax = suma;
                    nextAdd = artist;
                }
            }
            if (nextAdd != null) {
                top5Artists.add(nextAdd.getName());
                allArtists.remove(nextAdd);
            }
        }
        return top5Artists;
    }

    /**
     * Gets online users
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineusers = new ArrayList<>();
        for (User user : users) {
            if (user.isConnectionStatus()) {
                onlineusers.add(user.getUsername());
            }
        }
        return onlineusers;
    }

    /**
     * Adds a user
     */
    public static void addUser(final User user) {
        users.add(user);
    }

    /**
     * Adds a song
     */
    public static void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Adds an artist
     */
    public static void addArtist(final Artist artist) {
        artists.add(artist);
    }

    /**
     * Adds a host
     */
    public static void addHost(final Host host) {
        hosts.add(host);
    }

    /**
     * Adds an album
     */
    public static void addAlbum(final Album album) {
        albums.add(album);
    }

    /**
     * Adds an event
     */
    public static void addEvent(final Event event) {
        events.add(event);
    }

    /**
     * Adds merch
     */
    public static void addMerch(final Merch merch1) {
        merch.add(merch1);
    }

    /**
     * Adds a podcast
     */
    public static void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Adds an announcement
     */
    public static void addAnnouncement(final Announcement announcement) {
        announcements.add(announcement);
    }
    /**
     * Shows all albums
     */
    public static ArrayList<AlbumOutput> showAlbums(final String username) {
        ArrayList<AlbumOutput> albumOutputs = new ArrayList<>();
        for (Album album : albums) {
            if (album.getOwner().equals(username)) {
                albumOutputs.add(new AlbumOutput(album));
            }
        }
        return albumOutputs;
    }

    /**
     * Shows all podcasts
     */
    public static ArrayList<PodcastOutput> showPodcasts(final String username) {
        ArrayList<PodcastOutput> podcastOutputs = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.getOwner().equals(username)) {
                podcastOutputs.add(new PodcastOutput(podcast));
            }
        }
        return podcastOutputs;
    }

    /**
     * Prints the current page of a user
     */
    public static String printCurrentPage(final User user) {
        if (!user.isConnectionStatus()) {
            return user.getUsername() + " is offline.";
        }
        if (user.getSelectedSearch().equals("home page")) {
            List<Song> sortedSongs = new ArrayList<>(user.getLikedSongs());
            sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
            List<String> topSongs = new ArrayList<>();
            int count = 0;
            for (Song song : sortedSongs) {
                if (count >= LIMIT) {
                    break;
                }
                topSongs.add(song.getName());
                count++;
            }
            ArrayList<String> top5Playlists = new ArrayList<>();
            ArrayList<Playlist> allPlaylists = new ArrayList<>(user.getFollowedPlaylists());
            for (int i = 1; i <= 5; i++) {
                int sumaMax = -1;
                Playlist nextAdd = null;
                for (Playlist playlist : allPlaylists) {
                    int suma = 0;
                    for (Song song : playlist.getSongs()) {
                        suma += song.getLikes();
                    }
                    if (suma > sumaMax) {
                        sumaMax = suma;
                        nextAdd = playlist;
                    }
                }
                if (nextAdd != null) {
                    top5Playlists.add(nextAdd.getName());
                    allPlaylists.remove(nextAdd);
                }
            }
            return "Liked songs:\n\t" + topSongs + "\n\nFollowed playlists:\n\t" + top5Playlists;
        }
        boolean modif = false;
        if (user.getSelectedSearch().equals("liked content page")) {
            StringBuilder newstring = new StringBuilder();
            newstring.append("Liked songs:\n\t[");
            for (Song song : user.getLikedSongs()) {
                newstring.append(song.getName() + " - " + song.getArtist() + ", ");
                modif = true;
            }
            String modifiedString;
            if (modif) {
                modifiedString = newstring.substring(0, newstring.length() - 2);
                newstring = new StringBuilder(modifiedString);
                modif = false;
            }
            newstring.append("]\n\nFollowed playlists:\n\t[");
            for (Playlist playlist : user.getFollowedPlaylists()) {
                newstring.append(playlist.getName() + " - " + playlist.getOwner() + ", ");
                modif = true;
            }
            if (modif) {
                modifiedString = newstring.substring(0, newstring.length() - 2);
                newstring = new StringBuilder(modifiedString);
            }
            newstring.append("]");
            return newstring.toString();
        }
        String type = new String();
        User user2 = null;
        for (User user1 : users) {
            user2 = user1;
            if (user1.getType().equals("artist")
                    && user1.getUsername().equals(user.getSelectedSearch())) {
                type = "artist";
                break;
            }
            if (user1.getType().equals("host")
                    && user1.getUsername().equals(user.getSelectedSearch())) {
                type = "host";
                break;
            }
        }
        if (type.equals("artist")) {
            StringBuilder newstring = new StringBuilder();
            ArrayList<String> albumss = new ArrayList<>();
            for (Album album : albums) {
                if (album.getOwner().equals(user2.getUsername())) {
                    albumss.add(album.getName());
                }
            }
            newstring.append("Albums:\n\t" + albumss + "\n\nMerch:\n\t[");
            for (Merch merch : merch) {
                newstring.append(merch.getName()
                        + " - " + merch.getPrice() + ":\n\t" + merch.getDescription() + ", ");
                modif = true;
            }
            String modifiedString;
            if (modif) {
                modifiedString = newstring.substring(0, newstring.length() - 2);
                newstring = new StringBuilder(modifiedString);
                modif = false;
            }
            newstring.append("]\n\nEvents:\n\t[");
            for (Event event : events) {
                newstring.append(event.getName()
                        + " - " + event.getDate() + ":\n\t" + event.getDescription() + ", ");
                modif = true;
            }
            if (modif) {
                modifiedString = newstring.substring(0, newstring.length() - 2);
                newstring = new StringBuilder(modifiedString);
            }
            newstring.append("]");
            return newstring.toString();
        }
        if (type.equals("host")) {
            StringBuilder newstring = new StringBuilder();
            newstring.append("Podcasts:\n\t[");
            boolean modif1 = false;
            String modifiedString;
            for (Podcast podcast : podcasts) {
                if (podcast.getOwner().equals(user2.getUsername())) {
                    newstring.append(podcast.getName() + ":\n\t[");
                    for (Episode episode : podcast.getEpisodes()) {
                        newstring.append(episode.getName()
                                + " - " + episode.getDescription() + ", ");
                        modif = true;
                    }
                    if (modif) {
                        modifiedString = newstring.substring(0, newstring.length() - 2);
                        newstring = new StringBuilder(modifiedString);
                        modif = false;
                    }
                    newstring.append("]\n, ");
                }
            }
            if (modif1) {
                modifiedString = newstring.substring(0, newstring.length() - 2);
                newstring = new StringBuilder(modifiedString);
            }
            modifiedString = newstring.substring(0, newstring.length() - 2);
            newstring = new StringBuilder(modifiedString);
            newstring.append("]\n\nAnnouncements:\n\t[");
            for (Announcement announcement : announcements) {
                newstring.append(announcement.getName()
                        + ":\n\t" + announcement.getDescription() + "\n");
            }
            newstring.append("]");
            return newstring.toString();
        }
        return "a";
    }

    /**
     * Gets all users
     */
    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Removes a user
     */
    public static void removeUser(final User user) {
        users.remove(user);
    }

    /**
     * Removes a song
     */
    public static void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * Removes an announcement
     */
    public static void removeAnnouncement(final Announcement announcement) {
        announcements.remove(announcement); }

    /**
     * Removes an album
     */
    public static void removeAlbum(final Album album) {
        albums.remove(album); }

    /**
     * Removes an event
     */
    public static void removeEvent(final Event event) {
        events.remove(event); }

    /**
     * Removes a podcast
     */
    public static void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast); }
    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        artists = new ArrayList<>();
        albums = new ArrayList<>();
        events = new ArrayList<>();
        merch = new ArrayList<>();
        hosts = new ArrayList<>();
        announcements = new ArrayList<>();
        timestamp = 0;
    }
}
