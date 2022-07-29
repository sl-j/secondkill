package com.lei.secondkill.controller;


import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private TUserService tUserService;

    @RequestMapping("/toList")
    public String toList(Model model,TUser user){

        model.addAttribute("user",user);
        return "goodsList";
    }
}
