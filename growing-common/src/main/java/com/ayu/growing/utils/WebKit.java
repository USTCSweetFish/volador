package com.ayu.growing.utils;

import com.ayu.growing.exception.ExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class WebKit {

    public static final String AES_KEY_DEFAULT = "9e7cv358fdlx463f";//aes_key,

    public static final String API_TRANSCLIENTIP_KEY = "apiTransClientIp";

    public static final String API_TRANSUID_KEY = "payApigatewayTransmitUId";


    public static HttpServletRequest getHttpRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static HttpServletRequest getHttpRequestDefNull() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request == null) {
            return null;
        }
        return request;
    }

    /**
     * 通过HttpServletRequest 获取 用户id
     *
     * @return
     */
    public static String getMidFromReqHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null) {
            throw ExceptionEnum.INTERNAL_ERROR.createException();
        }
        String mId = request.getHeader(API_TRANSUID_KEY);
        return StringUtils.trimToEmpty(mId);
    }

    /**
     * 通过HttpServletRequest 获取 用户id
     *
     * @return
     */
    public static String getClientIpFromReqHeader() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request == null) {
            return null;
        }
        String clientIp = request.getHeader(API_TRANSCLIENTIP_KEY);
        return StringUtils.trimToEmpty(clientIp);
    }

    private static final String URL_PATH_SEGMENT = "/";

    /**
     * 后期修改为String ...
     */
    public static String buildConcatPath(String path) {
        if (StringUtils.isBlank(path)) {
            return "";
        }
        return URL_PATH_SEGMENT.concat(path);
    }

    public static String getUserDeviceId() {
        HttpServletRequest request = getHttpRequest();
        String deviceId = StringUtils.EMPTY;
        if (null != request) {
            deviceId = StringUtils.defaultIfBlank(request.getHeader("deviceFingerprint"), request.getHeader("Buvid"));
        }

        return deviceId == null ? "" : deviceId;
    }


    public static String getDefClientIpFromReq() {
        String clientIp = getClientIpFromReqHeader();
        if (StringUtils.isNotBlank(clientIp)) {
            return clientIp;
        }
        return IpUtils.getClientIpAddr(WebKit.getHttpRequest());
    }


    /**
     *获取ua信息
     */
    public static String getUserAgent() {

        return getHeadValue("USER-AGENT");
    }

    /**
     *获取mobi_app
     */
    public static String getMobiApp() {

        return getHeadValue("mobiapp");
    }

    /**
     *获取buildId
     */
    public static Integer getBuildId() {
        String buildId = getHeadValue("buildid");
        if (StringUtils.isNotBlank(buildId)) {
            try {
                return Integer.parseInt(buildId);
            } catch (Exception e) {

            }
        }
        return null;
    }

    public static String getHeadValue(String key) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request == null) {
            return null;
        }
        String headerValue = request.getHeader(key);
        return StringUtils.trimToEmpty(headerValue);
    }

}
