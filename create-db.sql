DROP DATABASE IF EXISTS `to-do-list`;

CREATE DATABASE `to-do-list`;

USE `to-do-list`;

CREATE TABLE `users` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(50) NOT NULL,
    `password` CHAR(68) NOT NULL,
    `active` TINYINT NOT NULL DEFAULT 1,
    `role` varchar(50) NOT NULL DEFAULT 'ROLE_USER',
    PRIMARY KEY (`user_id`),
    UNIQUE (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `tasks` (
    `task_id` INT NOT NULL AUTO_INCREMENT,
    `content` TEXT NOT NULL,
    `priority` ENUM('HIGH', 'LOW', 'NORMAL') NOT NULL,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`task_id`),
    CONSTRAINT `fk_tasks_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `users` (`user_name`, `password`, `role`)
VALUES ('admin', '$2a$10$4td0tV3b/wpNUQHR6eGNjeGOiZgGg./uZs/.5cUA55.QyEkJzh/XO', 'ROLE_ADMIN');
INSERT INTO `users` (`user_name`, `password`)
VALUES ('hazem', '$2a$10$4td0tV3b/wpNUQHR6eGNjeGOiZgGg./uZs/.5cUA55.QyEkJzh/XO');
INSERT INTO `users` (`user_name`, `password`)
VALUES ('nadine', '$2a$10$4td0tV3b/wpNUQHR6eGNjeGOiZgGg./uZs/.5cUA55.QyEkJzh/XO');
INSERT INTO `users` (`user_name`, `password`)
VALUES ('marc', '$2a$10$4td0tV3b/wpNUQHR6eGNjeGOiZgGg./uZs/.5cUA55.QyEkJzh/XO');

INSERT INTO `tasks` (`content`, `priority`, `user_id`) VALUES ('play basketball', 'NORMAL', 2);
INSERT INTO `tasks` (`content`, `priority`, `user_id`) VALUES ('go swimming', 'LOW', 2);
INSERT INTO `tasks` (`content`, `priority`, `user_id`) VALUES ('do campaign', 'HIGH', 3);
INSERT INTO `tasks` (`content`, `priority`, `user_id`) VALUES ('listen to radiohead', 'LOW', 3);
INSERT INTO `tasks` (`content`, `priority`, `user_id`) VALUES ('do math', 'HIGH', 4);
INSERT INTO `tasks` (`content`, `priority`, `user_id`) VALUES ('eat snacks', 'NORMAL', 4);