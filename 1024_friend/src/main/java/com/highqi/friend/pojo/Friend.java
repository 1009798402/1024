package com.highqi.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 20:52
 * @Description:
 */
@Data
@Entity
@IdClass(Friend.class)
@Table(name = "tb_friend")
public class Friend implements Serializable {

    @Id
    private String userid;

    @Id
    private String friendid;

    /** 0.表示userId单向like friendId   1.表示双向like. */
    private String islike;
}
