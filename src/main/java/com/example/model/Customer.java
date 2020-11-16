package com.example.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Customer implements Serializable {

	private static final long serialVersionUID = -874794992964046149L;

	private Long customer_id;

	private String ctry_cd;
	private String customer_name;
	private String phone_no;
	private String phone_type;
	private String email_ad;

	private String customer_guid;
	
	private String photo_id_bytes;
	
	private Date init_insert_ts;

	private Date last_mdfy_ts;
	private String last_mdfy_prog;
	private String last_mdfy_user;
}