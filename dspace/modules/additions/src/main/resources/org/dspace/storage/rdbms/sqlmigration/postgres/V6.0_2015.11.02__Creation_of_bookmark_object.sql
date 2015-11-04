

CREATE TABLE bookmark (
	bookmark_id 	INTEGER PRIMARY KEY not null,
	title 			VARCHAR(50) ,
	date_created 	DATE,
	creator		UUID references EPERSON(uuid) not null,
	item			UUID references ITEM (uuid) not null
);
CREATE SEQUENCE bookmark_seq;

