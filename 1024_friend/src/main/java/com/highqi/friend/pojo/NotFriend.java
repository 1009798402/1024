package com.highqi.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 20:55
 * @Description:
 */
@Data
@Entity
@IdClass(NotFriend.class)
@Table(name = "tb_notfriend")
public class NotFriend implements Serializable {

    @Id
    private String userid;

    @Id
    private String friendid;

}
