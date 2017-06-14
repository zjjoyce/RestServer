-- -----------------------------------------------------
-- Schema ocmanager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ocmanager` DEFAULT CHARACTER SET utf8 ;
USE `ocmanager` ;

-- -----------------------------------------------------
-- Table `ocmanager`.`tenants`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`tenants` (
  `id` VARCHAR(64) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `parentId` VARCHAR(64) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`users` (
  `id` VARCHAR(64) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`services` (
  `id` VARCHAR(64) NOT NULL,
  `servicename` VARCHAR(64) NOT NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`servicename`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`roles` (
  `id` VARCHAR(64) NOT NULL,
  `rolename` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`service_instances`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`service_instances` (
  `id` VARCHAR(64) NOT NULL,
  `instanceName` VARCHAR(64) NOT NULL,
  `tenantId` VARCHAR(64) NOT NULL,
  `serviceTypeId` VARCHAR(64) NULL,
  `serviceTypeName` VARCHAR(64) NOT NULL,
  `quota` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_service_instances_tenants1_idx` (`tenantId` ASC),
  INDEX `fk_service_instances_services1_idx` (`serviceTypeName` ASC),
  CONSTRAINT `fk_service_instances_tenants1`
    FOREIGN KEY (`tenantId`)
    REFERENCES `ocmanager`.`tenants` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_service_instances_services1`
    FOREIGN KEY (`serviceTypeName`)
    REFERENCES `ocmanager`.`services` (`servicename`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`tenants_users_roles_assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`tenants_users_roles_assignment` (
  `tenant_id` VARCHAR(64) NOT NULL,
  `user_id` VARCHAR(64) NOT NULL,
  `role_id` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`tenant_id`, `user_id`),
  INDEX `fk_tenants_users_assignment_users1_idx` (`user_id` ASC),
  INDEX `fk_tenants_users_assignment_tenants1_idx` (`tenant_id` ASC),
  INDEX `fk_tenants_users_assignment_roles1_idx` (`role_id` ASC),
  CONSTRAINT `fk_tenants_users_assignment_tenants1`
    FOREIGN KEY (`tenant_id`)
    REFERENCES `ocmanager`.`tenants` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_tenants_users_assignment_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocmanager`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_tenants_users_assignment_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocmanager`.`roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ocmanager`.`services_roles_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ocmanager`.`services_roles_permission` (
  `service_id` VARCHAR(64) NOT NULL,
  `role_id` VARCHAR(64) NOT NULL,
  `permission` TEXT NULL,
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
