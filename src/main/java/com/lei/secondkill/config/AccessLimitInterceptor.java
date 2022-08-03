//package com.lei.secondkill.config;
//
//import com.lei.secondkill.entity.TUser;
//import com.lei.secondkill.service.TUserService;
//import com.lei.secondkill.utils.CookieUtil;
//import com.lei.secondkill.vo.AppHttpCodeEnum;
//import com.lei.secondkill.vo.ResponseResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.util.StringUtils;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.concurrent.TimeUnit;
//
//public class AccessLimitInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private TUserService tUserService;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(handler instanceof HandlerMethod){
//
//            TUser user = getUser(request,response);
//            HandlerMethod hm = (HandlerMethod) handler;
//            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
//            if(accessLimit == null){
//                return true;
//            }
//
//            int second = accessLimit.second();
//            int maxCount = accessLimit.maxCount();
//            boolean needLogin = accessLimit.needLogin();
//            String uri = request.getRequestURI();
//            if(needLogin){
//
//            }
//            //限制访问次数
//            ValueOperations valueOperations = redisTemplate.opsForValue();
//
//            captcha ="0";
//            Integer count = (Integer) valueOperations.get(uri + ":" + user.getId());
//            if(count == null){
//                valueOperations.set(uri + ":" + user.getId(),1,5, TimeUnit.SECONDS);
//            } else if (count < 5) {
//                valueOperations.increment(uri + ":" + user.getId());
//            }else {
//                return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
//            }
//        }
//        return false;
//    }
//
//    private TUser getUser(HttpServletRequest request, HttpServletResponse response) {
//        String ticket = CookieUtil.getCookieValue(request, "userTicket");
//        if(StringUtils.isEmpty(ticket)) return null;
//
//        return tUserService.getUserByCookies(ticket,request,response);
//    }
//}
