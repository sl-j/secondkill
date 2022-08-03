package com.lei.secondkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.secondkill.entity.Order;
import com.lei.secondkill.entity.SeckillGoods;
import com.lei.secondkill.entity.SeckillOrder;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.mapper.OrderMapper;
import com.lei.secondkill.mapper.SeckillOrderMapper;
import com.lei.secondkill.service.OrderService;
import com.lei.secondkill.service.SeckillGoodsService;
import com.lei.secondkill.service.SeckillOrderService;
import com.lei.secondkill.utils.MD5Utils;
import com.lei.secondkill.utils.UUIDUtil;
import com.lei.secondkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * (Order)表服务实现类
 *
 * @author makejava
 * @since 2022-07-29 20:08:05
 */
@Service()
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public Order seckill(TUser user, GoodsVo goodsVo) {
        //库存-1
        LambdaQueryWrapper<SeckillGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeckillGoods::getId, goodsVo.getId());
        SeckillGoods seckillGoods = seckillGoodsService.getOne(queryWrapper);
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean secKillResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count = stock_count - 1").eq("goods_id", seckillGoods.getId()).gt("stock_count", 0));

        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断是否还有库存
        if(seckillGoods.getStockCount() < 1){
            valueOperations.set("isStockEmpty:" + goodsVo.getId(),0);
            return null;
        }
        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0l);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(seckillGoods.getId());
        seckillOrderService.save(seckillOrder);

        //将秒杀订单存入redis中
        valueOperations.set("order:"+user.getId()+":"+goodsVo.getId(),seckillOrder);


        return order;
    }

    /**
     * 获取秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    @Override
    public String createPath(TUser user, Long goodsId) {
        String str = MD5Utils.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:" + user.getId() + ":"+goodsId,str,60, TimeUnit.SECONDS);
        return str;
    }

    /**
     * 校验验证码
     * @param user
     * @param goodsId
     * @param captcha
     * @return
     */
    @Override
    public boolean checkCaptcha(TUser user, Long goodsId, String captcha) {
        if(user == null || goodsId < 0 || captcha == null) return false;

        String str = (String) redisTemplate.opsForValue().get("captcha:" + user.getId() + ":" + goodsId);
        return captcha.equals(str);
    }
}
