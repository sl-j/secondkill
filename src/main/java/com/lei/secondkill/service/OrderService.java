package com.lei.secondkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.secondkill.entity.Order;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.vo.GoodsVo;
import org.springframework.stereotype.Service;


/**
 * (Order)表服务接口
 *
 * @author makejava
 * @since 2022-07-29 20:08:05
 */

public interface OrderService extends IService<Order> {

    Order seckill(TUser user, GoodsVo goodsVo);

    String createPath(TUser user, Long goodsId);

    boolean checkCaptcha(TUser user, Long goodsId, String captcha);
}
