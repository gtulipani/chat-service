CREATE TABLE IF NOT EXISTS `users` (
	`id`						bigint(11) unsigned NOT NULL AUTO_INCREMENT,
	`created_on`		timestamp  					NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`last_modified`	timestamp  					NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`username`			varchar(255)				NOT NULL,
	`password`			varchar(255)				NOT NULL,
	`token`					varchar(255)				DEFAULT NULL,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `messages` (
	`id`						bigint(11) unsigned NOT NULL AUTO_INCREMENT,
	`created_on`		datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`last_modified`	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`sender`				bigint(11)		NOT NULL,
	`recipient`			bigint(11)		NOT NULL,
	`content`				varchar(255)	NOT NULL,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;
