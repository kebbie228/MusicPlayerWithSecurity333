
INSERT INTO artist(nick_name) VALUES('QuEen');
INSERT INTO artist(nick_name) VALUES('Deftones');
INSERT INTO artist(nick_name) VALUES('Joji');

INSERT INTO album(album_name,artist_id,photo_file_path) VALUES('Queen Album 1',1,'photo/Queen-TheShowMustGoOn.jpg');
INSERT INTO album(album_name,artist_id,photo_file_path) VALUES('Deftones Album 1',2,'photo/deftones-doYouBelieve.jpg');
INSERT INTO album(album_name,artist_id,photo_file_path) VALUES('Deftones Album 2',2,'photo/deftones-myOwnSummer.jpg');

INSERT INTO album(album_name,artist_id,photo_file_path) VALUES('Joji Album 1',3,'photo/joji-gimmelove.jpg');
INSERT INTO album(album_name,artist_id,photo_file_path) VALUES('Joji Album 2',3,'photo/joji1.jpg');
INSERT INTO album(album_name,artist_id,photo_file_path) VALUES('Joji Album 3',3,'photo/joji-run.jpg');

INSERT INTO song(song_name,song_year,artist_id, album_id,audio_file_path,photo_file_path) VALUES('The Show Must Go On',1983,1,1,'Queen - The Show Must Go On.mp3','photo/Queen-TheShowMustGoOn.jpg');

INSERT INTO song(song_name,song_year,artist_id, album_id,audio_file_path,photo_file_path) VALUES('Do you believe',2011,2,2,'deftones-do-you-believe.mp3','photo/deftones-doYouBelieve.jpg');
INSERT INTO song(song_name,song_year,artist_id, album_id,audio_file_path,photo_file_path) VALUES('Sextape',2011,2,2,'Deftones – Sextape.mp3','photo/deftones-doYouBelieve.jpg');
INSERT INTO song(song_name,song_year,artist_id, album_id,audio_file_path,photo_file_path) VALUES('My Own Summer',2013,2,3,'Deftones – My Own Summer (Shove It).mp3','photo/deftones-myOwnSummer.jpg');

INSERT INTO song(song_name,song_year,artist_id, album_id,audio_file_path,photo_file_path) VALUES('Gimme love',2021,3,4,'Joji_-_Gimme_Love_(musmore.com).mp3','photo/joji-gimmelove.jpg');
INSERT INTO song(song_name,song_year,artist_id, album_id,audio_file_path,photo_file_path) VALUES('Yeah Right',2019,3,5,'Joji_-_Yeah_Right_(musmore.com).mp3','photo/joji1.jpg');
INSERT INTO song(song_name,song_year,artist_id, album_id,audio_file_path,photo_file_path) VALUES('Run',2020,3,6,'Joji_-_Run_(musmore.com).mp3','photo/joji-run.jpg');

--insert into listener (username, password, role)values ('Listener1','Listener1','ROLE_ADMIN');
-- insert into listener (username, password, role) values ('Listener2','Listener2','ROLE_USER');
-- INSERT INTO listener_song(listener_id,song_id) VALUES(1,1);
-- INSERT INTO listener_song(listener_id,song_id) VALUES(1,3);
-- INSERT INTO listener_song(listener_id,song_id) VALUES(1,6);
-- INSERT INTO listener_song(listener_id,song_id) VALUES(1,5);


--INSERT INTO listener_song(listener_id,song_id) VALUES(2,1);
--INSERT INTO listener_song(listener_id,song_id) VALUES(2,3);



