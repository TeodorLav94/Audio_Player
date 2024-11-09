package app.user;

import app.Admin;
import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 */
public class User {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Setter
    @Getter
    private boolean connectionStatus;
    @Getter
    private String selectedSearch;
    @Setter
    @Getter
    private String type;
    @Getter
    private ArrayList<String> podcastsVisited = new ArrayList<>();

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        connectionStatus = true;
        type = "user";
        selectedSearch = "home page";
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }
        for (Artist artist : Admin.getArtists()) {
            if (selected.getName().equals(artist.getName())) {
                selectedSearch = selected.getName();
                return "Successfully selected %s's page.".formatted(selected.getName());
            }
        }
        for (Host host : Admin.getHosts()) {
            if (selected.getName().equals(host.getName())) {
                selectedSearch = selected.getName();
                return "Successfully selected %s's page.".formatted(selected.getName());
            }
        }
        return "Successfully selected %s.".formatted(selected.getName());
    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song")
            && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }
        if (searchBar.getLastSearchType().equals("podcast")) {
            podcastsVisited.add(searchBar.getLastSelected().getName());
        }
        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
                && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * Switches connection status
     */
    public String switchConnectionStatus() {
        if (!type.equals("user")) {
            return username + " is not a normal user.";
        } else {
            if (connectionStatus) {
                connectionStatus = false;
            } else {
                connectionStatus = true;
            }
            return username + " has changed status successfully.";
        }
    }

    /**
     * Checks if there is interaction between user and deluser
     */
    public boolean checkInteraction(final User deluser) {
        for (User user : Admin.getAllUsers()) {
            for (Podcast podcast : Admin.getPodcasts()) {
                if (podcast.getOwner().equals(deluser.getUsername())) {
                    for (String visited : user.getPodcastsVisited()) {
                        if (visited.equals(podcast.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        for (Playlist playlist : playlists) {
            for (Song songplaylist : playlist.getSongs()) {
                if (songplaylist.getArtist().equals(deluser.getUsername())) {
                    return true;
                }
            }
        }
        if (selectedSearch.equals(deluser.getUsername())) {
            return true;
        }
        if (player.getType() != null && player.getCurrentAudioFile() != null) {
            if (player.getType().equals("podcast")) {
                for (Podcast podcast : Admin.getPodcasts()) {
                    if (podcast.getOwner().equals(deluser.getUsername())) {
                        for (String visited : podcastsVisited) {
                            if (visited.equals(podcast.getName())) {
                                return true;
                            }
                        }
                        for (Episode episode : podcast.getEpisodes()) {
                            if (player.getCurrentAudioFile().getName().equals(episode.getName())) {
                                return true;
                            }
                        }
                    }
                }
            }
            if (player.getType().equals("playlist")) {
                for (Playlist playlist : deluser.getPlaylists()) {
                    for (Song song : playlist.getSongs()) {
                        if (player.getCurrentAudioFile().getName().equals(song.getName())) {
                            return true;
                        }
                    }
                }
            }
            if (player.getType().equals("album")) {
                for (Album album : Admin.getAlbums()) {
                    if (album.getOwner().equals(deluser.getUsername())) {
                        for (Song song : album.getSongs()) {
                            if (player.getCurrentAudioFile().getName().equals(song.getName())) {
                                return true;
                            }
                        }
                    }
                }
            }
            if (player.getType().equals("song")) {
                for (Song song : Admin.getSongs()) {
                    if (player.getCurrentAudioFile().getName().equals(song.getName())
                            && song.getArtist().equals(deluser.getUsername())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the album can be deleted
     */
    public boolean checkLoadedAlbum(final String name) {
        if (player.getType() != null && player.getCurrentAudioFile() != null) {
            if (player.getType().equals("playlist")) {
                for (Playlist playlist : playlists) {
                    for (Song song : playlist.getSongs()) {
                        if (player.getCurrentAudioFile().getName().equals(song.getName())) {
                            return true;
                        }
                    }
                }
            }
            if (player.getType().equals("song") || player.getType().equals("album")) {
                for (Album album : Admin.getAlbums()) {
                    if (album.getName().equals(name)) {
                        for (Song song : album.getSongs()) {
                            if (player.getCurrentAudioFile().getName().equals(song.getName())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if a podcast can be deleted
     */
    public boolean checkLoadedPodcast(final String name) {
        for (User user : Admin.getAllUsers()) {
            for (Podcast podcast : Admin.getPodcasts()) {
                if (podcast.getName().equals(name)) {
                    for (String visited : user.getPodcastsVisited()) {
                        if (visited.equals(podcast.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        if (player.getType() != null && player.getCurrentAudioFile() != null) {
            if (player.getType().equals("podcast")) {
                for (Podcast podcast : Admin.getPodcasts()) {
                    if (podcast.getOwner().equals(name)) {
                        for (Episode episode : podcast.getEpisodes()) {
                            if (player.getCurrentAudioFile().getName().equals(episode.getName())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Changes the page the user is on
     */
    public String changePage(final String nextpage) {
        if (nextpage.equals("Home")) {
            selectedSearch = "home page";
            return username + " accessed Home successfully.";
        }
        if (nextpage.equals("LikedContent")) {
            selectedSearch = "liked content page";
            return username + " accessed LikedContent successfully.";
        }
        return username + " is trying to access a non-existent page.";
    }
    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        player.simulatePlayer(time);
    }
}
