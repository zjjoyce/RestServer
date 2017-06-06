-- -----------------------------------------------------
-- Schema ocmanager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ocmanager` DEFAULT CHARACTER SET utf8 ;
USE `ocmanager` ;

-- -----------------------------------------------------
-- Table `ocmanager`.`tenants`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`tenants` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `parent_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`services` (
  `id` INT NOT NULL,
  `servicename` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`roles` (
  `id` INT NOT NULL,
  `rolename` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`service_instances`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`service_instances` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tenant_id` INT NOT NULL,
  `service_type` INT NOT NULL,
  `quota` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_service_instances_tenants1_idx` (`tenant_id` ASC),
  INDEX `fk_service_instances_services1_idx` (`service_type` ASC),
  CONSTRAINT `fk_service_instances_tenants1`
    FOREIGN KEY (`tenant_id`)
    REFERENCES `ocmanager`.`tenants` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_service_instances_services1`
    FOREIGN KEY (`service_type`)
    REFERENCES `ocmanager`.`services` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`tenants_users_assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`tenants_users_assignment` (
  `tenant_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`tenant_id`, `user_id`),
  INDEX `fk_tenants_users_assignment_users1_idx` (`user_id` ASC),
  INDEX `fk_tenants_users_assignment_tenants1_idx` (`tenant_id` ASC),
  CONSTRAINT `fk_tenants_users_assignment_tenants1`
    FOREIGN KEY (`tenant_id`)
    REFERENCES `ocmanager`.`tenants` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_tenants_users_assignment_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocmanager`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`users_roles_assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`users_roles_assignment` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_users_roles_assignment_roles1_idx` (`role_id` ASC),
  INDEX `fk_users_roles_assignment_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_users_roles_assignment_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocmanager`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_users_roles_assignment_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocmanager`.`roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`services_roles_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`services_roles_permission` (
  `service_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  `read` TINYINT NOT NULL,
  `write` TINYINT NOT NULL,
  `excute` TINYINT NOT NULL,
  PRIMARY KEY (`service_id`, `role_id`),
  INDEX `fk_services_roles_permission_roles1_idx` (`role_id` ASC),
  INDEX `fk_services_roles_permission_services1_idx` (`service_id` ASC),
  CONSTRAINT `fk_services_roles_permission_services1`
    FOREIGN KEY (`service_id`)
    REFERENCES `ocmanager`.`services` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_services_roles_permission_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocmanager`.`roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;
