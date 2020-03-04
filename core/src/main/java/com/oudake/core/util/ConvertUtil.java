package com.oudake.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyi
 */
public class ConvertUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConvertUtil.class);

    /**
     * 对象转换到Map
     * @param obj obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            LOGGER.error(">>对象转换Map异常: " + e);
        }
        return map;
    }

}
