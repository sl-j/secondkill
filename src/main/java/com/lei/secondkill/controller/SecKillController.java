package com.lei.secondkill.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lei.secondkill.entity.Order;
import com.lei.secondkill.entity.SeckillOrder;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.service.GoodsService;
import com.lei.secondkill.service.OrderService;
import com.lei.secondkill.service.SeckillGoodsService;
import com.lei.secondkill.service.SeckillOrderService;
import com.lei.secondkill.vo.AppHttpCodeEnum;
import com.lei.secondkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, TUser user,Long goodsId){
        if(user == null) return "login";

        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.toDetail(goodsId);
        //判断库存
        if(goodsVo.getStockCount() < 1) {
            model.addAttribute("errmsg", AppHttpCodeEnum.EMPTY_STOCK);
            return "secKillFail";
        }

        //判断是否重复抢购
//        LambdaQueryWrapper<SeckillOrder> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SeckillOrder::getUserId,user.getId()).eq(SeckillOrder::getGoodsId,goodsId);
//        SeckillOrder seckillOrder = seckillOrderService.getOne(queryWrapper);
        //从redis中拿出订单信息，判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsVo.getId());

        if(seckillOrder != null){
            model.addAttribute("errmsg", AppHttpCodeEnum.REPEAT_ERROR);
            return "secKillFail";
        }

//        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Order order = orderService.seckill(user,goodsVo);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);

        return "orderDetail";

    }
}
