package com.lei.secondkill.vo;


import com.lei.secondkill.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo{
    //商品id@TableId
    private Long id;

    //商品名称
    private String goodsName;
    //商品标题
    private String goodsTitle;
    //商品图片
    private String goodsImg;
    //商品描述
    private String goodsDetail;
    //商品价格
    private BigDecimal goodsPrice;
    //商品库存,-1表示没有限制
    private Integer goodsStock;

    //秒杀家
    private BigDecimal seckillPrice;
    //库存数量
    private Integer stockCount;
    //秒杀开始时间
    private Date startDate;
    //秒杀结束时间
    private Date endDate;
}
