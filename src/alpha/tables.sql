/**
 * Author:  Duskmourne
 * Created: 08 8, 20
 */

DROP TABLE IF EXISTS `hospitalcharges`;
CREATE TABLE `hospitalcharges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chargenumber` varchar(45) NOT NULL DEFAULT '',
  `chargetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `chargeto` varchar(100) NOT NULL DEFAULT '',
  `chargenotes` varchar(191) NOT NULL DEFAULT '',
  `invoicenumber` varchar(45) NOT NULL DEFAULT '',
  `billnumber` varchar(45) NOT NULL DEFAULT '',
  `ornumber` varchar(45) NOT NULL DEFAULT '',
  `description` varchar(191) NOT NULL DEFAULT '',
  `chargefacility` varchar(100) NOT NULL DEFAULT '',
  `chargetype` varchar(100) NOT NULL DEFAULT '',
  `totalgross` double NOT NULL DEFAULT '0',
  `vatsales` double NOT NULL DEFAULT '0',
  `nonvatsales` double NOT NULL DEFAULT '0',
  `zeroratedsales` double NOT NULL DEFAULT '0',
  `inputvat` double NOT NULL DEFAULT '0',
  `lessvat` double NOT NULL DEFAULT '0',
  `scdiscount` double NOT NULL DEFAULT '0',
  `scid` varchar(100) NOT NULL DEFAULT '',
  `pwddiscount` double NOT NULL DEFAULT '0',
  `pwdid` varchar(100) NOT NULL DEFAULT '',
  `careto` varchar(100) NOT NULL DEFAULT '',
  `empdiscount` double NOT NULL DEFAULT '0',
  `empid` varchar(100) NOT NULL DEFAULT '',
  `otdiscount` double NOT NULL DEFAULT '0',
  `otdiscountremarks` varchar(100) NOT NULL DEFAULT '',
  `netsales` double NOT NULL DEFAULT '0',
  `paidamount` double NOT NULL DEFAULT '0',
  `paymentreferrence` varchar(100) NOT NULL DEFAULT '',
  `cashier` varchar(100) NOT NULL DEFAULT '',
  `user` varchar(100) NOT NULL DEFAULT '',
  `cancelled` varchar(100) NOT NULL DEFAULT '',
  `canceltime` timestamp NULL DEFAULT NULL,
  `cancelremarks` varchar(191) NOT NULL DEFAULT '',
  `voided` varchar(100) NOT NULL DEFAULT '',
  `voidtime` timestamp NULL DEFAULT NULL,
  `voidremarks` varchar(191) NOT NULL DEFAULT '',
  `cashierid` int(20) NOT NULL DEFAULT '0',
  `remarks` varchar(191) NOT NULL DEFAULT '',
  `notes` varchar(191) NOT NULL DEFAULT '',
  `recordtable` varchar(191) NOT NULL DEFAULT '',
  `recordtableid` int(20) NOT NULL DEFAULT '0',
  `user_id` int(20) NOT NULL DEFAULT '0',
  `patient_id` int(20) NOT NULL DEFAULT '0',
  `opt0` varchar(191) NOT NULL DEFAULT '',
  `opt1` varchar(191) NOT NULL DEFAULT '',
  `opt2` varchar(191) NOT NULL DEFAULT '',
  `opt3` varchar(191) NOT NULL DEFAULT '',
  `opt4` varchar(191) NOT NULL DEFAULT '',
  `opt5` varchar(191) NOT NULL DEFAULT '',
  `opt6` varchar(191) NOT NULL DEFAULT '',
  `opt7` varchar(191) NOT NULL DEFAULT '',
  `opt8` varchar(191) NOT NULL DEFAULT '',
  `opt9` varchar(191) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* ADMISSION COUNTERS */
CREATE TABLE `admissioncounters` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `recorddate` date NOT NULL,
  `currentadmission` int(11) NOT NULL DEFAULT '0',
  `newadmission` int(11) NOT NULL DEFAULT '0',
  `discharged` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
