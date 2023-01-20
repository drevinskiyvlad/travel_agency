-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema travel_agency_3
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travel_agency_3` DEFAULT CHARACTER SET utf8mb3 ;
USE `travel_agency_3` ;

-- -----------------------------------------------------
-- Table `travel_agency_3`.`hotel_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency_3`.`hotel_type` ;

CREATE TABLE IF NOT EXISTS `travel_agency_3`.`hotel_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hotel_type_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `hotel_type_name_UNIQUE` (`hotel_type_name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travel_agency_3`.`offer_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency_3`.`offer_type` ;

CREATE TABLE IF NOT EXISTS `travel_agency_3`.`offer_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `offer_type_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`offer_type_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travel_agency_3`.`offer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency_3`.`offer` ;

CREATE TABLE IF NOT EXISTS `travel_agency_3`.`offer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(8) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `offer_type_id` INT NOT NULL,
  `hotel_type_id` INT NOT NULL,
  `hotel_name` VARCHAR(64) NOT NULL,
  `places` INT NOT NULL,
  `discount` DECIMAL(4,3) NOT NULL,
  `is_hot` TINYINT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  INDEX `ofer_type_id_idx` (`offer_type_id` ASC) VISIBLE,
  INDEX `fk_offer_hotel_type1_idx` (`hotel_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_offer_hotel_type1`
    FOREIGN KEY (`hotel_type_id`)
    REFERENCES `travel_agency_3`.`hotel_type` (`id`),
  CONSTRAINT `ofer_type_id`
    FOREIGN KEY (`offer_type_id`)
    REFERENCES `travel_agency_3`.`offer_type` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travel_agency_3`.`order_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency_3`.`order_status` ;

CREATE TABLE IF NOT EXISTS `travel_agency_3`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_status_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `order_status_name_UNIQUE` (`order_status_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travel_agency_3`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency_3`.`user_role` ;

CREATE TABLE IF NOT EXISTS `travel_agency_3`.`user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`user_role_name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travel_agency_3`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency_3`.`user` ;

CREATE TABLE IF NOT EXISTS `travel_agency_3`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `user_role_id` INT NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `blocked` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,
  INDEX `fk_user_user_role1_idx` (`user_role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_role1`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `travel_agency_3`.`user_role` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 28
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travel_agency_3`.`user_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travel_agency_3`.`user_order` ;

CREATE TABLE IF NOT EXISTS `travel_agency_3`.`user_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(8) NOT NULL,
  `user_id` INT NOT NULL,
  `offer_id` INT NOT NULL,
  `order_status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `offer_id_idx` (`offer_id` ASC) VISIBLE,
  INDEX `order_status_id_idx` (`order_status_id` ASC) VISIBLE,
  CONSTRAINT `offer_id`
    FOREIGN KEY (`offer_id`)
    REFERENCES `travel_agency_3`.`offer` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `order_status_id`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `travel_agency_3`.`order_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `travel_agency_3`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
