package com.highqi.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 15:19
 * @Description: 标签实体类
 */
@Data
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {
    @Id
    private String id;
    /**
     * 标签名称 .
     */
    private String labelname;
    /**
     * 状态 .
     */
    private String state;
    /**
     * 使用数量 .
     */
    private Long count;
    /**
     * 推荐 .
     */
    private String recommend;
    /**
     * 关注数 .
     */
    private Long fans;

}
