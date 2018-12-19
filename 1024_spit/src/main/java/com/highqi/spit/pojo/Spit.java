package com.highqi.spit.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 陈建春
 * @Date: 2018-12-18 20:16
 * @Description: mongoDB Spit表对应的实体类
 */
@Data
public class Spit implements Serializable {

    @Id
    private String _id;

    private String content;
    private Date publishtime;
    private String userid;
    private String nickname;
    private Integer visits;
    private Integer thumbup;
    private Integer share;
    private Integer comment;
    private String state;
    private String parentid;
}
