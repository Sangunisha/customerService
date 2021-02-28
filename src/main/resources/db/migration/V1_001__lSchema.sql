/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `bank_customer`;

USE `bank_customer`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` varchar(191) NOT NULL,
  `created_ts` bigint(20) DEFAULT NULL,
  `modified_ts` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `balance_amount` double DEFAULT NULL,
  `branch_id` varchar(255) DEFAULT NULL,
  `customer_id` varchar(191) DEFAULT NULL,
  `type_specific_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKilfrl5elaw9v224qm7qj5elcb` (`customer_id`),
  CONSTRAINT `FKilfrl5elaw9v224qm7qj5elcb` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_role` varchar(31) NOT NULL,
  `id` varchar(191) NOT NULL,
  `created_ts` bigint(20) DEFAULT NULL,
  `modified_ts` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `address` text,
  `age` int(11) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email_id` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
