package me.zzxb.controller;

import me.zzxb.pojo.Txls;
import me.zzxb.pojo.Users;
import me.zzxb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Txls> listTxlsByUserName(String uname){
        List<Txls> data = usersService.getTxlsByUserName(uname);
        return data;
    }

    @RequestMapping(value = "/vlist")
    public String listTxlsByUsersNameView(String uname, ModelMap modelMap){
        List<Txls> data = usersService.getTxlsByUserName(uname);
        modelMap.addAttribute("lxrlist",data);
        return "vlist";
    }
}
