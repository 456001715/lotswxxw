package com.lots.lots.util;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * ImgJsonSerializer
 *
 * @author lots
 * @date 2022/4/11 9:44
 */
@Component
public class ImgJsonSerializer extends JsonSerializer<String> {

    @Value("${lots.oss.resources-url}")
    private String imgDomain;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (StrUtil.isBlank(value)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        } else if (StrUtil.isBlank(imgDomain)) {
            gen.writeString(value);
            return;
        }
        String[] imgs = value.split(StrUtil.COMMA);
        StringBuilder sb = new StringBuilder();

        for (String img : imgs) {
            if (ReUtil.isMatch(PatternPool.URL_HTTP, img)) {
                sb.append(img).append(StrUtil.COMMA);
            }
            else {
                sb.append(imgDomain).append(img).append(StrUtil.COMMA);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        gen.writeString(sb.toString());
    }
}
