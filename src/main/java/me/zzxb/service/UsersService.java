package me.zzxb.service;

import me.zzxb.pojo.Txls;
import me.zzxb.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzxb on 16/8/24.
 */

@Service("usersService")
public class UsersService {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    public boolean addUsers(Users myUser){
        hibernateTemplate.save(myUser);
        return true;
    }

    public boolean updateUsers(String uname,String upwd,String newUpwd){
        Users myUser = this.findByUserName(uname);
        if(myUser != null){
            if(myUser.getUpwd().equals(upwd)){
                myUser.setUpwd(newUpwd);
                hibernateTemplate.saveOrUpdate(myUser);
                return true;
            }
        }
        return false;
    }

    public boolean delUsers(int uid){
        String hsql = "delete Users u where u.userid = ?";
        hibernateTemplate.bulkUpdate(hsql,uid);
        return true;
    }

    public Users findByUserName(String userName){
        String hsql = "from Users u where u.uname = ?";
        List<?> data = hibernateTemplate.find(hsql,userName);
        if(!data.isEmpty()){
            return (Users) data.get(0);
        }
        return null;
    }


    public List<Txls> getTxlsByUserName(String userName){
        String hsql = "select txl from Users u inner join u.txlsesByUserid txl where u.uname = ?";
        List<Txls> data = (List<Txls>) hibernateTemplate.find(hsql,userName);
        return data;
    }
}
