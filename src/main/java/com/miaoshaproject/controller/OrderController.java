package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturntype;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    //封装下单请求
    public CommonReturntype creatOrder(@RequestParam(name = "itemId")Integer itemId,
                                       @RequestParam(name = "amount")Integer amount) throws BusinessException {

        //获取用户的登陆信息


        OrderModel orderModel = orderService.creatOrder(null,itemId,amount);
        return CommonReturntype.create(null);
    }
}
