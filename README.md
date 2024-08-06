Music Playlist Web Service

The Playlist Service offers a range of functionalities to manage user playlists. It supports both SOAP and REST protocols, with different functionalities implemented using each protocol.

SOAP Services

Login Request: Send a request to log in.

Registration Request: Send a request to register a new user.

Deregistration Request: Send a request to deregister a user.

Change Password: Update the password for a user.

User Info: Display information about the logged-in user.

Playlist Duration: Retrieve the full duration of a playlist by name.

Send Playlist: Send a playlist to another user.

Playlist Shuffle: Shuffle the songs in a playlist.

List Songs by Duration: List songs in ascending order by duration from a specified playlist.


REST Services

List Playlists: GET /playlists

Create Playlist: POST /playlists?playlistName={playlistName}&userName={userName}&songTitle={songTitle}

Add Track to System: POST /playlists/songs/{songTitle}?songTitle={songTitle}&songArtist={songArtist}&songGenre={songGenre}&songDuration={songDuration}

Add Existing Song to Playlist: POST /playlists/{playlistName}/songs/{songTitle}

Add New Song to Playlist: POST /playlists/{playlistName}/newsongs/{songTitle}?songArtist={songArtist}&songGenre={songGenre}&songDuration={songDuration}

List Songs from Playlist: GET /playlists/{playlistName}/songs

Browse Playlist by Name: GET /playlists/{playlistName}

Delete Playlist: DELETE /playlists/{playlistName}/user/{userName}

List Song by Title: GET /playlists/songs/{songTitle}

Delete Song from Playlist: DELETE /playlists/{playlistName}/user/{userName}/song/{songTitle}

Rename Playlist: PUT /playlists/{playlistName}/rename/{newPlaylistName}/user/{userName}

List Songs by Genre: GET /playlists/songs/genre/{genreType}

List Playlists by User: GET /playlists/user/{userName}
