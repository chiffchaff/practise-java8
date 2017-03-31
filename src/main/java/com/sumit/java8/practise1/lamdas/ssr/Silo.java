package com.sumit.java8.practise1.lamdas.ssr;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Fully immutable representation of silo instance configuration.
 */
@SuppressWarnings("PMD.UnusedPrivateField") // TODO (kmckenney): Remove when we can used PMD 5.4 (supports Lombok)
@Getter
public class Silo {
    private final String name;
    private final String description;
    private final Status status;
    private final long capacity;
    private final long utilization;
    private final String statusChangeTime;
    private final String version;
    private final List<String> sourceIps;
    private final BigInteger weight;
    // Synthetic
    private final Optional<String> site;
    private final Optional<String> siloType;

    @Builder
    public Silo(final String name, final String description, final Status status, final long capacity,
                final long utilization, final String statusChangeTime, final String version,
                @Singular
                final List<String> sourceIps, final BigInteger weight) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.capacity = capacity;
        this.utilization = utilization;
        this.statusChangeTime = statusChangeTime;
        this.version = version;
        this.sourceIps = sourceIps;
        this.weight = weight;
        // Synthetic
        this.site = getSite(this.name);
        this.siloType = getSiloType(this.name);
    }

    public static Optional<String> getSite(final String siloName) {
        final int dot = siloName.indexOf('.');
        return (-1 == dot) ? Optional.empty() : Optional.of(siloName.substring(0, dot));
    }

    public static Optional<String> getSiloType(final String siloName) {
        final int firstDot = siloName.indexOf('.');
        if (-1 == firstDot) {
            return Optional.empty();
        }
        final int secondDot = siloName.indexOf('.', firstDot + 1);
        return (-1 == secondDot) ? Optional.empty()
                : Optional.of(siloName.substring(firstDot + 1, secondDot));
    }

    @XmlEnum
    public enum Status {
        UP,
        DOWN,
        HALTING
    }

    public boolean exclusiveRouting() {
        return 0 < getSourceIps().size();
    }

    @XmlType(name = "silo")
    public static class Xml implements Parser.Adaptor<Silo> {
        @XmlAttribute
        private final String name = null;
        @XmlAttribute(name = "desc")
        private final String description = null;
        @XmlAttribute
        private final Silo.Status status = null;
        @XmlAttribute
        private long capacity = 0;
        @XmlAttribute(name = "utilization_pct")
        private long utilization = 0;
        @XmlAttribute(name = "status_change_time")
        private final String statusChangeTime = null;
        @XmlAttribute
        private final String version = null;
        @XmlElement(name = "source_ip")
        private final List<String> sourceIps = new ArrayList<>();

        public Silo build() {
            return Silo.builder()
                    .name(name)
                    .description(description)
                    .status(status)
                    .capacity(capacity)
                    .utilization(utilization)
                    .statusChangeTime(statusChangeTime)
                    .version(version)
                    .sourceIps(sourceIps)
                    .weight(computeWeight())
                    .build();
        }

        private BigInteger computeWeight() {
            return BigInteger.valueOf(capacity).multiply(BigInteger.valueOf(utilization));
        }
    }
}
