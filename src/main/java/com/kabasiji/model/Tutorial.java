package com.kabasiji.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 教程
 * @author huang_kangjie
 * @date 2019-07-04 10:47
 * @since 1.0.3
 **/
@Data
public class Tutorial implements Serializable {

     private Long id;
     private String name;//教程名称

}
