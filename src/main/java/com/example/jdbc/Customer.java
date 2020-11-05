package com.example.jdbc;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {

	private static final long serialVersionUID = -874794992964046149L;

	private Long customer_id;

	private String ctry_cd;
	private String phone_no;
	private String phone_type;
	private String email_ad;

	private String customer_guid;

	private Date update_token_expiry_ts;

	private String update_token;

	private Date init_insert_ts;

	private Date last_mdfy_ts;

	private String last_mdfy_prog;
	private String last_mdfy_user;

	/**
	 * @return the customer_id
	 */
	public Long getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id
	 *            the customer_id to set
	 */
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * @return the ctry_cd
	 */
	public String getCtry_cd() {
		return ctry_cd;
	}

	/**
	 * @param ctry_cd
	 *            the ctry_cd to set
	 */
	public void setCtry_cd(String ctry_cd) {
		this.ctry_cd = ctry_cd;
	}

	/**
	 * @return the phone_no
	 */
	public String getPhone_no() {
		return phone_no;
	}

	/**
	 * @param phone_no
	 *            the phone_no to set
	 */
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	/**
	 * @return the phone_type
	 */
	public String getPhone_type() {
		return phone_type;
	}

	/**
	 * @param phone_type
	 *            the phone_type to set
	 */
	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
	}

	/**
	 * @return the email_ad
	 */
	public String getEmail_ad() {
		return email_ad;
	}

	/**
	 * @param email_ad
	 *            the email_ad to set
	 */
	public void setEmail_ad(String email_ad) {
		this.email_ad = email_ad;
	}

	/**
	 * @return the customer_guid
	 */
	public String getCustomer_guid() {
		return customer_guid;
	}

	/**
	 * @param customer_guid
	 *            the customer_guid to set
	 */
	public void setCustomer_guid(String customer_guid) {
		this.customer_guid = customer_guid;
	}

	/**
	 * @return the update_token_expiry_ts
	 */
	public Date getUpdate_token_expiry_ts() {
		return update_token_expiry_ts;
	}

	/**
	 * @param update_token_expiry_ts
	 *            the update_token_expiry_ts to set
	 */
	public void setUpdate_token_expiry_ts(Date update_token_expiry_ts) {
		this.update_token_expiry_ts = update_token_expiry_ts;
	}

	/**
	 * @return the update_token
	 */
	public String getUpdate_token() {
		return update_token;
	}

	/**
	 * @param update_token
	 *            the update_token to set
	 */
	public void setUpdate_token(String update_token) {
		this.update_token = update_token;
	}

	/**
	 * @return the init_insert_ts
	 */
	public Date getInit_insert_ts() {
		return init_insert_ts;
	}

	/**
	 * @param init_insert_ts
	 *            the init_insert_ts to set
	 */
	public void setInit_insert_ts(Date init_insert_ts) {
		this.init_insert_ts = init_insert_ts;
	}

	/**
	 * @return the last_mdfy_ts
	 */
	public Date getLast_mdfy_ts() {
		return last_mdfy_ts;
	}

	/**
	 * @param last_mdfy_ts
	 *            the last_mdfy_ts to set
	 */
	public void setLast_mdfy_ts(Date last_mdfy_ts) {
		this.last_mdfy_ts = last_mdfy_ts;
	}

	/**
	 * @return the last_mdfy_prog
	 */
	public String getLast_mdfy_prog() {
		return last_mdfy_prog;
	}

	/**
	 * @param last_mdfy_prog
	 *            the last_mdfy_prog to set
	 */
	public void setLast_mdfy_prog(String last_mdfy_prog) {
		this.last_mdfy_prog = last_mdfy_prog;
	}

	/**
	 * @return the last_mdfy_user
	 */
	public String getLast_mdfy_user() {
		return last_mdfy_user;
	}

	/**
	 * @param last_mdfy_user
	 *            the last_mdfy_user to set
	 */
	public void setLast_mdfy_user(String last_mdfy_user) {
		this.last_mdfy_user = last_mdfy_user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", ctry_cd=" + ctry_cd + ", phone_no=" + phone_no
				+ ", phone_type=" + phone_type + ", email_ad=" + email_ad + ", customer_guid=" + customer_guid
				+ ", update_token_expiry_ts=" + update_token_expiry_ts + ", update_token=" + update_token
				+ ", init_insert_ts=" + init_insert_ts + ", last_mdfy_ts=" + last_mdfy_ts + ", last_mdfy_prog="
				+ last_mdfy_prog + ", last_mdfy_user=" + last_mdfy_user + "]";
	}
}
