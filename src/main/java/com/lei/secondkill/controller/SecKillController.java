package com.lei.secondkill.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lei.secondkill.entity.Order;
import com.lei.secondkill.entity.SeckillMsg;
import com.lei.secondkill.entity.SeckillOrder;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.rabbitmq.MQSender;
import com.lei.secondkill.service.GoodsService;
import com.lei.secondkill.service.OrderService;
import com.lei.secondkill.service.SeckillGoodsService;
import com.lei.secondkill.service.SeckillOrderService;
import com.lei.secondkill.utils.JsonUtil;
import com.lei.secondkill.vo.AppHttpCodeEnum;
import com.lei.secondkill.vo.GoodsVo;
import com.lei.secondkill.vo.ResponseResult;
import org.apache.catalina.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    private Map<Long,Boolean> EmptyStockMap = new HashMap<>();

    @RequestMapping("/doSeckill")
    @ResponseBody
    public ResponseResult doSeckill(Model model, TUser user,Long goodsId){
        if(user == null) ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);

        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(seckillOrder != null){
            return ResponseResult.errorResult(AppHttpCodeEnum.REPEAT_ERROR);
        }

        //通过内存标记判断是否为空，减少对redis的访问压力
        if(EmptyStockMap.get(goodsId)){
            return ResponseResult.errorResult(AppHttpCodeEnum.EMPTY_STOCK);
        }

        //预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if(stock < 0){
            EmptyStockMap.put(goodsId,true);
            return ResponseResult.errorResult(AppHttpCodeEnum.EMPTY_STOCK);
        }

        SeckillMsg seckillMsg = new SeckillMsg(user,goodsId);
        mqSender.send(JsonUtil.object2JsonStr(seckillMsg));
        return ResponseResult.okResult(0);//0表示正在排队中
    }

    /**
     * 系统初始化，把商品库存数量加载到redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVo = goodsService.findGoodsVo();
        if(CollectionUtils.isEmpty(goodsVo)){
            return;
        }

        goodsVo.forEach( goodsVo1 -> {
            redisTemplate.opsForValue().set("seckillGoods:"+goodsVo1.getId(),goodsVo1.getStockCount());
            EmptyStockMap.put(goodsVo1.getId(),false);
        });

    }

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getResult(TUser user,Long goodsId){
        if(user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        Long orderId = seckillOrderService.getResult(user,goodsId);

        return ResponseResult.okResult(orderId);
    }
}
