package com.kabasiji.repository;

import com.kabasiji.model.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
/**
 * 泛型的参数分别是实体类型和主键类型
 * @author huang_kangjie
 * @date 2019-07-04 11:05
 * @since 1.0.3
 **/
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long>{

}
