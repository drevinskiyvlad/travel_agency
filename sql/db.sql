-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema travel_agency
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travel_agency` DEFAULT CHARACTER SET utf8;
-- -----------------------------------------------------
USE `travel_agency`;

-- -----------------------------------------------------
-- Table `travel_agency`.`offer_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`offer_type`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`offer_type`
(
    `id`              INT         NOT NULL AUTO_INCREMENT,
    `offer_type_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `offer_type_name_UNIQUE` (`offer_type_name` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`user_role`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`user_role`
(
    `id`             INT         NOT NULL AUTO_INCREMENT,
    `user_role_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_role_name_UNIQUE` (`user_role_name` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`order_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`order_status`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`order_status`
(
    `id`                INT         NOT NULL AUTO_INCREMENT,
    `order_status_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `order_status_name_UNIQUE` (`order_status_name` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`user`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`user`
(
    `id`           INT         NOT NULL AUTO_INCREMENT,
    `email`        VARCHAR(64) NOT NULL,
    `password`     VARCHAR(64) NOT NULL,
    `user_role_id` INT         NOT NULL,
    `first_name`   VARCHAR(45) NOT NULL,
    `last_name`    VARCHAR(45) NOT NULL,
    `phone`        VARCHAR(15) NOT NULL,
    `blocked`      TINYINT     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `fk_user_user_role_idx` (`user_role_id` ASC) VISIBLE,
    UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,
    CONSTRAINT `user_role`
        FOREIGN KEY (`user_role_id`)
            REFERENCES `travel_agency`.`user_role` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`hotel_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`hotel_type`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`hotel_type`
(
    `id`              INT         NOT NULL AUTO_INCREMENT,
    `hotel_type_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `hotel_type_name_UNIQUE` (`hotel_type_name` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`hotel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`hotel`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`hotel`
(
    `id`            INT          NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(128) NOT NULL,
    `type_id` INT          NOT NULL,
    `city`          VARCHAR(45)  NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_hotel_hotel_type1_idx` (`type_id` ASC) VISIBLE,
    CONSTRAINT `hotel_type`
        FOREIGN KEY (`type_id`)
            REFERENCES `travel_agency`.`hotel_type` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`offer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`offer`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`offer`
(
    `id`            INT            NOT NULL AUTO_INCREMENT,
    `code`          VARCHAR(8)     NOT NULL,
    `hotel_id`      INT            NOT NULL,
    `offer_type_id` INT            NOT NULL,
    `places`        INT            NOT NULL,
    `discount`      DECIMAL(4, 3)  NOT NULL,
    `is_hot`        TINYINT        NOT NULL,
    `active`        TINYINT        NOT NULL,
    `price`         DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_offer_offer_type1_idx` (`offer_type_id` ASC) VISIBLE,
    UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
    INDEX `offer_hotel_idx` (`hotel_id` ASC) VISIBLE,
    CONSTRAINT `offer_type`
        FOREIGN KEY (`offer_type_id`)
            REFERENCES `travel_agency`.`offer_type` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `offer_hotel`
        FOREIGN KEY (`hotel_id`)
            REFERENCES `travel_agency`.`hotel` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`user_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency`.`user_order`;

CREATE TABLE IF NOT EXISTS `travel_agency`.`user_order`
(
    `id`              INT        NOT NULL AUTO_INCREMENT,
    `code`            VARCHAR(8) NOT NULL,
    `user_id`         INT        NOT NULL,
    `offer_id`        INT        NOT NULL,
    `order_status_id` INT        NOT NULL,
    PRIMARY KEY (`id`, `order_status_id`),
    UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
    INDEX `fk_user_order_user1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_user_order_offer1_idx` (`offer_id` ASC) VISIBLE,
    INDEX `fk_user_order_order_status1_idx` (`order_status_id` ASC) VISIBLE,
    CONSTRAINT `order_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `travel_agency`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `order_offer`
        FOREIGN KEY (`offer_id`)
            REFERENCES `travel_agency`.`offer` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT `order_status`
        FOREIGN KEY (`order_status_id`)
            REFERENCES `travel_agency`.`order_status` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

