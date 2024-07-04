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

CREATE TABLE PHARMACY (
	PHARMACY_ID			BIGINT AUTO_INCREMENT PRIMARY KEY,
	PHARMACY_NAME		VARCHAR(200) NOT NULL,
	TYPE				VARCHAR(50) NULL,
	SIDOCDNM			VARCHAR(50) NOT NULL,
	SGGUCDNM			VARCHAR(50) NOT NULL,
	ADDRESS				VARCHAR(500) NOT NULL,
	PHONE				VARCHAR(20) NULL,
	LATITUDE			DOUBLE NULL,
	LONGITUDE			DOUBLE NULL,
	CREATED_AT			TIMESTAMP NOT NULL,
	UPDATED_AT			TIMESTAMP NULL
);

-- 위도, 경도로 근처 장소 조회해오는 쿼리
SELECT *
FROM (
    SELECT
        HOSPITAL.*,
        6371 * ACOS(
            COS(RADIANS(현재 위도)) * 
            COS(RADIANS(HOSPITAL.LATITUDE)) * 
            COS(RADIANS(HOSPITAL.LONGITUDE) - RADIANS(현재 경도)) + 
            SIN(RADIANS(현재 위도)) * 
            SIN(RADIANS(HOSPITAL.LATITUDE))
        ) AS DISTANCE
    FROM HOSPITAL
) AS DATA
WHERE TYPE = "병원" AND SIDOCDNM = "서울" AND DATA.DISTANCE < 5
ORDER BY DISTANCE
LIMIT 3;

-- 병원 북마크 테이블
CREATE TABLE HOSPITAL_BOOKMARK (
    HOSPITAL_BOOKMARK_ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
	MEMBER_IDX          		BIGINT NOT NULL,
    HOSPITAL_ID         		BIGINT,
    FOREIGN KEY (MEMBER_IDX) REFERENCES MEMBER (MEMBER_IDX) ON DELETE CASCADE
);

-- 약국 북마크 테이블
CREATE TABLE PHARMACY_BOOKMARK (
    PHARMACY_BOOKMARK_ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    MEMBER_IDX          		BIGINT NOT NULL,
    PHARMACY_ID         		BIGINT,
    FOREIGN KEY (MEMBER_IDX) REFERENCES MEMBER (MEMBER_IDX) ON DELETE CASCADE
);

-- 커뮤니티 테이블
CREATE TABLE COMMUNITY (
	COMMUNITY_ID			BIGINT AUTO_INCREMENT PRIMARY KEY,
	COMMUNITY_NAME			VARCHAR(50) NOT NULL,
	DESCRIPTION				VARCHAR(100) NULL,
	PURPOSE					VARCHAR(100) NULL,
	ACTIVE_YN				VARCHAR(1) NOT NULL,
	TOTAL_VIEW_COUNT		BIGINT NULL,
	TOTAL_MEMBER_COUNT		BIGINT NULL,
	TOTAL_POST_COUNT		BIGINT NULL,
	CREATED_AT				TIMESTAMP NOT NULL,
	UPDATED_AT				TIMESTAMP NULL
);