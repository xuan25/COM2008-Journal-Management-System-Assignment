-- Account related

CREATE TABLE Account(
    email       VARCHAR(255),
    title       VARCHAR(255),
    forename    VARCHAR(255),
    surname     VARCHAR(255),
    university  VARCHAR(255),
    PRIMARY KEY (email)
);

CREATE TABLE Author(
    email           VARCHAR(255),
    hashedPassword  VARCHAR(255),
    PRIMARY KEY (email),
    FOREIGN KEY (email) REFERENCES Account(email)
);

CREATE TABLE Reviewer(
    email           VARCHAR(255),
    hashedPassword  VARCHAR(255),
    PRIMARY KEY (email),
    FOREIGN KEY (email) REFERENCES Account(email)
);

CREATE TABLE Editor(
    email           VARCHAR(255),
    hashedPassword  VARCHAR(255),
    PRIMARY KEY (email),
    FOREIGN KEY (email) REFERENCES Account(email)
);


-- Jounal related

CREATE TABLE Journal(
    issn        VARCHAR(14),
    journalName VARCHAR(255),
    cheifEmail  VARCHAR(255),
    PRIMARY KEY (issn),
    FOREIGN KEY (cheifEmail) REFERENCES Editor(email)
);

CREATE TABLE EditorOnBoard(
    issn    VARCHAR(255),
    email   VARCHAR(255),
    PRIMARY KEY (issn, email),
    FOREIGN KEY (issn) REFERENCES Journal(issn),
    FOREIGN KEY (email) REFERENCES Editor(email)
);

CREATE TABLE Edition(
    issn    VARCHAR(14),
    volume  INT,
    edition INT,
    PRIMARY KEY (issn, volume, edition),
    FOREIGN KEY (issn) REFERENCES Journal(issn)
);


-- Article related

CREATE TABLE Document(
    uuid            VARCHAR(255),
    documentData    LONGBLOB,
    PRIMARY KEY (uuid)
);

CREATE TABLE Submission(
    issn            VARCHAR(14),
    submissionID    VARCHAR(14),
    title           VARCHAR(255),
    mainAuthor      VARCHAR(255),
    corrAuthor      VARCHAR(255),
    contentAbstract VARCHAR(255),
    draftID         VARCHAR(255),
    finalID         VARCHAR(255),
    status          INT,
    PRIMARY KEY (issn, submissionID),
    FOREIGN KEY (issn) REFERENCES Journal(issn),
    FOREIGN KEY (mainAuthor) REFERENCES Author(email),
    FOREIGN KEY (corrAuthor) REFERENCES Author(email)
);

CREATE TABLE SubmissionAuthor(
    issn            VARCHAR(14),
    submissionID    VARCHAR(255),
    email           VARCHAR(255),
    PRIMARY KEY (issn, submissionID, email),
    FOREIGN KEY (issn, submissionID) REFERENCES Submission(issn, submissionID),
    FOREIGN KEY (email) REFERENCES Author(email)
);

CREATE TABLE Article(
    issn            VARCHAR(14),
    submissionID    VARCHAR(255),
    volume          INT,
    edition         INT,
    PRIMARY KEY (issn, submissionID),
    FOREIGN KEY (issn, submissionID) REFERENCES Submission(issn, submissionID),
    FOREIGN KEY (issn, volume, edition) REFERENCES Edition(issn, volume, edition)
);


-- Review related

CREATE TABLE Review(
    email           VARCHAR(255),
    issn            VARCHAR(255),
    submissionID    VARCHAR(255),
    summary         VARCHAR(255),
    verdict         INT,
    timestamp       BIGINT,
    PRIMARY KEY (email, issn, submissionID),
    FOREIGN KEY (email) REFERENCES Reviewer(email),
    FOREIGN KEY (issn, submissionID) REFERENCES Submission(issn, submissionID)
);

CREATE TABLE TypoError(
    email           VARCHAR(255),
    issn            VARCHAR(255),
    submissionID    VARCHAR(255),
    num             INT,
    content         VARCHAR(255),
    PRIMARY KEY (email, issn, submissionID, num),
    FOREIGN KEY (email, issn, submissionID) REFERENCES Review(email, issn, submissionID)
);

CREATE TABLE Criticism(
    email           VARCHAR(255),
    issn            VARCHAR(255),
    submissionID    VARCHAR(255),
    num             INT,
    content         VARCHAR(255),
    PRIMARY KEY (email, issn, submissionID, num),
    FOREIGN KEY (email, issn, submissionID) REFERENCES Review(email, issn, submissionID)
);