SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mi-1000_shazamm
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mi-1000_shazamm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mi-1000_shazamm` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `mi-1000_shazamm` ;

-- -----------------------------------------------------
-- Table `mi-1000_shazamm`.`joueur`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mi-1000_shazamm`.`joueur` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `mi-1000_shazamm`.`joueur` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(50) NULL,
  `prenom` VARCHAR(50) NULL,
  `avatar` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mi-1000_shazamm`.`partie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mi-1000_shazamm`.`partie` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `mi-1000_shazamm`.`partie` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_joueur1` INT UNSIGNED NOT NULL,
  `id_joueur2` INT UNSIGNED NOT NULL,
  `id_vainqueur` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `id_joueur_idx` (`id_joueur1` ASC) VISIBLE,
  INDEX `id_joueur2_idx` (`id_joueur2` ASC) VISIBLE,
  INDEX `id_vainqueur_idx` (`id_vainqueur` ASC) VISIBLE,
  CONSTRAINT `id_joueur1`
    FOREIGN KEY (`id_joueur1`)
    REFERENCES `mi-1000_shazamm`.`joueur` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_joueur2`
    FOREIGN KEY (`id_joueur2`)
    REFERENCES `mi-1000_shazamm`.`joueur` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_vainqueur`
    FOREIGN KEY (`id_vainqueur`)
    REFERENCES `mi-1000_shazamm`.`joueur` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mi-1000_shazamm`.`manche`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mi-1000_shazamm`.`manche` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `mi-1000_shazamm`.`manche` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_partie` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `id_partie_idx` (`id_partie` ASC) VISIBLE,
  CONSTRAINT `id_partie`
    FOREIGN KEY (`id_partie`)
    REFERENCES `mi-1000_shazamm`.`partie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mi-1000_shazamm`.`tour`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mi-1000_shazamm`.`tour` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `mi-1000_shazamm`.`tour` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_manche` INT UNSIGNED NOT NULL,
  `position_mur_flammes` INT NULL,
  `position_joueur1` INT NULL,
  `position_joueur2` INT NULL,
  `mise_joueur1` INT NULL,
  `mise_joueur2` INT NULL,
  `puissance_joueur1` INT NULL,
  `puissance_joueur2` INT NULL,
  `numero_tour` INT NULL,
  `date` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `id_manche_idx` (`id_manche` ASC) VISIBLE,
  CONSTRAINT `id_manche`
    FOREIGN KEY (`id_manche`)
    REFERENCES `mi-1000_shazamm`.`manche` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mi-1000_shazamm`.`carte`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mi-1000_shazamm`.`carte` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `mi-1000_shazamm`.`carte` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_tour` INT UNSIGNED NOT NULL,
  `id_joueur` INT UNSIGNED NOT NULL,
  `numero_carte` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `id_tour_idx` (`id_tour` ASC) VISIBLE,
  INDEX `id_joueur_idx` (`id_joueur` ASC) VISIBLE,
  CONSTRAINT `id_tour`
    FOREIGN KEY (`id_tour`)
    REFERENCES `mi-1000_shazamm`.`tour` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_joueur`
    FOREIGN KEY (`id_joueur`)
    REFERENCES `mi-1000_shazamm`.`joueur` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mi-1000_shazamm`.`couleur`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mi-1000_shazamm`.`couleur` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `mi-1000_shazamm`.`couleur` (
  `id_partie` INT UNSIGNED NOT NULL,
  `couleur_j1` VARCHAR(10) NULL,
  `couleur_j2` VARCHAR(10) NULL,
  INDEX `id_partie_idx` (`id_partie` ASC) VISIBLE,
  CONSTRAINT `id_partie_actuelle`
    FOREIGN KEY (`id_partie`)
    REFERENCES `mi-1000_shazamm`.`partie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
