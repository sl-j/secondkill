package com.lei.secondkill.rabbitmq;

import com.lei.secondkill.entity.SeckillMsg;
import com.lei.secondkill.entity.SeckillOrder;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.service.GoodsService;
import com.lei.secondkill.service.OrderService;
import com.lei.secondkill.utils.JsonUtil;
import com.lei.secondkill.vo.AppHttpCodeEnum;
import com.lei.secondkill.vo.GoodsVo;
import com.lei.secondkill.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息消费者
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    /**
     * 下单操作
     * @param msg
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg){
        log.info("接收消息：" + msg);
        SeckillMsg seckillMsg = JsonUtil.jsonStr2Object(msg,SeckillMsg.class);
        Long goodsId = seckillMsg.getGoodId();
        TUser user = seckillMsg.getUser();
        GoodsVo goodsVo = goodsService.toDetail(goodsId);
        if(goodsVo.getStockCount() < 1){
            return;
        }

        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(seckillOrder != null){
            return;
        }

        //下单
        orderService.seckill(user,goodsVo);
    }
}
