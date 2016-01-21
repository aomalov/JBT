# JBT project: Coupon Dibi

Phase 1 represents the storage and business rules layer of the project

> Famous ![Groupon](https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Groupon_Logo.svg/250px-Groupon_Logo.svg.png "Groupon") business model is somehow inspiring the tutorial project as a whole. 
> Scalable multilayer Web/DB application serves as B2C portal for **Companies** advertising their services and products through a reasonable price reduction 
> **Customers** register with the app and benefit from coupons on sale.
> **App hosting** monetizes the marketing fee giving ads to the companies and provideng them with a broader reach and possible leads generator



## Technical description

  - `Derby Db` is used to store data on: Companies, Customers, Coupons, Companies issuing coupons, Customers buying coupons
  - **Security** is enforced at the authentication layer: passwords are stored with hash, some salt is provided. Neither password could be found in open text, nor hash function could be easily refactored to get plain text passwords from stolen hashes (see [Security] for more details)
  - Data Transfer Objects `DTO` used for entities
  - Data Access Objects `DAO` used for conveying DTO to storage facility
  - `JDBC` used as middleware db driver, actual data storage can be replaced at the DAO layer by:
    - file system
    - NoSql db 
    - whatever ...
  - Threadsafe ``Connection pool`` is provided for DAO activities in order to elevate the storage througput 
  - `Facade` classes encompass business logic at the moment of data storage and retrieval. They encapsulate DAO and DTO to allow for an easy API - create , read, update and delete (`CRUD`)
  - Single entry point to the **Coupon Dibi** is the *CouponSystem* singleton. Login gives successful user a facade and a connection pool of 20 parallel sessions to the database.
  - All facade methods throw single type of Exception to provide for unified error handling at the presentation layer
  - A background routine acompanies the *CouponSystem*. A separate thread cleans up the database, safely removing the outdated coupons
  - `JUnit 4` is used to automate granular tests: get asserted results and catch exceptions if appropriate 
 
  ### Installation and Testing

```sh
launch DerbyDB database
configure "defDriverName", "defDbUrl" in "ConnectionPool.java" 
run "DBCreator.java" to initialize the CouponDB database
run "TestCouponSystem.java" to populate the database and perform end-to-end testing
discover multithread connection pool test and unit tests  
```
