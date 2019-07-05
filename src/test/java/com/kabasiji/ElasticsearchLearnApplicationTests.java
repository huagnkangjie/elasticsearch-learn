package com.kabasiji;


import com.alibaba.fastjson.JSON;
import com.kabasiji.model.Article;
import com.kabasiji.model.Author;
import com.kabasiji.model.Tutorial;
import com.kabasiji.repository.ArticleSearchRepository;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticsearchLearnApplication.class)
public class ElasticsearchLearnApplicationTests {

     private Logger log = LoggerFactory.getLogger(getClass());

     @Autowired
     private ArticleSearchRepository articleSearchRepository;

     @Test
     public void contextLoads() {

     }


     @Test
     public void testSaveArticleIndex(){
          Author author=new Author();
          author.setId(1L);
          author.setName("罗家英");
          author.setRemark("java developer2");

          Tutorial tutorial=new Tutorial();
          tutorial.setId(1L);
          tutorial.setName("elastic search2");

          Article article =new Article();
          article.setId(1L);
          article.setTitle("无印良品 MUJI 基础润肤化妆水" );
          article.setAbstracts("springboot integreate elasticsearch is very easy " );
          article.setTutorial(tutorial);
          article.setAuthor(author);
          article.setContent("无印良品 MUJI 基础润肤化妆水 高保湿型 200ml" );
          article.setPostTime(new Date());
          article.setClickCount(1L);
          articleSearchRepository.save(article);

          Article article2 =new Article();
          article2.setId(2L);
          article2.setTitle("荣耀 V10 尊享版" );
          article2.setAbstracts("springboot integreate elasticsearch is very easy " );
          article2.setTutorial(tutorial);
          article2.setAuthor(author);
          article2.setContent("荣耀 V10 尊享版 6GB+128GB 幻夜黑 移动联通电信4G全面屏游戏手机 双卡双待" );
          article2.setPostTime(new Date());
          article2.setClickCount(1L);
          articleSearchRepository.save(article2);

          Article article3 =new Article();
          article3.setId(3L);
          article3.setTitle("资生堂(SHISEIDO) 尿素红罐护手霜" );
          article3.setAbstracts("springboot integreate elasticsearch is very easy " );
          article3.setTutorial(tutorial);
          article3.setAuthor(author);
          article3.setContent("日本进口 资生堂(SHISEIDO) 尿素红罐护手霜 100g/罐 男女通用 深层滋养 改善粗糙" );
          article3.setPostTime(new Date());
          article3.setClickCount(1L);
          articleSearchRepository.save(article2);
     }

     @Test
     public void getAll() {
          List<Article> list = new ArrayList<>();
          articleSearchRepository.findAll().forEach(list::add);
          System.out.println("索引长度："+list.size());
          list.forEach(print -> {
               System.out.println(JSON.toJSON(print));
          });
     }

     @Test
     public void getById() {
          log.info("【根据ID查询内容】：{}", JSON.toJSONString(articleSearchRepository.findOne(1L)));
     }


     @Test
     public void query(){
          String queryString="联通良品";//搜索关键字
          QueryStringQueryBuilder builder=new QueryStringQueryBuilder(queryString);
          Iterable<Article> searchResult = articleSearchRepository.search(builder);
          Iterator<Article> iterator = searchResult.iterator();
          while(iterator.hasNext()){
               System.out.println("【根据关键字搜索内容】 " + iterator.next());
          }
     }

     @Test
     public void queryHit(){

     }

     @Test
     public void deleteAll() {
          articleSearchRepository.deleteAll();
     }


}
