package com.highqi.user.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 21:39:23
 * @Description: 
 */
@Data
@Entity
@Table(name="tb_admin")
public class Admin implements Serializable{

	@Id
	private String id;//ID


	private String loginname;//登陆名称
	private String password;//密码
	private String state;//状态

}
