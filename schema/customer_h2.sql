CREATE TABLE PUBLIC.customer (
	customer_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	ctry_cd char(2) NOT NULL,
	phone_no varchar(18) NOT NULL,
	phone_type varchar(12) NOT NULL,
	email_ad varchar(65) NOT NULL,
	customer_guid varchar(65) NOT NULL,
	update_token_expiry_ts TIMESTAMP NOT NULL,
	update_token varchar(65) NOT NULL,
	init_insert_ts TIMESTAMP NOT NULL,
	last_mdfy_ts TIMESTAMP NOT NULL,
	last_mdfy_user varchar(65) NOT NULL,
	last_mdfy_prog varchar(65) NOT NULL
);

CREATE TABLE PUBLIC.country (
	ctry_id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	ctry_cd char(2) NOT NULL,
	ctry_nm varchar(65) NOT NULL
);

INSERT INTO country (ctry_cd, ctry_nm) VALUES ('IN', 'INDIA');
INSERT INTO country (ctry_cd, ctry_nm) VALUES ('US', 'UNITED STATES OF AMERICA');
INSERT INTO country (ctry_cd, ctry_nm) VALUES ('JP', 'JAPAN');
INSERT INTO country (ctry_cd, ctry_nm) VALUES ('FR', 'FRANCE');
INSERT INTO country (ctry_cd, ctry_nm) VALUES ('GB', 'GREAT BRITAN');
INSERT INTO country (ctry_cd, ctry_nm) VALUES ('AE', 'UNITED ARAB EMIRATES');
INSERT INTO country (ctry_cd, ctry_nm) VALUES ('SG', 'SINGAPORE');

JDBC URL for H2 - jdbc:h2:tcp://localhost/./customer