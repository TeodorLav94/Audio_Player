package app;

import app.audio.Collections.*;
import app.audio.Files.*;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.user.userFiles.Announcement;
import app.user.userFiles.Event;
import app.user.userFiles.Merch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import javassist.compiler.ast.Visitor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();
        ArrayList<String> results = user.search(filters, type);
        String message = "Search returned " + results.size() + " results";
        if (!user.isConnectionStatus()) {
            results = new ArrayList<>();
            message = commandInput.getUsername() + " is offline.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isConnectionStatus()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.like();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String message = user.createPlaylist(commandInput.getPlaylistName(),
                                             commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Switches the online status of a user.
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = user.switchConnectionStatus();
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Gets the online users.
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineusers = Admin.getOnlineUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineusers));

        return objectNode;
    }

    /**
     * Adds a new user.
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String name = commandInput.getUsername();
        Integer age = commandInput.getAge();
        String city = commandInput.getCity();
        String message;
        String type = commandInput.getType();
        User user = new User(name, age, city);
        user.setType(type);
        if (Admin.getUser(name) == null) {
            Admin.addUser(user);
            message = "The username " + name + " has been added successfully.";
            if (type.equals("artist")) {
                String newage = age.toString();
                Artist artist = new Artist(name, newage, city);
                Admin.addArtist(artist);
            }
            if (type.equals("host")) {
                String newage = age.toString();
                Host host = new Host(name, newage, city);
                Admin.addHost(host);
            }
            if (!type.equals("user")) {
                user.setConnectionStatus(false);
            }
        } else {
            message = "The username " + name + " is already taken.";
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new album.
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        Integer releaseyear = commandInput.getReleaseYear();
        String description = commandInput.getDescription();
        ArrayList<SongInput> songs = commandInput.getSongs();
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("artist")) {
                message = commandInput.getUsername()
                        + " is not an artist.";
            } else {
                boolean contains = false;
                for (Album album1 : Admin.getAlbums()) {
                    if (album1.getName().equals(name)
                            && album1.getOwner().equals(user.getUsername())) {
                        contains = true;
                    }
                }
                if (contains) {
                    message = commandInput.getUsername()
                            + " has another album with the same name.";
                } else {
                    boolean repeat = false;
                    for (int i = 0; i < songs.size() - 1; i++) {
                        for (int j = i + 1; j < songs.size(); j++) {
                            if (songs.get(i).getName().equals(songs.get(j).getName())) {
                                repeat = true;
                            }
                        }
                    }
                    if (repeat) {
                        message = commandInput.getUsername()
                                + " has the same song at least twice in this album.";
                    } else {
                        ArrayList<Song> newsongs = new ArrayList<>();
                        for (SongInput song : songs) {
                            Song newsong = new Song(song.getName(), song.getDuration(),
                                    song.getAlbum(), song.getTags(), song.getLyrics(),
                                    song.getGenre(), song.getReleaseYear(), song.getArtist());
                            Admin.addSong(newsong);
                            newsongs.add(newsong);
                        }
                        Album album = new Album(name, releaseyear, description,
                                newsongs, commandInput.getUsername());
                        Admin.addAlbum(album);
                        message = commandInput.getUsername()
                                + " has added new album successfully.";
                    }
                }
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shows all albums.
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        List<AlbumOutput> albums = Admin.showAlbums(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));
        return objectNode;
    }

    /**
     * Prints the current page of a user.
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        assert user != null;
        String message = Admin.printCurrentPage(user);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Adds a new event.
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String date = commandInput.getDate();
        String name = commandInput.getName();
        String description = commandInput.getDescription();
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("artist")) {
                message = commandInput.getUsername() + " is not an artist.";
            } else {
                boolean contains = false;
                for (Event event : Admin.getEvents()) {
                    if (event.getName().equals(name)) {
                        contains = true;
                    }
                }
                if (contains) {
                    message = commandInput.getUsername()
                            + " has another event with the same name.";
                } else {
                    if (!isValidDate(date)) {
                        message = "Event for " + commandInput.getUsername()
                                + " does not have a valid date.";
                    } else {
                        Event newevent = new Event(name, description, date);
                        Admin.addEvent(newevent);
                        message = commandInput.getUsername()
                                + " has added new event successfully.";
                    }
                }
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Checks if a date of the year is valid.
     */
    private static boolean isValidDate(final String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);

            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year = date.getYear();

            if (month == 2 && day > 28) {
                return false;
            }
            if (day > 31 || month > 12 || year < 1900 || year > 2023) {
                return false;
            }
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Adds new merch.
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        String description = commandInput.getDescription();
        Integer price = commandInput.getPrice();
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("artist")) {
                message = commandInput.getUsername() + " is not an artist.";
            } else {
                boolean contains = false;
                for (Merch merch : Admin.getMerch()) {
                    if (merch.getName().equals(name)) {
                        contains = true;
                    }
                }
                if (contains) {
                    message = commandInput.getUsername()
                            + " has merchandise with the same name.";
                } else {
                    if (price < 0) {
                        message = "Price for merchandise can not be negative.";
                    } else {
                        Merch newmerch = new Merch(name, description, price);
                        Admin.addMerch(newmerch);
                        message = commandInput.getUsername()
                                + " has added new merchandise successfully.";
                    }
                }
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Gets all the users.
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<User> users = Admin.getAllUsers();
        List<String> names = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals("user")) {
                names.add(user.getUsername());
            }
        }
        for (User user : users) {
            if (user.getType().equals("artist")) {
                names.add(user.getUsername());
            }
        }
        for (User user : users) {
            if (user.getType().equals("host")) {
                names.add(user.getUsername());
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(names));
        return objectNode;
    }

    /**
     * Deletes a user if the conditions are met.
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        User delUser = Admin.getUser(commandInput.getUsername());
        String message;
        boolean isInteraction = false;
        for (User user : Admin.getAllUsers()) {
            isInteraction = user.checkInteraction(delUser);
            if (isInteraction) {
                break;
            }
        }
        if (isInteraction) {
            message = delUser.getUsername() + " can't be deleted.";
        } else {
            for (Playlist playlist : delUser.getPlaylists()) {
                for (User user : Admin.getAllUsers()) {
                    user.getFollowedPlaylists().remove(playlist);
                }
            }
            for (Song song : delUser.getLikedSongs()) {
                song.dislike();
            }
            for (Playlist playlist : delUser.getFollowedPlaylists()) {
                playlist.decreaseFollowers();
            }
            if (delUser.getType().equals("artist")) {
                for (Song song : Admin.getSongs()) {
                    if (song.getArtist().equals(delUser.getUsername())) {
                        for (User user : Admin.getAllUsers()) {
                            user.getLikedSongs().remove(song);
                            for (Playlist playlist : user.getPlaylists()) {
                                playlist.getSongs().remove(song);
                            }
                        }
                        Admin.removeSong(song);
                    }
                }
                for (Album album : Admin.getAlbums()) {
                    if (album.getOwner().equals(delUser.getUsername())) {
                        Admin.removeAlbum(album);
                    }
                }
            }
            if (delUser.getType().equals("host")) {
                for (Podcast podcast : Admin.getPodcasts()) {
                    if (podcast.getOwner().equals(delUser.getUsername())) {
                        Admin.removePodcast(podcast);
                    }
                }
            }
            Admin.removeUser(delUser);
            message = delUser.getUsername() + " was successfully deleted.";
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Adds a new podcast.
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        ArrayList<EpisodeInput> episodes = commandInput.getEpisodes();
        if (user == null) {
            message = "The username " + commandInput.getUsername()
                    + " doesn't exist.";
        } else {
            if (!user.getType().equals("host")) {
                message = commandInput.getUsername() + " is not a host.";
            } else {
                boolean contains = false;
                for (Podcast podcast : Admin.getPodcasts()) {
                    if (podcast.getName().equals(name)
                            && podcast.getOwner().equals(user.getUsername())) {
                        contains = true;
                    }
                }
                if (contains) {
                    message = commandInput.getUsername()
                            + " has another podcast with the same name.";
                } else {
                    boolean repeat = false;
                    for (int i = 0; i < episodes.size() - 1; i++) {
                        for (int j = i + 1; j < episodes.size(); j++) {
                            if (episodes.get(i).getName().equals(episodes.get(j).getName())) {
                                repeat = true;
                            }
                        }
                    }
                    if (repeat) {
                        message = commandInput.getUsername()
                                + " has the same song at least twice in this album.";
                    } else {
                        ArrayList<Episode> newepisodes = new ArrayList<>();
                        for (EpisodeInput episode : episodes) {
                            Episode newepisode = new Episode(episode.getName(),
                                    episode.getDuration(), episode.getDescription());
                            newepisodes.add(newepisode);
                        }
                        Podcast podcast = new Podcast(name, user.getUsername(), newepisodes);
                        Admin.addPodcast(podcast);
                        message = commandInput.getUsername()
                                + " has added new podcast successfully.";
                    }
                }
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new announcement.
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        String description = commandInput.getDescription();
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("host")) {
                message = commandInput.getUsername() + " is not a host.";
            } else {
                boolean contains = false;
                for (Announcement announcement : Admin.getAnnouncements()) {
                    if (announcement.getName().equals(name)) {
                        contains = true;
                    }
                }
                if (contains) {
                    message = commandInput.getUsername()
                            + " has already added an announcement with this name.";
                } else {
                    Announcement announcement = new Announcement(name, description);
                    Admin.addAnnouncement(announcement);
                    message = commandInput.getUsername()
                            + " has successfully added new announcement.";
                }
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Deletes an announcement.
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("host")) {
                message = commandInput.getUsername() + " is not a host.";
            } else {
                boolean contains = false;
                for (Announcement announcement : Admin.getAnnouncements()) {
                    if (announcement.getName().equals(name)) {
                        contains = true;
                        Admin.removeAnnouncement(announcement);
                        break;
                    }
                }
                if (!contains) {
                    message = commandInput.getUsername()
                            + " has no announcement with the given name.";
                } else {
                    message = commandInput.getUsername()
                            + " has successfully deleted the announcement.";
                }
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Shows all the podcasts.
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        List<PodcastOutput> podcasts = Admin.showPodcasts(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(podcasts));
        return objectNode;
    }

    /**
     * Removes an album if the conditions are met.
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        boolean isInteraction = false;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("artist")) {
                message = commandInput.getUsername() + " is not an artist.";
            } else {
                boolean contains = false;
                for (Album album : Admin.getAlbums()) {
                    if (album.getName().equals(name)
                            && album.getOwner().equals(user.getUsername())) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    message = commandInput.getUsername()
                            + " doesn't have an album with the given name.";
                } else {
                    for (User user1 : Admin.getAllUsers()) {
                        isInteraction = user1.checkLoadedAlbum(name);
                        if (isInteraction) {
                            break;
                        }
                    }
                    if (isInteraction) {
                        message = commandInput.getUsername() + " can't delete this album.";
                    } else {
                        message = commandInput.getUsername() + " deleted the album successfully.";
                        for (Album album : Admin.getAlbums()) {
                            if (album.getName().equals(name)
                                    && album.getOwner().equals(user.getUsername())) {
                                for (Song song : album.getSongs()) {
                                    for (User user1 : Admin.getAllUsers()) {
                                        user1.getLikedSongs().remove(song);
                                        for (Playlist playlist : user1.getPlaylists()) {
                                            playlist.getSongs().remove(song);
                                        }
                                    }
                                    Admin.removeSong(song);
                                }
                                Admin.removeAlbum(album);
                            }
                        }
                    }
                }
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Changes the page of a user.
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String nextpage = commandInput.getNextPage();
        String message = user.changePage(nextpage);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Deletes a podcast.
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        boolean isInteraction = false;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("host")) {
                message = commandInput.getUsername() + " is not a host.";
            } else {
                boolean contains = false;
                for (Podcast podcast : Admin.getPodcasts()) {
                    if (podcast.getName().equals(name)
                            && podcast.getOwner().equals(user.getUsername())) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    message = commandInput.getUsername()
                            + " doesn't have a podcast with the given name.";
                } else {
                    for (User user1 : Admin.getAllUsers()) {
                        isInteraction = user1.checkLoadedPodcast(name);
                        if (isInteraction) {
                            break;
                        }
                    }
                    if (isInteraction) {
                        message = commandInput.getUsername() + " can't delete this podcast.";
                    } else {
                        message = commandInput.getUsername()
                                + " deleted the podcast successfully.";
                        for (Podcast podcast : Admin.getPodcasts()) {
                            if (podcast.getName().equals(name)) {
                                Admin.removePodcast(podcast);
                            }
                        }
                    }
                }
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Deletes an event.
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        String name = commandInput.getName();
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (!user.getType().equals("artist")) {
                message = commandInput.getUsername() + " is not a host.";
            } else {
                boolean contains = false;
                for (Event event : Admin.getEvents()) {
                    if (event.getName().equals(name)) {
                        contains = true;
                        Admin.removeEvent(event);
                        break;
                    }
                }
                if (!contains) {
                    message = commandInput.getUsername()
                            + " doesn't have an event with the given name.";
                } else {
                    message = commandInput.getUsername()
                            + " deleted the event successfully.";
                }
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Gets the top 5 albums
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets the top 5 artists
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }
}
