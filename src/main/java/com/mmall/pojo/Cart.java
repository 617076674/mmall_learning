package com.mmall.pojo;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    private Date createTime;

    private Date updateTime;
}