package com.lei.secondkill.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (SeckillGoods)表实体类
 *
 * @author makejava
 * @since 2022-07-29 20:08:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_seckill_goods")
public class SeckillGoods  {
    //秒杀商品ID@TableId
    private Long id;

    //商品ID
    private Long goodsId;
    //秒杀家
    private Double seckillPrice;
    //库存数量
    private Integer stockCount;
    //秒杀开始时间
    private Date startDate;
    //秒杀结束时间
    private Date endDate;



}
