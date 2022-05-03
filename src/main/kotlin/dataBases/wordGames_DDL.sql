
-- para SQLite

create table Game
(
    id    integer primary key autoincrement,
    name  text,
    rules text,
    lastWord text
);

-- drop table if exists Player;
create table Player
(
    id       integer primary key autoincrement,
    name     text,
    nickname text,
    email    text unique not null
);

drop table if exists Match;

create table Match
(
    id        integer primary key autoincrement,
    gameId    integer,
    playerId  integer,
    word      text,
    game_date text,
    score     integer, -- in wordle or similar # of attempts
    win_loss  integer, -- 1 = win, 0 = loss
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


insert into Match(playerId,gameId,win_loss,score, game_date)
values (1,1,1,4,'2018-01-01'),
       (1,1,1,3,'2018-01-01'),
       (2,2,1,5,'2018-15-01'),
       (3,1,0,7,'2018-15-11'),
       (4,2,0,7,'2018-15-11'),
       (5,1,0,7,'2018-15-11'),
       (6,1,1,6,'2018-15-11');