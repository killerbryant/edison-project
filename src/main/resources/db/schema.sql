DROP TABLE IF EXISTS UserInfo;

CREATE TABLE UserInfo(
	CUSTOMER_ID varchar(10) NOT NULL primary key,
	CUSTOMER_NAME varchar(20) NOT NULL,
	CUSTOMER_GENDER char(1) NOT NULL,
	CUSTOMER_CITY varchar(20) NOT NULL,
	CUSTOMER_SIGN_TIME timestamp NOT NULL
);