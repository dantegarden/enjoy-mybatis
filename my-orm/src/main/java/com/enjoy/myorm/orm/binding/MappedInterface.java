package com.enjoy.myorm.orm.binding;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: mapper接口文件
 * @author: lij
 * @create: 2019-10-06 18:47
 */
@Data
public class MappedInterface {
    private String interfaceId;
    private Map<String, MappedMethod> methods = new HashMap<>();

    public MappedInterface(String interfaceId) {
        this.interfaceId = interfaceId;
    }
}
