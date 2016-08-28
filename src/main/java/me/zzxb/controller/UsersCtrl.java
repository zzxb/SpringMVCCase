package me.zzxb.controller;

import me.zzxb.pojo.Users;
import me.zzxb.service.UsersService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by zzxb on 16/8/24.
 */

@Controller
@RequestMapping("/users")
public class UsersCtrl {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/register")
    @ResponseBody
    public Map<String,String> register(String userName,String upwd){
        Users myUsers = new Users(userName,upwd);
        usersService.addUsers(myUsers);
        Map<String,String> data = new HashMap<String,String>();
        data.put("state","1");
        data.put("info","插入成功!");
        return data;
    }
}
