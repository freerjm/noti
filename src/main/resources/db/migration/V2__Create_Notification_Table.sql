CREATE TABLE `NOTIFICATION`
(
	`UUID`			VARCHAR(128)	NOT NULL,
	`MESSAGE`		VARCHAR(2048)	NOT NULL,
	`STATUS`		VARCHAR(128)	NOT NULL,
	`SEND_AT`		DATETIME		NULL,
	`SENT_AT`		DATETIME		NULL,

	PRIMARY KEY(`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
