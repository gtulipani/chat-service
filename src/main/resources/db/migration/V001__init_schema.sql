CREATE TABLE IF NOT EXISTS `users` (
	`id`						bigint(11) unsigned NOT NULL AUTO_INCREMENT,
	`created_on`		timestamp  					NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`last_modified`	timestamp  					NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`username`			varchar(255)				NOT NULL,
	`password`			varchar(255)				NOT NULL,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;
