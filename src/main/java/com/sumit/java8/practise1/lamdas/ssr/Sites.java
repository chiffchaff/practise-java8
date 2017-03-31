package com.sumit.java8.practise1.lamdas.ssr;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Fully immutable representation of sites configuration.
 */
@Getter
@Builder
public class Sites {
    @Singular
    private final List<Site> sites;

    @XmlType(name = "sites")
    public static class Xml implements Parser.Adaptor<Sites> {
        @XmlElement(name = "site")
        private final List<Site> sites = new ArrayList<>();

        public Sites build() {
            return Sites.builder().sites(sites).build();
        }
    }
}
