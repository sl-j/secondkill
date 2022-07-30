package com.lei.secondkill.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Order)表实体类
 *
 * @author makejava
 * @since 2022-07-29 20:08:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_order")
public class Order  {
    //订单ID@TableId
    private Long id;

    //用户ID
    private Long userId;
    //商品ID
    private Long goodsId;
    //收获地址ID
    private Long deliveryAddrId;
    //商品名字
    private String goodsName;
    //商品数量
    private Integer goodsCount;
    //商品价格
    private Double goodsPrice;
    //1 pc,2 android, 3 ios
    private Integer orderChannel;
    //订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退货，5已完成
    private Integer status;
    //订单创建时间
    private Date createDate;
    //支付时间
    private Date payDate;



}
