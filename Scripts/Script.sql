create table BOARD2(
	seq int AUTO_INCREMENT,
	title varchar(100),
	writer varchar(20),
	content varchar(1000),
	regdate timestamp default current_timestamp,
	image_path VARCHAR(255),
	cnt int default 0,
	primary key(seq)
);