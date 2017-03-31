package com.sumit.java8.practise1.lamdas.ssr;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@XmlType(name = "http_match")
public class HttpMatch {
    private static final String[] TRIMMABLE_URI_PREFIXES =
            { HttpConstants.RE_0ORMORE, HttpConstants.RE_0ORMORE_NO_CAPTURE };

    @XmlAttribute
    private String checksum;

    private String uriPath;
    @XmlElement(name = "param")
    private List<HttpMatchParam> params = new ArrayList<>();

    private boolean uriPathHasWildCard = false;

    /**
     * The central, non-wildcard portion of the {@link #uriPath}.
     * <p>
     * This is as-per the {@code m_uripath_value} parsing within the CPP Slingshot::Rule constructor.
     */
    private String simpleUriPath;

    private TreeMultimap<String, String> sortedMap =
            TreeMultimap.create(Ordering.natural(), Ordering.natural().nullsFirst());

    private Pattern uriPathPattern;
    private Pattern qsPattern;

    public static Pattern createQueryStringPattern(TreeMultimap<String, String> sortedMap) {
        return Pattern.compile(createQueryStringPatternString(sortedMap));
    }

    static String createQueryStringPatternString(TreeMultimap<String, String> sortedMap) {
        StringBuilder sbQueryString = new StringBuilder(HttpConstants.RE_BEGIN);
        boolean[] isFirst = new boolean[] { true };
        sortedMap.entries().forEach(entry -> {
            String oneParamPattern = StringUtil.isNullOrEmpty(entry.getValue()) ?
                    createNoValueParamPattern(entry.getKey()) :
                    createParamValuePattern(entry.getKey(), entry.getValue());

            if (isFirst[0]) {
                sbQueryString.append(oneParamPattern);
                isFirst[0] = false;
            }
            else {
                // key=val|(.+)&key=val
                // no value - (key=(.*)|key)|(.+)&(key=(.*)|key)
                sbQueryString.append(HttpConstants.HTTP_QS_PARAM_DELIMIT);
                sbQueryString.append(HttpConstants.RE_OPARA);
                sbQueryString.append(oneParamPattern);
                sbQueryString.append(HttpConstants.RE_OR);
                sbQueryString.append(HttpConstants.RE_1ORMORE);
                sbQueryString.append(HttpConstants.HTTP_QS_PARAM_DELIMIT);
                sbQueryString.append(oneParamPattern);
                sbQueryString.append(HttpConstants.RE_CPARA);
            }
        });
        sbQueryString.append(HttpConstants.RE_END);
        return sbQueryString.toString();
    }

    /**
     * non empty value case
     *
     * @param param: the param
     * @param value: the non empty value of param
     * @return {@code key=val}
     */
    private static String createParamValuePattern(String param, String value) {
        return param + HttpConstants.HTTP_QS_PARAM_KV_SEPARATOR + value;
    }

    /**
     * no value case
     *
     * @param param: the param
     * @return {@code (key=(.*)|key)}
     */
    private static String createNoValueParamPattern(String param) {
        return String.valueOf(HttpConstants.RE_OPARA) + param + HttpConstants.HTTP_QS_PARAM_KV_SEPARATOR +
                HttpConstants.RE_0ORMORE + HttpConstants.RE_OR + param + HttpConstants.RE_CPARA;
    }

    private static boolean isTrimmable(final String uriPath) {
        if (!StringUtils.isEmpty(uriPath)) {
            if (uriPath.startsWith(HttpConstants.HTTP_PATH_SEPARATOR) || uriPath
                    .startsWith(HttpConstants.RE_ANCHOR_BEGIN))
            {
                return true;
            }
            for (final String trimmablePrefix : TRIMMABLE_URI_PREFIXES) {
                if (uriPath.startsWith(trimmablePrefix)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String createSimpleUriPath(final String uriPath) {
        if (!isTrimmable(uriPath)) {
            return "";
        }

        String simpleUriPath = uriPath;
        final int pathEnd = uriPath.indexOf(HttpConstants.RE_PATHEND);
        if (-1 < pathEnd) {
            simpleUriPath = simpleUriPath.substring(0, pathEnd);
        }

        for (final String trimmablePrefix : TRIMMABLE_URI_PREFIXES) {
            if (simpleUriPath.startsWith(trimmablePrefix)) {
                simpleUriPath = simpleUriPath.substring(trimmablePrefix.length());
            }
            if (simpleUriPath.endsWith(trimmablePrefix)) {
                simpleUriPath = simpleUriPath.substring(0, simpleUriPath.length() - trimmablePrefix.length());
            }
        }

        return simpleUriPath;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getUriPath() {
        return uriPath;
    }

    @XmlElement(name = "uri_path")
    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public List<HttpMatchParam> getParams() {
        return params;
    }

    public String getId() {
        return (uriPathPattern == null ? "" : uriPathPattern.toString()) + "?" +
                (qsPattern == null ? "" : qsPattern.toString());
    }

    public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        uriPathHasWildCard = uriPath == null || uriPath.indexOf('*') >= 0;

        if (uriPath != null) {
            uriPathPattern = Pattern.compile(uriPath);
        }

        if (params.size() > 0) {
            params.forEach(param -> sortedMap.put(param.getName(), param.getValue()));
            qsPattern = createQueryStringPattern(sortedMap);
        }

        simpleUriPath = createSimpleUriPath(uriPath);
    }

    public boolean uriPathHasWildCard() {
        return uriPathHasWildCard;
    }

    public String getSimpleUriPath() {
        return simpleUriPath;
    }

    public boolean matches(String _uriPath, String _queryString) {
        return (uriPathPattern == null || uriPathPattern.matcher(_uriPath).find()) && (qsPattern == null || qsPattern
                .matcher(_queryString).find());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("uriPath=").append(uriPath);
        params.forEach(param -> sb.append(" | ").append(param));
        sb.append(" | ").append(getId());
        return sb.toString();
    }
}

