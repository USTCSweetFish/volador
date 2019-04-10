package com.ayu.growing.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    /**
     * 获取浏览器所在用户端的ip地址
     *
     * @param request
     * @return
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Real-IP");
        if (invalidIp(ipAddress)) {
            String forwardIps = request.getHeader("x-forwarded-for");
            if (StringUtils.isNotBlank(forwardIps)) {
                String forwardIp = forwardIps.split(",")[0];
                if (!invalidIp(forwardIp)) {
                    ipAddress = forwardIp;
                }
            }
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
//        BiliLog.info("ipTestInfo: backendReal={},realIp={},forwardIp={},proxyClientIp={},wlProxyClientIp={},remoteAddr={},ip={}", request.getHeader("X-BACKEND-BILI-REAL-IP"), request.getHeader("X-Real-IP"),
//                request.getHeader("x-forwarded-for"), request.getHeader("Proxy-Client-IP"), request.getHeader("WL-Proxy-Client-IP"), request.getRemoteAddr(), ipAddress);
        return ipAddress;
    }

    private static boolean invalidIp(String ipAddress) {
        return ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress) || isInnerIP(ipAddress);
    }

    /**
     * 判断是否内网ip
     *
     * @param ipAddress
     * @return
     */
    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp = false;
        try {
            long ipNum = getIpNum(ipAddress);
            /**
             私有IP：A类  10.0.0.0-10.255.255.255
             B类  172.16.0.0-172.31.255.255
             C类  192.168.0.0-192.168.255.255
             当然，还有127这个网段是环回地址
             **/
            long aBegin = getIpNum("10.0.0.0");
            long aEnd = getIpNum("10.255.255.255");
            long bBegin = getIpNum("172.16.0.0");
            long bEnd = getIpNum("172.31.255.255");
            long cBegin = getIpNum("192.168.0.0");
            long cEnd = getIpNum("192.168.255.255");
            isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || ipAddress.equals("127.0.0.1");
        } catch (Exception e) {

        }
        return isInnerIp;
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }
}
