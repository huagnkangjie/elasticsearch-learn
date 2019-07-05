package com.kabasiji;

import com.alibaba.fastjson.JSON;
import com.kabasiji.document.ProductDocument;
import com.kabasiji.document.ProductDocumentBuilder;
import com.kabasiji.page.Page;
import com.kabasiji.service.EsSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EssearchApplicationTests {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EsSearchService esSearchService;

    @Test
    public void save() {
        /**
         * 这里用到了ik分词器，所以需要在es安装ik的分词器
         * 安装步骤：
         * 1、下载https://github.com/medcl/elasticsearch-analysis-ik/releases
         * 2、直接下载zip包
         * 3、把zip里面的文件全部拷贝到es所在目录下的/plugins/ik
         * 4、修改plugin-descriptor.properties文件，将其中的java版本，以及es版本号均改为你所使用的版本号
         */

        log.info("【创建索引前的数据条数】：{}",esSearchService.getAll().size());

        ProductDocument productDocument = ProductDocumentBuilder.create()
                .addId(System.currentTimeMillis() + "01")
                .addProductName("无印良品 MUJI 基础润肤化妆水")
                .addProductDesc("无印良品 MUJI 基础润肤化妆水 高保湿型 200ml")
                .addCreateTime(new Date()).addUpdateTime(new Date())
                .builder();

        ProductDocument productDocument1 = ProductDocumentBuilder.create()
                .addId(System.currentTimeMillis() + "02")
                .addProductName("荣耀 V10 尊享版")
                .addProductDesc("荣耀 V10 尊享版 6GB+128GB 幻夜黑 移动联通电信4G全面屏游戏手机 双卡双待")
                .addCreateTime(new Date()).addUpdateTime(new Date())
                .builder();

        ProductDocument productDocument2 = ProductDocumentBuilder.create()
                .addId(System.currentTimeMillis() + "03")
                .addProductName("资生堂(SHISEIDO) 尿素红罐护手霜")
                .addProductDesc("日本进口 资生堂(SHISEIDO) 尿素红罐护手霜 100g/罐 男女通用 深层滋养 改善粗糙")
                .addCreateTime(new Date()).addUpdateTime(new Date())
                .builder();

        esSearchService.save(productDocument,productDocument1,productDocument2);

        log.info("【创建索引ID】:{},{},{}",productDocument.getId(),productDocument1.getId(),productDocument2.getId());
        log.info("【创建索引后的数据条数】：{}",esSearchService.getAll().size());
    }

    @Test
    public void getAll(){
        esSearchService.getAll().parallelStream()
                .map(JSON::toJSONString)
                .forEach(System.out::println);
    }

    @Test
    public void deleteAll() {
        esSearchService.deleteAll();
    }

    @Test
    public void getById() {
        log.info("【根据ID查询内容】：{}", JSON.toJSONString(esSearchService.getById("156229238704702")));
    }

    @Test
    public void query() {
        log.info("【根据关键字搜索内容】：{}", JSON.toJSONString(esSearchService.query("荣耀",ProductDocument.class)));
    }

    @Test
    public void queryHit() {

        String keyword = "联通尿素";
        String indexName = "projectname-01";

        List<Map<String,Object>> searchHits = esSearchService.queryHit(keyword,indexName,"productName","productDesc");
        log.info("【根据关键字搜索内容，命中部分高亮，返回长度】：{}", searchHits.size());
        log.info("【根据关键字搜索内容，命中部分高亮，返回内容】：{}", JSON.toJSONString(searchHits));
        //[{"highlight":{"productDesc":"<span style='color:red'>无印良品</span> MUJI 基础润肤化妆水 高保湿型 200ml","productName":"<span style='color:red'>无印良品</span> MUJI 基础润肤化妆水"},"source":{"productDesc":"无印良品 MUJI 基础润肤化妆水 高保湿型 200ml","createTime":1544755966204,"updateTime":1544755966204,"id":"154475596620401","productName":"无印良品 MUJI 基础润肤化妆水"}},{"highlight":{"productDesc":"<span style='color:red'>荣耀</span> V10 尊享版 6GB+128GB 幻夜黑 移动联通电信4G全面屏游戏手机 双卡双待","productName":"<span style='color:red'>荣耀</span> V10 尊享版"},"source":{"productDesc":"荣耀 V10 尊享版 6GB+128GB 幻夜黑 移动联通电信4G全面屏游戏手机 双卡双待","createTime":1544755966204,"updateTime":1544755966204,"id":"154475596620402","productName":"荣耀 V10 尊享版"}}]
    }

    @Test
    public void queryHitByPage() {

        String keyword = "联通尿素";
        String indexName = "orders3";

        Page<Map<String,Object>> searchHits = esSearchService.queryHitByPage(1,1,keyword,indexName,"productName","productDesc");
        log.info("【根据关键字搜索内容，命中部分高亮，返回长度】：{}", searchHits.getList().size());
        log.info("【分页查询，根据关键字搜索内容，命中部分高亮，返回内容】：{}", JSON.toJSONString(searchHits));
        //[{"highlight":{"productDesc":"<span style='color:red'>无印良品</span> MUJI 基础润肤化妆水 高保湿型 200ml","productName":"<span style='color:red'>无印良品</span> MUJI 基础润肤化妆水"},"source":{"productDesc":"无印良品 MUJI 基础润肤化妆水 高保湿型 200ml","createTime":1544755966204,"updateTime":1544755966204,"id":"154475596620401","productName":"无印良品 MUJI 基础润肤化妆水"}},{"highlight":{"productDesc":"<span style='color:red'>荣耀</span> V10 尊享版 6GB+128GB 幻夜黑 移动联通电信4G全面屏游戏手机 双卡双待","productName":"<span style='color:red'>荣耀</span> V10 尊享版"},"source":{"productDesc":"荣耀 V10 尊享版 6GB+128GB 幻夜黑 移动联通电信4G全面屏游戏手机 双卡双待","createTime":1544755966204,"updateTime":1544755966204,"id":"154475596620402","productName":"荣耀 V10 尊享版"}}]
    }

    @Test
    public void deleteIndex() {
        log.info("【删除索引库】");
        esSearchService.deleteIndex("orders3");
    }

}

