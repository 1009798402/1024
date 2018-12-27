package com.highqi.base.service;

import com.highqi.base.pojo.Label;
import com.highqi.base.repository.LabelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 16:08
 * @Description: Label的Service层
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class LabelService {

    @Resource
    private LabelRepository repository;

    public void save(Label label) {
        repository.save(label);
    }

    public List<Label> getAll() {
        return repository.findAll();
    }

    public Label getLabelById(String labelId) {
        return repository.findById(labelId).get();
    }

    public void deleteLabelById(String labelId) {
        repository.deleteById(labelId);
    }

    public Page<Label> pageSearchLabel(Integer page, Integer size, Label label) {

        //分页对象
        Pageable pageable = PageRequest.of(page - 1, size);

        return repository.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                //创建一个存放条件对象的list
                List<Predicate> list = new ArrayList<>();

                //设置条件
                if (!StringUtils.isEmpty(label.getLabelname())) {
                    Expression<String> labelName = root.get("labelname").as(String.class);
                    Predicate predicate = criteriaBuilder.like(labelName, "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (!StringUtils.isEmpty(label.getState())) {
                    Expression<String> state = root.get("state").as(String.class);
                    Predicate predicate = criteriaBuilder.equal(state, label.getState());
                    list.add(predicate);
                }

                Predicate[] predicates = list.toArray(new Predicate[list.size()]);

                return criteriaBuilder.and(predicates);
            }
        }, pageable);
    }
}
