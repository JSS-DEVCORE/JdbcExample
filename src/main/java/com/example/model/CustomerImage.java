package com.example.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CustomerImage implements Serializable {

	private static final long serialVersionUID = 7574605056281552193L;

	private Long customer_image_id;
	private Long customer_id;

	private String image_type;
	private String image;

	private Date last_mdfy_ts;
	private String last_mdfy_prog;
	private String last_mdfy_user;
}