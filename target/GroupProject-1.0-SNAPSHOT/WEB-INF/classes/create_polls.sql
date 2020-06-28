/*
    Database name: GroupProject
    Username: nbuser
    Password: nbuser
*/

CREATE TABLE polls (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(255) NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer1 VARCHAR(255) NOT NULL,
    answer2 VARCHAR(255) NOT NULL,
    answer3 VARCHAR(255) NOT NULL,
    answer4 VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE responses (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(255) NOT NULL,
    question VARCHAR(255) NOT NULL,
    choice VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    poll_id INTEGER DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (poll_id) REFERENCES polls(id)
);

INSERT INTO NBUSER.POLLS ("NAME", QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, "TIMESTAMP") VALUES ('admin', 'Test poll question 1?', 'This is Answer 1', 'This is Answer 2', 'This is Answer 3', 'This is Answer 4', '2020-04-25 12:51:07.968');
INSERT INTO NBUSER.RESPONSES ("NAME", QUESTION, CHOICE, "TIMESTAMP", POLL_ID) VALUES ('user1', 'Test poll question 1?', 'This is Answer 1', '2020-04-25 12:51:20.446', 1);

INSERT INTO NBUSER.POLLS ("NAME", QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, "TIMESTAMP") VALUES ('admin', 'Test poll question 2?', 'This is Answer 1', 'This is Answer 2', 'This is Answer 3', 'This is Answer 4', '2020-04-25 12:51:57.324');
INSERT INTO NBUSER.RESPONSES ("NAME", QUESTION, CHOICE, "TIMESTAMP", POLL_ID) VALUES ('admin', 'Test poll question 2?', 'This is Answer 1', '2020-04-25 12:52:07.173', 2);
INSERT INTO NBUSER.RESPONSES ("NAME", QUESTION, CHOICE, "TIMESTAMP", POLL_ID) VALUES ('user1', 'Test poll question 2?', 'This is Answer 2', '2020-04-25 12:52:18.735', 2);
INSERT INTO NBUSER.RESPONSES ("NAME", QUESTION, CHOICE, "TIMESTAMP", POLL_ID) VALUES ('user2', 'Test poll question 2?', 'This is Answer 3', '2020-04-25 12:52:35.093', 2);