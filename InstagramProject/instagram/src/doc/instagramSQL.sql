-- MySQL Script generated by MySQL Workbench
-- Wed May 13 23:49:57 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema instaclone
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `instaclone` ;

-- -----------------------------------------------------
-- Schema instaclone
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `instaclone` DEFAULT CHARACTER SET utf8 ;
USE `instaclone` ;

-- -----------------------------------------------------
-- Table `instaclone`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`user` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`user` (
  `userId` VARCHAR(20) NOT NULL,
  `userName` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `followerNum` INT NULL DEFAULT 0,
  `followingNum` INT NULL DEFAULT 0,
  `postNum` INT NULL DEFAULT 0,
  `email` VARCHAR(45) NULL,
  `gender` VARCHAR(2) NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instaclone`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`post` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`post` (
  `postId` VARCHAR(20) NOT NULL,
  `caption` VARCHAR(100) NOT NULL,
  `imageSrc` VARCHAR(45) NULL,
  `likeNum` INT NOT NULL,
  PRIMARY KEY (`postId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instaclone`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`comment` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`comment` (
  `commentId` VARCHAR(20) NOT NULL,
  `comment` VARCHAR(100) NOT NULL,
  `likeNum` INT NOT NULL,
  `userId` VARCHAR(20) NOT NULL,
  `postId` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`commentId`),
  INDEX `fk_comment_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_comment_post1_idx` (`postId` ASC) VISIBLE,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `instaclone`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_post1`
    FOREIGN KEY (`postId`)
    REFERENCES `instaclone`.`post` (`postId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instaclone`.`hashtag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`hashtag` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`hashtag` (
  `hashtagId` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`hashtagId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instaclone`.`persontag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`persontag` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`persontag` (
  `hastagID` VARCHAR(20) NOT NULL,
  `user_user_id` VARCHAR(20) NOT NULL,
  `post_postId` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`hastagID`),
  INDEX `fk_persontag_user1_idx` (`user_user_id` ASC) VISIBLE,
  INDEX `fk_persontag_post1_idx` (`post_postId` ASC) VISIBLE,
  CONSTRAINT `fk_persontag_user1`
    FOREIGN KEY (`user_user_id`)
    REFERENCES `instaclone`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_persontag_post1`
    FOREIGN KEY (`post_postId`)
    REFERENCES `instaclone`.`post` (`postId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instaclone`.`Follow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`Follow` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`Follow` (
  `followIdx` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(20) NOT NULL,
  `followingId` VARCHAR(20) NOT NULL,
  INDEX `fk_user_has_user_user2_idx` (`followingId` ASC) VISIBLE,
  INDEX `fk_user_has_user_user1_idx` (`userId` ASC) VISIBLE,
  PRIMARY KEY (`followIdx`),
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `instaclone`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`followingId`)
    REFERENCES `instaclone`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instaclone`.`PersonTag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`PersonTag` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`PersonTag` (
  `personTagIdx` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(20) NOT NULL,
  `postId` VARCHAR(20) NOT NULL,
  `postOwner` VARCHAR(4) NOT NULL,
  INDEX `fk_user_has_post_post1_idx` (`postId` ASC) VISIBLE,
  INDEX `fk_user_has_post_user1_idx` (`userId` ASC) VISIBLE,
  PRIMARY KEY (`personTagIdx`),
  CONSTRAINT `fk_user_has_post_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `instaclone`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_post_post1`
    FOREIGN KEY (`postId`)
    REFERENCES `instaclone`.`post` (`postId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `instaclone`.`HashGroup`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instaclone`.`HashGroup` ;

CREATE TABLE IF NOT EXISTS `instaclone`.`HashGroup` (
  `HashGroupIdx` INT NOT NULL AUTO_INCREMENT,
  `postId` VARCHAR(20) NOT NULL,
  `hashtagId` VARCHAR(20) NOT NULL,
  INDEX `fk_post_has_hashtag_hashtag1_idx` (`hashtagId` ASC) VISIBLE,
  INDEX `fk_post_has_hashtag_post1_idx` (`postId` ASC) VISIBLE,
  PRIMARY KEY (`HashGroupIdx`),
  CONSTRAINT `fk_post_has_hashtag_post1`
    FOREIGN KEY (`postId`)
    REFERENCES `instaclone`.`post` (`postId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_has_hashtag_hashtag1`
    FOREIGN KEY (`hashtagId`)
    REFERENCES `instaclone`.`hashtag` (`hashtagId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
