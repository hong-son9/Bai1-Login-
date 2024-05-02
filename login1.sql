create database login1;
use login1;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `roles` varbinary(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
ALTER TABLE `user` DROP COLUMN `role`;

CREATE TABLE `post` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `create_edit` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `comment` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `post_id` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
select * from user;