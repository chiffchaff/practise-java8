package com.sumit.java8.practise1.lamdas.ssr;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "param")
public class HttpMatchParam {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String value;

    public HttpMatchParam() {
    }

    public HttpMatchParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
