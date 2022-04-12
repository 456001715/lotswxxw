package com.lots.lots.util;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @name: IpUtil
 * @author: lots
 * @date: 2021/4/29 10:13
 */
public class IpUtil {
    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址。
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            String localhost = "127.0.0.1";
            String localIp = "0:0:0:0:0:0:0:1";
            if (localhost.equals(ip) || localIp.equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }

    public static void main(String[] args) throws Exception {
        String aimartSignInUrl = "https://aimart.zkj.com/app-api/promotion-coupon/signIn/getSignInCalender";
        String aimartSignInResult = HttpRequest
                .get(aimartSignInUrl)
                .header(Header.HOST,"aimart.zkj.com")
                .header(Header.CONNECTION,"keep-alive")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.64")
                .header("aimart-version","1.6.6")
                .header(Header.AUTHORIZATION,"455a5a6ac9de42499b6096191ab4f40ae8c9b6a84735541ac064e34d435d640e")
                .header("chan","aimart_mini")
                .header(Header.CONTENT_TYPE,"application/json")
                .header("device-id","MMMM9C287922EE264502")
                .header("merchant-id","3")
                .header("merchantId","3")
                .header("store-code",null)
                .header("store-id",null)
                .header(Header.REFERER, "https://servicewechat.com/wx345bc6693c73d31a/86/page-frame.html")
                .header(Header.ACCEPT_ENCODING,"gzip, deflate, br")
                .header(Header.ORIGIN, "https://aimart.zkj.com")
                .execute().body();
        System.out.println(aimartSignInResult);
    }
}
