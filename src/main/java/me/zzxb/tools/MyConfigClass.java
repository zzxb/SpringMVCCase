package me.zzxb.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * 解决could not initialize proxy - no Session错误的解决方案
 */
public class MyConfigClass extends ObjectMapper {

    public MyConfigClass() {
        registerModule(new Hibernate4Module());
    }
}