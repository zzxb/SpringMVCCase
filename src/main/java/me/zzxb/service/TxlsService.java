package me.zzxb.service;

import me.zzxb.pojo.Txls;
import me.zzxb.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by zzxb on 16/8/29.
 */

@Service("txlsService")
public class TxlsService {
    @Autowired
    HibernateTemplate hibernateTemplate;
    @Autowired
    UsersService usersService;

    public boolean addTxl(String uname, Txls txls){
        Users myUser = usersService.findByUserName(uname);
        txls.setUsersByUserid(myUser);
        hibernateTemplate.saveOrUpdate(txls);
        return true;
    }
}
