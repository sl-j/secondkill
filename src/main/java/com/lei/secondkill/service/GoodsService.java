package com.lei.secondkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.secondkill.entity.Goods;
import com.lei.secondkill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * (Goods)表服务接口
 *
 * @author makejava
 * @since 2022-07-29 20:07:23
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();
}
