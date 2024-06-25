CREATE DATABASE IF NOT EXISTS MEDIQUANTA;
USE MEDIQUANTA;

CREATE TABLE MEMBER (
    MEMBER_IDX     			BIGINT AUTO_INCREMENT PRIMARY KEY,
    MEMBER_ID      			VARCHAR(50) NOT NULL,
    PASSWD         			VARCHAR(200) NOT NULL,
    NICKNAME       			VARCHAR(50) NOT NULL,
    PROFILE_ORIGINAL_NAME	VARCHAR(200) NOT NULL,
    PROFILE_UUID			VARCHAR(200) NOT NULL,
    EMAIL          			VARCHAR(200) NOT NULL,
    BIRTH          			TIMESTAMP NOT NULL,
    GENDER         			VARCHAR(2) NOT NULL,
    ZIPCODE					VARCHAR(10) NOT NULL,
	ROAD_ADDRESS 			VARCHAR(200) NOT NULL,
	LAND_ADDRESS 			VARCHAR(200) NULL,
	ETC_ADDRESS 			VARCHAR(200) NULL,
    ROLE					VARCHAR(10) NOT NULL,
    ACTIVE_YN      			VARCHAR(1) NOT NULL,
    CREATED_AT     			TIMESTAMP NOT NULL,
    LAST_LOGIN     			TIMESTAMP NOT NULL
);

CREATE TABLE HOSPITAL (
	HOSPITAL_ID			BIGINT AUTO_INCREMENT PRIMARY KEY,
	HOSPITAL_NAME		VARCHAR(200) NOT NULL,
	TYPE				VARCHAR(50) NULL,
	SIDOCDNM			VARCHAR(50) NOT NULL,
	SGGUCDNM			VARCHAR(50) NOT NULL,
	ADDRESS				VARCHAR(500) NOT NULL,
	PHONE				VARCHAR(20) NULL,
	LATITUDE			DOUBLE NULL,
	LONGITUDE			DOUBLE NULL,
	HOSPITAL_URL		VARCHAR(255) NULL,
	CREATED_AT			TIMESTAMP NOT NULL,
	UPDATED_AT			TIMESTAMP NULL
);