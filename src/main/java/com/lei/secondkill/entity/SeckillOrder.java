package com.lei.secondkill.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (SeckillOrder)表实体类
 *
 * @author makejava
 * @since 2022-07-29 20:08:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_seckill_order")
public class SeckillOrder  {
    //秒杀订单ID@TableId
    private Long id;

    //用户ID
    private Long userId;
    //订单ID
    private Long orderId;
    //商品ID
    private Long goodsId;



}
