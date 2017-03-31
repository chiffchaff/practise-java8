package com.sumit.java8.practise1.lamdas.ssr;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Fully immutable representation of site configuration.
 */
@SuppressWarnings("PMD.UnusedPrivateField") // TODO (kmckenney): Remove when we can used PMD 5.4 (supports Lombok)
@Getter
@XmlType(name = "site")
public class Site {
    @XmlAttribute
    private final String name = null;
    @XmlAttribute
    private final String description = null;
}
