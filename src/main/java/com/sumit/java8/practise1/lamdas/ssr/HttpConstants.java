package com.sumit.java8.practise1.lamdas.ssr;

/**
 * Created by sumijaiswal on 1/23/17.
 */
public class HttpConstants {
    public static final String HTTP_PATH_SEPARATOR = "/";
    public static final char HTTP_QS_PARAM_DELIMIT = '&';
    public static final char HTTP_QS_PARAM_KV_SEPARATOR = '=';
    public static final char HTTP_URI_QS_DELIMIT = '?';
    public static final String RE_ANYPATH = "^([^?]*)";
    public static final String RE_PATHEND = "(\\?(.*))?$";
    public static final String RE_BEGIN = "(\\?|\\?(.+)&)";
    public static final String RE_END = "(&|$)";
    public static final String RE_0ORMORE = "(.*)";
    public static final String RE_0ORMORE_NO_CAPTURE = ".*";
    public static final String RE_1ORMORE = "(.+)";
    public static final char RE_OPARA = '(';
    public static final char RE_CPARA = ')';
    public static final char RE_OR = '|';
    public static final String RE_ANCHOR_BEGIN = "^";
    public static final String HEADER_VALUE_DELIMITER = ":";


    public static final String KILLER_COOKIE = "X-PP-K";
    public static final String SET_COOKIE_POSTFIX_STR = "; domain=.testing.com; path=/; Secure; HttpOnly";
    public static final String SET_EXPIRY_COOKIE_POSTFIX_STR =
            "; Expires=Thu, 01 Jan 1970 00:00:01 GMT; domain=.testing.com; path=/; Secure; HttpOnly";
    public static final String MULTI_VAL_COOKIE_KV_SEP = "%3D";

    public static final String COOKIE_HEADER = "Cookie";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String EXPIRES = "; Expires=";
    public static final String APP_DEFENSE_COOKIE = "X-PP-ADS";
    public static final String HEADER_DELIMITER = "\r\n";

    public static final String SLINGSHOT_TARGET_APP = "X-PP-SLINGSHOT-TARGETAPP";
    public static final String HTTP_FORWARD_HEADER = "X-PP-HTTP-FORWARD";
    public static final String AZ_LOCATOR_HEADER = "HTTP_X_PP_AZ_LOCATOR";
    public static final String SLR_BOUNCE_HEADER = "X-SLR-BOUNCE";
    public static final String SLR_NO_BOUNCE_HEADER = "X-SLR-NOBOUNCE";
    public static final String SLR_REQUEST_RETRY_HEADER = "X-SLR-RETRY";
    public static final String SLR_RETRY_EMPTY = "SLR-RETRY-EMPTY";
    public static final String AD_STICKINESS = "dispatch";
    public static final String SL_STICKINESS = "X-PP-SILOVER";
    public static final String HEADER_HOST = "HTTP_HOST";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
}
