
# Restful API

This project is a practice project for me to learn the Springboot Restful API


## Documentation (API Spec)
- [User](https://github.com/Ryandinulfatah12/springboot-contact-management/blob/main/documentation/user.md)
- [Address](https://github.com/Ryandinulfatah12/springboot-contact-management/blob/main/documentation/address.md)
- [Contacts](https://github.com/Ryandinulfatah12/springboot-contact-management/blob/main/documentation/contact.md)



## License

[MIT](https://choosealicense.com/licenses/mit/)


## Set up table

Users

```bash
CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `token` varchar(100) DEFAULT NULL,
  `token_expired_at` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

Address

```bash
CREATE TABLE `addresses` (
  `id` varchar(100) NOT NULL,
  `contact_id` varchar(100) NOT NULL,
  `street` varchar(200) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `province` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `postal_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_contacts_addresses` (`contact_id`),
  CONSTRAINT `fk_contacts_addresses` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
``` 

Contacts

```bash
CREATE TABLE `contacts` (
  `id` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_contacts` (`username`),
  CONSTRAINT `fk_user_contacts` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
``` 