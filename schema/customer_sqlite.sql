CREATE TABLE customer (
	[customer_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	[ctry_cd] [char](2) NOT NULL,
	[customer_name] [text] NOT NULL,
	[phone_no] [varchar](18) NOT NULL,
	[phone_type] [varchar](12) NOT NULL,
	[email_ad] [varchar](65) NOT NULL,
	[customer_guid] [uniqueidentifier] NOT NULL,
	[init_insert_ts] [datetime] NOT NULL,
	[last_mdfy_ts] [datetime] NOT NULL,
	[last_mdfy_user] [varchar](65) NOT NULL,
	[last_mdfy_prog] [varchar](65) NOT NULL
);

CREATE TABLE customer_audit (
    [customer_audit_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	[customer_id] INTEGER NOT NULL,
	[ctry_cd] [char](2) NOT NULL,
	[customer_name] [text] NOT NULL,
	[phone_no] [varchar](18) NOT NULL,
	[phone_type] [varchar](12) NOT NULL,
	[email_ad] [varchar](65) NOT NULL,
	[customer_guid] [uniqueidentifier] NOT NULL,
	[init_insert_ts] [datetime] NOT NULL,
	[last_mdfy_ts] [datetime] NOT NULL,
	[last_mdfy_user] [varchar](65) NOT NULL,
	[last_mdfy_prog] [varchar](65) NOT NULL
);

CREATE TRIGGER update_customer BEFORE UPDATE ON customer 
  BEGIN
    INSERT INTO customer_audit (customer_id, customer_id, ctry_cd, customer_name, phone_no, phone_type, 
    	email_ad, customer_guid, init_insert_ts, last_mdfy_ts, last_mdfy_user, last_mdfy_prog)
		VALUES (old.customer_id, old.customer_id, old.ctry_cd, old.customer_name, old.phone_no, old.phone_type, 
				old.email_ad, old.customer_guid, old.init_insert_ts, old.last_mdfy_ts, 
					old.last_mdfy_user, old.last_mdfy_prog);
  END;
  
CREATE TRIGGER delete_customer BEFORE DELETE ON customer 
  BEGIN
    INSERT INTO customer_audit (customer_id, customer_id, ctry_cd, customer_name, phone_no, phone_type, 
    	email_ad, customer_guid, init_insert_ts, last_mdfy_ts, last_mdfy_user, last_mdfy_prog)
		VALUES (old.customer_id, old.customer_id, old.ctry_cd, old.customer_name, old.phone_no, old.phone_type, 
				old.email_ad, old.customer_guid, old.init_insert_ts, old.last_mdfy_ts, 
					old.last_mdfy_user, old.last_mdfy_prog);
  END;