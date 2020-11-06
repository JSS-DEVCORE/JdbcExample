package com.example.model;

/**
 * @project JdbcExample - Country
 * @author User
 * @date Nov 6, 2020
 */
public class Country {
	
	private Long country_id;
	private String country_cd;
	private String country_nm;

	/**
	 * @return the country_id
	 */
	public Long getCountry_id() {
		return country_id;
	}

	/**
	 * @param country_id
	 *            the country_id to set
	 */
	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	/**
	 * @return the country_cd
	 */
	public String getCountry_cd() {
		return country_cd;
	}

	/**
	 * @param country_cd
	 *            the country_cd to set
	 */
	public void setCountry_cd(String country_cd) {
		this.country_cd = country_cd;
	}

	/**
	 * @return the country_nm
	 */
	public String getCountry_nm() {
		return country_nm;
	}

	/**
	 * @param country_nm
	 *            the country_nm to set
	 */
	public void setCountry_nm(String country_nm) {
		this.country_nm = country_nm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Country [country_id=" + country_id + ", country_cd=" + country_cd + ", country_nm=" + country_nm + "]";
	}
}