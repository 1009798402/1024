package com.highqi.spit.service;

import com.highqi.spit.constant.SpitConstant;
import com.highqi.spit.dao.SpitDao;
import com.highqi.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: 陈建春
 * @Date: 2018-12-18 20:15
 * @Description:
 */
@Service
public class SpitService {

    @Resource
    private SpitDao spitDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        Optional<Spit> spit = spitDao.findById(id);
        return spit.isPresent() ? spit.get() : null;
    }

    public void save(Spit spit) {

        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        String parentId = spit.getParentid();
        if (!StringUtils.isEmpty(parentId)) {

            mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where("_id").is(parentId)), new Update().inc("comment", 1), "spit");
        }
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentid, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageable);
    }

    /**
     * 点赞方式1  与数据库交互2次  查询 修改
     */
    public Boolean doGood(String id, String userid) {

        //查询
        Optional<Spit> spitOptional = spitDao.findById(id);
        //如果存在
        if (spitOptional.isPresent()) {

            //查询这个人是否已经点过赞
            Integer isDoGoot = (Integer) redisTemplate.opsForValue().get(SpitConstant.SPIT_PREfix + id + "_" + userid);
            //没点过的话就+1
            if (isDoGoot == null) {
                Spit spit = spitOptional.get();
                Integer thumbup = spit.getThumbup();
                spit.setThumbup(thumbup == null ? 1 : thumbup + 1);
                spitDao.save(spit);
                redisTemplate.opsForValue().set(SpitConstant.SPIT_PREfix + id + "_" + userid, 1);
                return true;
            }
        }
        return false;
    }

    /**
     * 点赞方式2  与数据库交互2次  查询 修改
     */
    public Boolean doGood2(String id, String userid) {


        //查询这个人是否已经点过赞
        Integer isDoGoot = (Integer) redisTemplate.opsForValue().get(SpitConstant.SPIT_PREfix + id + "_" + userid);
        if (isDoGoot == null) {
            //点赞+1
            mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where("_id").is(id)), new Update().inc("thumbup", 1), "spit");

            redisTemplate.opsForValue().set(SpitConstant.SPIT_PREfix + id + "_" + userid, 1);
            return true;
        }
        return false;
    }
}
