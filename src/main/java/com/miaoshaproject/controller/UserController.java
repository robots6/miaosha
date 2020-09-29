package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturntype;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登陆接口
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturntype login(@RequestParam(name = "telphone") String telphone,
                                  @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登陆服务，用来校验用户登陆是否合法
        UserModel userModel = userService.validateLogin(telphone, password);
        //将登陆凭证加入到用户登陆成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN", userModel);
        return CommonReturntype.create(null);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturntype register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String inSessionOtpCode = (String) httpServletRequest.getSession().getAttribute(telphone);
        if (!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setTelephone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturntype.create(null);

    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }


    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody

    public CommonReturntype getOtp(@RequestParam(name = "telphone") String telphone) {
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 1000;
        String otpCode = String.valueOf(randomInt);

        httpServletRequest.getSession().setAttribute(telphone, otpCode);
        System.out.println("telphone=" + telphone + "&otpCode=" + otpCode);

        return CommonReturntype.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturntype getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);

        if (userModel == null) {
//            userModel.setEncrptPassword("123");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        UserVO userVO = convertFormModel(userModel);
        return CommonReturntype.create(userVO);
    }

    private UserVO convertFormModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }


}
