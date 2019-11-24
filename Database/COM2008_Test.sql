-- New Journal and Cheif
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testCheifEditor@test.com", "Mr.", "CheifEditor", "Test", "testUniversity");
INSERT INTO Editor (email, hashedPassword) VALUES ("testCheifEditor@test.com", "hashedPassword");
INSERT INTO Journal (issn, journalName, cheifEmail) VALUES ("ISSN 0000-0000", "TestJournal", "testCheifEditor@test.com");
INSERT INTO EditorOnBoard (issn, email) VALUES ("ISSN 0000-0000", "testCheifEditor@test.com");

-- New Edition
INSERT INTO Edition (issn, volume, edition) VALUES ("ISSN 0000-0000", 1, 1);

-- Add Editor
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testEditor@test.com", "Ms.", "Editor", "Test", "testUniversity");
INSERT INTO Editor (email, hashedPassword) VALUES ("testEditor@test.com", "hashedPassword");
INSERT INTO EditorOnBoard (issn, email) VALUES ("ISSN 0000-0000", "testEditor@test.com");


-- New Author 
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testAuthor@test.com", "Mr.", "Author", "Test", "testUniversity");
INSERT INTO Author (email, hashedPassword) VALUES ("testAuthor@test.com", "hashedPassword");

-- New Submission
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testCoAuthor@test.com", "Mr.", "CoAuthor", "Test", "testUniversity");
INSERT INTO Author (email, hashedPassword) VALUES ("testCoAuthor@test.com", "hashedPassword");
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testOtherAuthor@test.com", "Mr.", "OtherAuthor", "Test", "testUniversity");
INSERT INTO Author (email, hashedPassword) VALUES ("testOtherAuthor@test.com", "hashedPassword");

INSERT INTO Submission (issn, submissionID, title, mainAuthor, coAuthor, contentAbstract, draftID, finalID, status) VALUES ("ISSN 0000-0000", "001", "testTitle", "testAuthor@test.com", "testCoAuthor@test.com", "testAbtract", "draft", "final", 0);
INSERT INTO SubmissionAuthor (issn, submissionID, email) VALUES ("ISSN 0000-0000", "001", "testAuthor@test.com");
INSERT INTO SubmissionAuthor (issn, submissionID, email) VALUES ("ISSN 0000-0000", "001", "testCoAuthor@test.com");
INSERT INTO SubmissionAuthor (issn, submissionID, email) VALUES ("ISSN 0000-0000", "001", "testOtherAuthor@test.com");


-- New Reviewers
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testReviewer1@test.com", "Mr.", "Reviewer1", "Test", "testUniversity");
INSERT INTO Reviewer (email, hashedPassword) VALUES ("testReviewer1@test.com", "hashedPassword");
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testReviewer2@test.com", "Mr.", "Reviewer2", "Test", "testUniversity");
INSERT INTO Reviewer (email, hashedPassword) VALUES ("testReviewer2@test.com", "hashedPassword");
INSERT INTO Account (email, title, forename, surname, university) VALUES ("testReviewer3@test.com", "Mr.", "Reviewer3", "Test", "testUniversity");
INSERT INTO Reviewer (email, hashedPassword) VALUES ("testReviewer3@test.com", "hashedPassword");

-- Add Reviews
INSERT INTO Review (email, issn, submissionID, summary, verdict, timestamp) VALUES ("testReviewer1@test.com", "ISSN 0000-0000", "001", "testSummary1", 3, 100);
INSERT INTO TypoError (email, issn, submissionID, num, content) VALUES ("testReviewer1@test.com", "ISSN 0000-0000", "001", 1, "testTypoError1");
INSERT INTO TypoError (email, issn, submissionID, num, content) VALUES ("testReviewer1@test.com", "ISSN 0000-0000", "001", 2, "testTypoError2");
INSERT INTO Criticism (email, issn, submissionID, num, content) VALUES ("testReviewer1@test.com", "ISSN 0000-0000", "001", 1, "testCriticism1");
INSERT INTO Criticism (email, issn, submissionID, num, content) VALUES ("testReviewer1@test.com", "ISSN 0000-0000", "001", 2, "testCriticism2");
INSERT INTO Review (email, issn, submissionID, summary, verdict, timestamp) VALUES ("testReviewer2@test.com", "ISSN 0000-0000", "001", "testSummary2", 3, 200);
INSERT INTO TypoError (email, issn, submissionID, num, content) VALUES ("testReviewer2@test.com", "ISSN 0000-0000", "001", 1, "testTypoError1");
INSERT INTO TypoError (email, issn, submissionID, num, content) VALUES ("testReviewer2@test.com", "ISSN 0000-0000", "001", 2, "testTypoError2");
INSERT INTO Criticism (email, issn, submissionID, num, content) VALUES ("testReviewer2@test.com", "ISSN 0000-0000", "001", 1, "testCriticism1");
INSERT INTO Criticism (email, issn, submissionID, num, content) VALUES ("testReviewer2@test.com", "ISSN 0000-0000", "001", 2, "testCriticism2");
INSERT INTO Review (email, issn, submissionID, summary, verdict, timestamp) VALUES ("testReviewer3@test.com", "ISSN 0000-0000", "001", "testSummary3", 3, 300);
INSERT INTO TypoError (email, issn, submissionID, num, content) VALUES ("testReviewer3@test.com", "ISSN 0000-0000", "001", 1, "testTypoError1");
INSERT INTO TypoError (email, issn, submissionID, num, content) VALUES ("testReviewer3@test.com", "ISSN 0000-0000", "001", 2, "testTypoError2");
INSERT INTO Criticism (email, issn, submissionID, num, content) VALUES ("testReviewer3@test.com", "ISSN 0000-0000", "001", 1, "testCriticism1");
INSERT INTO Criticism (email, issn, submissionID, num, content) VALUES ("testReviewer3@test.com", "ISSN 0000-0000", "001", 2, "testCriticism2");

-- Add article to an edition
INSERT INTO Article (issn, submissionID, volume, edition) VALUES ("ISSN 0000-0000", "001", 1, 1);