# Proiect GlobalWaves  - Etapa 2

<div align="center"><img src="https://tenor.com/view/listening-to-music-spongebob-gif-8009182.gif" width="300px"></div>

#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa1](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa1)


## Skel Structure

* src/
  * checker/ - checker files
  * fileio/ - contains classes used to read data from the json files
  * main/
      * Main - the Main class runs the checker on your implementation. Add the entry point to your implementation in it. Run Main to test your implementation from the IDE or from command line.
      * Test - run the main method from Test class with the name of the input file from the command line and the result will be written
        to the out.txt file. Thus, you can compare this result with ref.
  * app
      * audio 
          * Collections
              * Album - class that creates an album with all of its parameters 
              * AlbumOutput - class that helps with the command showAlbums 
              * Podcast - class that creates a podcast with all of its parameters
              * PodcastOutput - class that helps with the command showPodcasts
      * user 
          * userFiles - package for all the files for an artist or a host
              * Announcement - class that creates an announcement
              * Event - class that creates an event
              * Merch - class that creates merch
          * Artist - class that creates an artist
          * Host - class that creates a host
          * User
              * switchConnectionStatus - function that changes a normal user's status from online to offline and vice-versa
              * checkInteraction - function that checks if there is any interaction between the current user and the user which will be deleted
              * checkLoadedAlbum - function that checks if the album can be deleted
              * checkLoadedPodcast - function that checks if the podcast can be deleted
              * changePage - function that changes the page the user is on 
      * Admin 
          * getTop5Artists - function that gets the top 5 artists
          * getTop5Albums - function that gets the top 5 albums  
          * getOnlineUsers - function that gets all the online users
          * showAlbums - function that gets all albums
          * showPodcasts - function that gets all podcasts
          * printCurrentPage - function that prints the current page of a given user
      * CommandRunner - class that deals with all the commands 
          * New commands : switchConnectionStatus, getOnlineUsers, addUser, addAlbum, showAlbums, printCurrentPage, addEvent,
addMerch, getAllUsers, deleteUser, addPodcast, addAnnouncement, removeAnnouncement, showPodcasts, removeAlbum, changePage,
removePodcast, removeEvent, getTop5Albums, getTop5Artists.
* input/ - contains the tests and library in JSON format
* ref/ - contains all reference output for the tests in JSON format

<div align="center"><img src="https://media1.tenor.com/m/fUzF2K2pbGkAAAAd/smiling-dog-meme-smiling-dog.gif" width="500px"></div>
