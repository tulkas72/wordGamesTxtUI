create database if not exists WordGames;
use WordGames;

create user 'wordGames'@'localhost' identified by 'wordGames';
grant all privileges on WordGames.* to 'wordGames'@'localhost';

create table Game
(
    id    integer primary key auto_increment,
    name  varchar(60),
    rules text,
    lastWord varchar(23)
);

-- drop table if exists Player;
create table Player
(
    id       integer primary key auto_increment,
    name     varchar(60),
    nickname varchar(15) unique,
    email    varchar(255) unique not null
);

drop table if exists `Match`;

create table `Match`
(
    id        integer primary key auto_increment,
    gameId    integer,
    playerId  integer,
    word      varchar(23),
    game_date Date,
    score     integer, -- in wordle or similar # of attempts
    win_loss  Bool, -- 1 = win, 0 = loss
    foreign key (gameId) references Game (id),
    foreign key (playerId) references Player (id)
);

insert into Player ( name, nickname, email)
values ( 'jmsa', 'tulkas', 'jsanalv344@g.educaand.es');


insert into Player ( name, nickname, email)
values ( 'player1', 'player1', 'player1@wordgames.com'),
       ( 'player2', 'player2', 'player2@wordgames.com'),
       ( 'player3', 'player3', 'player3@wordgames.com'),
       ( 'player4', 'player4', 'player4@wordgames.com'),
       ( 'player5', 'player5', 'player5@wordgames.com');


insert into Game(name,rules,lastWord)
values ('Wordle','rules1','orden'),
       ('Hangman','rules2','banana');


insert into `Match`(playerId,gameId,word,win_loss,score, game_date)
values (1,1,'orden',1,3,'2018-01-01'),
       (2,1,'avion',1,5,'2018-12-01'),
       (3,1,'reloj',0,7,'2018-10-11'),
       (4,1,'perro',0,7,'2018-05-11'),
       (5,1,'botar',0,7,'2018-04-13'),
       (6,1,'votar',1,6,'2018-07-15');








