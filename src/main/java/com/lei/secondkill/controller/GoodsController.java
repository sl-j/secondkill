package com.lei.secondkill.controller;


import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.service.GoodsService;
import com.lei.secondkill.service.TUserService;
import com.lei.secondkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private TUserService tUserService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/toList")
    public String toList(Model model,TUser user){

        model.addAttribute("user",user);
        List<GoodsVo> goodVo = goodsService.findGoodsVo();
        model.addAttribute("goodsList",goodVo);
        return "goodsList";
    }

    /**
     * 跳转商品详情
     * @param goodsId
     * @return
     */
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model,TUser user,@PathVariable Long goodsId){
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.toDetail(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();

        //秒杀状态
        int secKillStatus = 0;

        //秒杀倒计时
        int remainSeconds = 0;
        if(nowDate.before(startDate)){
            remainSeconds = ((int)((startDate .getTime() - nowDate.getTime())) / 1000);
            secKillStatus = 0;
        }else if(nowDate.after(endDate)){
            secKillStatus = 2;
            remainSeconds = -1;
        }else{
            secKillStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("goods",goodsVo);
        model.addAttribute("secKillStatus",secKillStatus);
        return "goodsDetail";
    }
}
