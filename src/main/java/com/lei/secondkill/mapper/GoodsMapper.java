package com.lei.secondkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.secondkill.entity.Goods;
import com.lei.secondkill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * (Goods)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-29 20:07:23
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {


    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoBygooodsId(Long goodsId);
}
