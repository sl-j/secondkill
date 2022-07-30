package com.lei.secondkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.secondkill.entity.Goods;
import com.lei.secondkill.mapper.GoodsMapper;
import com.lei.secondkill.service.GoodsService;
import com.lei.secondkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Goods)表服务实现类
 *
 * @author makejava
 * @since 2022-07-29 20:07:23
 */
@Service()
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public List<GoodsVo> findGoodsVo() {

        return goodsMapper.findGoodsVo();
    }

    /**
     * 获取商品详情
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVo toDetail(Long goodsId) {
        return goodsMapper.findGoodsVoBygooodsId(goodsId);
    }
}
