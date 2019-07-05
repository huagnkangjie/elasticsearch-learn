package com.kabasiji.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huang_kangjie
 * @date 2019-07-04 10:49
 * @since 1.0.3
 **/
@Data
public class Author implements Serializable {
     /**
      * 作者id
      */
     private Long id;
     /**
      * 作者姓名
      */
     private String name;
     /**
      * 作者简介
      */
     private String remark;


}
