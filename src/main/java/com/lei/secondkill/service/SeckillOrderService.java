package com.lei.secondkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.secondkill.entity.SeckillOrder;
import com.lei.secondkill.entity.TUser;
import org.springframework.stereotype.Service;


/**
 * (SeckillOrder)表服务接口
 *
 * @author makejava
 * @since 2022-07-29 20:08:33
 */

public interface SeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(TUser user, Long goodsId);
}
