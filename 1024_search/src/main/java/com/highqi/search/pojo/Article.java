package com.highqi.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @Author: 陈建春
 * @Date: 2018-12-20 20:05
 * @Description: 分词对象实体类
 */
@Data
@Document(indexName = "1024_article", type = "article")
public class Article {

    @Id
    private String id;


    //是否索引.  ----是否可以被搜索到              index = true,
    //是否分词.  ----类似于模糊查询是否开启         analyzer = "ik_max_word",
    //是否存储.  ----可以理解成是否在页面上展示     searchAnalyzer = "ik_max_word"，
    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    private String state;
}
