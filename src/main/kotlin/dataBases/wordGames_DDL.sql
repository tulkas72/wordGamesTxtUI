create table Game
(
    id    integer primary key autoincrement,
    name  text,
    rules text,
    lastWord text
);

create table Player
(
    id       integer primary key autoincrement,
    name     text,
    nickname text,
    email    text
);

create table Match
(
    id        integer primary key autoincrement,
    gameId    integer,
    playerId  integer,
    word      text,
    game_date integer,
    score     integer, -- in wordle or similar # of attempts
    win_loss  integer, -- 1 = win, 0 = loss
    foreign key (gameId) references Game (id),
    foreign key (playerId) references Player (id)
);
