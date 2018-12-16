package com.highqi.article.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: 陈建春
 * @Date: 
 * @Description: 
 */
@Data
@Entity
@Table(name="tb_channel")
public class Channel implements Serializable{

	@Id
	private String id;//ID


	private String name;//频道名称
	private String state;//状态

}
