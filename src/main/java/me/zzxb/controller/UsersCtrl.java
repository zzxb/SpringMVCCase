package me.zzxb.controller;

import me.zzxb.pojo.Txls;
import me.zzxb.pojo.Users;
import me.zzxb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "/register/{userName}/{upwd}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> register(@PathVariable String userName,@PathVariable String upwd){
        Users myUsers = new Users(userName,upwd);
        usersService.addUsers(myUsers);
        Map<String,String> data = new HashMap<String,String>();
        data.put("state","200001");
        data.put("info","插入成功!");
        return data;
    }

    @RequestMapping(value = "/update/{uname}/{upwd}/{newUpwd}",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updateUserPwd(@PathVariable String uname, @PathVariable String upwd,@PathVariable String newUpwd){
        boolean isSucc = usersService.updateUsers(uname,upwd,newUpwd);
        Map<String,Object> data = new HashMap<>();
        if(isSucc) {
            data.put("state","200001");
            data.put("info", "更新成功!");
        }else{
            data.put("state","500001");
            data.put("info","更新失败！");
        }
        return data;
    }

    @RequestMapping(value = "/del/{uid}",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> delUsers(@PathVariable String uid){
        usersService.delUsers(Integer.parseInt(uid));
        Map<String,Object> data = new HashMap<>();
        data.put("state","200001");
        data.put("info","删除成功!");
        return data;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET  )
    @ResponseBody
    public List<Txls> listTxlsByUserName(String uname){
        List<Txls> data = usersService.getTxlsByUserName(uname);
        return data;
    }

    @RequestMapping(value = "/vlist",method = RequestMethod.GET )
    public String listTxlsByUsersNameView(String uname, ModelMap modelMap){
        List<Txls> data = usersService.getTxlsByUserName(uname);
        modelMap.addAttribute("lxrlist",data);
        return "vlist";
    }
}
