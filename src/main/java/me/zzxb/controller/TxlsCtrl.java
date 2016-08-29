package me.zzxb.controller;

import me.zzxb.pojo.Txls;
import me.zzxb.service.TxlsService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by zzxb on 16/8/29.
 */

@Controller
@RequestMapping("/txls")
public class TxlsCtrl {
    @Autowired
    TxlsService txlsService;

    @RequestMapping(value = "/addlxr")
    @ResponseBody
    public Map<String,String> addLxrs(String uname, String lxrname, String lxrtel){
        Txls txls = new Txls(lxrname,lxrtel);
        boolean isSucc = txlsService.addTxl(uname,txls);
        Map<String,String> data = new HashMap<String,String>();
        data.put("state","1");
        data.put("info","添加成功!");
        return data;
    }
}
