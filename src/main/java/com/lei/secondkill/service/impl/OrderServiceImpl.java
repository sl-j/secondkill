package com.lei.secondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.secondkill.entity.Order;
import com.lei.secondkill.mapper.OrderMapper;
import com.lei.secondkill.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * (Order)表服务实现类
 *
 * @author makejava
 * @since 2022-07-29 20:08:05
 */
@Service()
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
