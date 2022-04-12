package com.lots.lots.util;

import cn.hutool.core.util.RandomUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 用于接口加密。因为接口返回的数据长度可能超过普通加密的长度，所以用此方法加密
 *
 * @author lots
 * @version 1.0
 * @date 2012-4-26
 */
public class IntFaceEncryptUtils {

    /**
     * 默认加密秘钥，必须16位
     */
    private static final String DEFAULT_KEY = "a2s1xsd1~a1!xssf";

    /*** 算法*/
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * aes默认密码解密
     *
     * @param encrypt 内容
     * @return
     * @throws Exception
     */
    public static String decryptByDefaultKey(String encrypt) throws Exception {
        return decrypt(encrypt, DEFAULT_KEY);
    }

    /**
     * aes默认密码加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String encryptByDefaultKey(String content) throws Exception {
        return encrypt(content, DEFAULT_KEY);
    }

    /**
     * aes指定密码解密
     *
     * @param encrypt 内容
     * @return
     * @throws Exception
     */
    public static String decryptByKey(String encrypt, String key) throws Exception {
        return decrypt(encrypt, DEFAULT_KEY);
    }

    /**
     * aes指定密码加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String encryptByKey(String content, String key) throws Exception {
        return encrypt(content, DEFAULT_KEY);
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    private static byte[] base64Decode(String base64Code) throws Exception {

        return StringUtils.isEmpty(base64Code) ? null : java.util.Base64.getDecoder().decode(base64Code);
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的 String
     * @throws Exception
     */
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static void main(String[] args) {
        String key = RandomUtil.randomString(16);
        System.out.println(key);
        String b = "{knxx={是否建档立卡=否, 是否农村低保户=否, 困难程度=不困难, 是否低收入家庭=否, 年级认定意见=null, 家庭是否遭受突发意外=否, 是否孤儿=否, 认定时间=2016-09-25 00:00:00.0, 其他=null, 认定原因=调查了解, 是否残疾人子女=否, 家庭是否遭受自然灾害=否, 是否父母失业或丧失劳动能力=否, 家中是否有大病患者=否, 是否烈士子女=否, 是否残疾=否, 是否扶贫低保户=否, 是否农村五保户=否, 是否农村特困供养=否, 是否单亲家庭子女=否, 是否扶贫户=否, 班级认定意见=null}, jtcyxx=null, jbxx={就读方式=走读, 入学年份=201609, 特长=null, OID=00e120411fcf4e0db5fc, 专业=null, 性别=女, 学校=德阳市旌阳区柏隆小学校, 姓名=陆钰婷, 民族=汉族, 班级=null, 身体状况=健康或良好, 家庭住址=安徽省蒙城县立仓镇陆瓦坊村陆胡庄18号, 学籍号=L510603201004250043, 联系电话=3661919, 出生日期=20100425, 学院=null, 政治面貌=群众, 户口所在地=蒙城县, 上下学距离=null, 身份证号=341622201004252143, 现住址=安徽省蒙城县立仓镇陆瓦坊村陆胡庄18号, 教育阶段=义务教育}, jtqkxx=null}";
        //加密
        try {
            String result = encryptByKey(b, key);
            System.out.println(result);
            //解密
            System.out.println(decryptByKey(result, key));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    // 前端解密
//    $(function(){
//
//        $.ajax({
//
//                type:"post",
//                url:"http://localhost:8081/post",
//                data:{
//            a:"你是谁啊？？"
//        },
//        success:function(res){
//            var data = JSON.parse( Decrypt('a2s1xsd12a@!xssf',res.data));
//            console.log(data);
//        }
//    })
//
//        //解密
//        function Decrypt(keyStr,word){
//            var key = CryptoJS.enc.Utf8.parse(keyStr);
//            var decrypt = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
//            return CryptoJS.enc.Utf8.stringify(decrypt).toString();
//        }
//    })

}
