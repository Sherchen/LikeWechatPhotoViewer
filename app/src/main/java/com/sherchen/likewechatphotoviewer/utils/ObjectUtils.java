package com.sherchen.likewechatphotoviewer.utils;

import java.util.List;

/**
 * Created by dave on 2017/1/10.
 */

public class ObjectUtils {

    public static <T> int getSize(List<T> listEntity){
        return listEntity == null ? 0 : listEntity.size();
    }

    public static <T> int getSize(T[] listEntity){
        return listEntity == null ? 0 : listEntity.length;
    }

}
