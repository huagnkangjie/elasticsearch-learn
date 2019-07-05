package com.kabasiji.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huang_kangjie
 * @date 2019-07-04 10:50
 * @since 1.0.3
 **/
@Document(indexName="projectname-test-my1",type="article-my1",indexStoreType="fs",shards=5,replicas=1,
        refreshInterval="-1")
@Data
public class Article implements Serializable {
     private Long id;
     /**标题*/
     private String title;
     /**摘要*/
     private String abstracts;
     /**内容*/
     private String content;
     /**发表时间*/
     private Date postTime;
     /**点击率*/
     private Long clickCount;
     /**作者*/
     private Author author;
     /**所属教程*/
     private Tutorial tutorial;

}
