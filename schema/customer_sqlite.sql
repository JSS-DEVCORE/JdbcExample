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