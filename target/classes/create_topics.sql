/*
    Database name: GroupProject
    Username: nbuser
    Password: nbuser
*/

CREATE TABLE topics (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE comments (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(255) NOT NULL,
    commentdt VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    topic_id INTEGER DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (topic_id) REFERENCES topics(id)
);

CREATE TABLE attachments (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    filename VARCHAR(255) DEFAULT NULL,
    content_type VARCHAR(255) DEFAULT NULL,
    content BLOB DEFAULT NULL,
    topic_id INTEGER DEFAULT NULL,
    comment_id INTEGER DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (topic_id) REFERENCES topics(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id)
);

INSERT INTO NBUSER.TOPICS ("NAME", TITLE, CONTENT, CATEGORY, "TIMESTAMP") VALUES ('user1', 'Testing Title 1', 'Admin Change Content', 'lecture', '2020-04-25 12:22:51.272');
INSERT INTO NBUSER.TOPICS ("NAME", TITLE, CONTENT, CATEGORY, "TIMESTAMP") VALUES ('user1', 'Testing Title 2', 'Testing Content 2', 'lecture', '2020-04-25 12:16:13.909');
INSERT INTO NBUSER.TOPICS ("NAME", TITLE, CONTENT, CATEGORY, "TIMESTAMP") VALUES ('user2', 'Testing Title 3', 'Testing Content 3', 'laboratory', '2020-04-25 12:19:48.809');
INSERT INTO NBUSER.TOPICS ("NAME", TITLE, CONTENT, CATEGORY, "TIMESTAMP") VALUES ('admin', 'Admin Title', 'Admin Content', 'lecture', '2020-04-25 12:23:36.954');

INSERT INTO NBUSER.COMMENTS ("NAME", COMMENTDT, "TIMESTAMP", TOPIC_ID) VALUES ('user2', 'Test Comment 1', '2020-04-25 12:20:26.584', 1);