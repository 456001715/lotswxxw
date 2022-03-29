package com.lots.lotswxxw.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @name: JasyptUtil 加密解密方法测试
 * @author: lots
 * @date: 2021/4/29 14:47
 */
public class JasyptUtil {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("1otssto!");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("root");
        String url = textEncryptor.encrypt("root");
        System.out.println("username:" + "lots@(" + username + ")");
        System.out.println("password:" + "lots@(" + password + ")");
        System.out.println("url:" + "lots@(" + url + ")");
    }
}
