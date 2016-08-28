package me.zzxb.service;

import me.zzxb.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
