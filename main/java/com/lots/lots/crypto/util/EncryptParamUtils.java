package com.lots.lots.crypto.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @name: EncryptParamUtils
 * @author: lots
 * @date: 2021/5/6 15:18
 */
public class EncryptParamUtils {
    /**
     * 将加密对象进行可识别的序列化并转换为字节数据
     *
     * @param data 要加密的数据
     * @return 字节数据
     */
    public static byte[] toEncryptByte(Object data) {
        if (ObjectUtil.isNull(data)) {
            return null;
        }

        if (data instanceof String) {
            return ((String) data).getBytes();
        } else if (data instanceof Enum) {
            return ((Enum<?>) data).name().getBytes();

        }
        return JSONObject.toJSONBytes(data);
    }
}
