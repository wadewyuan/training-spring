-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `email` VARCHAR(45) NULL,
  `created_time` DATETIME NULL,
  `updated_time` DATETIME NULL,
  `last_login_time` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `book` ;

CREATE TABLE IF NOT EXISTS `book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `author` VARCHAR(45) NULL,
  `picture` VARCHAR(45) NULL,
  `description` VARCHAR(1400) NULL,
  `owner_id` INT NULL,
  `current_user_id` INT NULL,
  `created_time` DATETIME NULL,
  `updated_time` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role` ;

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `display_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `permission` ;

CREATE TABLE IF NOT EXISTS `permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `display_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `book_history` ;

CREATE TABLE IF NOT EXISTS `book_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NULL,
  `user_id` INT NULL,
  `status_time` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `role_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role_permission` ;

CREATE TABLE IF NOT EXISTS `role_permission` (
  `role_id` INT NOT NULL,
  `permission_id` INT NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_role` ;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `contact` ;

CREATE TABLE IF NOT EXISTS `contact` (
  `user_id` INT NOT NULL,
  `display_name` VARCHAR(45) NULL,
  `phone` VARCHAR(20) NULL,
  `address` VARCHAR(45) NULL,
  `department` VARCHAR(45) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `user` (`id`, `user_name`, `password`, `email`, `created_time`, `updated_time`, `last_login_time`) VALUES (1, 'wade_yuan', '000000', 'wadeyuanwy@hotmail.com', '2016-06-19 08:00:00', '2016-06-19 08:00:00', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `role`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `role` (`id`, `name`, `display_name`) VALUES (1, 'admin', 'System Administrator');
INSERT INTO `role` (`id`, `name`, `display_name`) VALUES (2, 'general', 'General User');

COMMIT;


-- -----------------------------------------------------
-- Data for table `permission`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `permission` (`id`, `name`, `display_name`) VALUES (1, 'add_book', 'Add Book');
INSERT INTO `permission` (`id`, `name`, `display_name`) VALUES (2, 'edit_book', 'Edit Book');
INSERT INTO `permission` (`id`, `name`, `display_name`) VALUES (3, 'delete_book', 'Delete Book');
INSERT INTO `permission` (`id`, `name`, `display_name`) VALUES (4, 'borrow_book', 'Borrow Book');

COMMIT;


-- -----------------------------------------------------
-- Data for table `role_permission`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 1);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 2);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 3);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (2, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_role`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `contact`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `contact` (`user_id`, `display_name`, `phone`, `address`, `department`) VALUES (1, 'Wade Yuan', '15927141136', '湖北省武汉市关山大道', '软件开发部');

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
