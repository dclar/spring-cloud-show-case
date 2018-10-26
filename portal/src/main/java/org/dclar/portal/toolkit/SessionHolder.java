package org.dclar.portal.toolkit;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Description:
 *
 * @author dclar
 */
public class SessionHolder {


    /**
     * 向session中放入数据
     *
     * @param sessionKey
     * @param sessionValue
     */
    public static void put(String sessionKey, Object sessionValue) {
        RequestContextHolder.currentRequestAttributes()
                .setAttribute(
                        sessionKey,
                        sessionValue,
                        RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 从session中取出数据
     *
     * @param sessionKey
     * @return
     */
    public static Object get(String sessionKey) {
        return RequestContextHolder.currentRequestAttributes()
                .getAttribute(
                        sessionKey,
                        RequestAttributes.SCOPE_SESSION);
    }
}
