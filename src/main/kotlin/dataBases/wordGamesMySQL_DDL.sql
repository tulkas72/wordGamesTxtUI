create database WordGames;
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

drop table if exists Player;
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

