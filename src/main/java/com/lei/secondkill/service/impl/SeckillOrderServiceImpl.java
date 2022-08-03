package com.lei.secondkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.secondkill.entity.SeckillOrder;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.mapper.SeckillOrderMapper;
import com.lei.secondkill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * (SeckillOrder)表服务实现类
 *
 * @author makejava
 * @since 2022-07-29 20:08:33
 */
@Service()
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    @Override
    public Long getResult(TUser user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new LambdaQueryWrapper<SeckillOrder>().eq(SeckillOrder::getUserId, user.getId()).eq(SeckillOrder::getGoodsId, goodsId));

        if(seckillOrder != null){
            return seckillOrder.getId();
        }else if(redisTemplate.hasKey("isStockEmpty:" + goodsId)){
            return -1l;
        }else{
            return 0L;
        }
    }
}
