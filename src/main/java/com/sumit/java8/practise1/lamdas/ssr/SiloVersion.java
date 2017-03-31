package com.sumit.java8.practise1.lamdas.ssr;

import lombok.Singular;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Fully immutable representation of silo version configuration.
 */
@SuppressWarnings("PMD.UnusedPrivateField") // TODO (kmckenney): Remove when we can used PMD 5.4 (supports Lombok)
public class SiloVersion {
    private final String siloType;
    private final String version;
    private final SourceInfo sourceInfo;
    private final List<App> apps;
    private final List<Silo> silos;
    private final BigInteger totalWeight;
    private final BigInteger totalNonExclusiveWeight;

    public SiloVersion(final String siloType, final String version, final SourceInfo sourceInfo,
                       @Singular
                       final List<App> apps, @Singular
                       final List<Silo> silos) {
        this.siloType = siloType;
        this.version = version;
        this.sourceInfo = sourceInfo;
        this.apps = Collections.unmodifiableList(apps.stream()
                // Drops duplicate Apps as per ConfigParser::parseSiloVersion
                .collect(Collectors.toMap(App::getName, Function.identity(), (app1, app2) -> app1,
                        LinkedHashMap::new))
                .values().stream()
                .map(app -> app.toBuilder().siloVersion(Optional.of(this)).build())
                .collect(Collectors.toList()));
        this.silos = silos;
        this.totalWeight = silos.parallelStream().map(Silo::getWeight).reduce(BigInteger.ZERO, BigInteger::add);
        this.totalNonExclusiveWeight = silos.parallelStream()
                .filter(silo -> !silo.exclusiveRouting())
                .map(Silo::getWeight)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public static SiloVersionBuilder builder() {
        return new SiloVersionBuilder();
    }

    public String getSiloType() {
        return this.siloType;
    }

    public String getVersion() {
        return this.version;
    }

    public SourceInfo getSourceInfo() {
        return this.sourceInfo;
    }

    public List<App> getApps() {
        return this.apps;
    }

    public List<Silo> getSilos() {
        return this.silos;
    }

    public BigInteger getTotalWeight() {
        return this.totalWeight;
    }

    public BigInteger getTotalNonExclusiveWeight() {
        return this.totalNonExclusiveWeight;
    }

    public SiloVersionBuilder toBuilder() {
        return new SiloVersionBuilder();
    }

    @XmlRootElement(name = "silo_version")
    public static class Xml implements Parser.Adaptor<SiloVersion> {
        @XmlAttribute(name = "silo_type")
        private final String siloType = null;
        @XmlAttribute
        private final String version = null;
        @XmlElement(name = "source_info")
        private final SourceInfo sourceInfo = null;
        @XmlElement(name = "app")
        private final List<App> apps = null;

        /**
         * {@inheritDoc}
         */
        public SiloVersion build() {
            return SiloVersion.builder()
                    .siloType(siloType)
                    .version(version)
                    .sourceInfo(sourceInfo)
                    .apps(apps)
                    .build();
        }
    }

    public static class SiloVersionBuilder {
        private String siloType;
        private String version;
        private SourceInfo sourceInfo;
        private ArrayList<App> apps;
        private ArrayList<Silo> silos;

        SiloVersionBuilder() {
        }

        public SiloVersion.SiloVersionBuilder siloType(String siloType) {
            this.siloType = siloType;
            return this;
        }

        public SiloVersion.SiloVersionBuilder version(String version) {
            this.version = version;
            return this;
        }

        public SiloVersion.SiloVersionBuilder sourceInfo(SourceInfo sourceInfo) {
            this.sourceInfo = sourceInfo;
            return this;
        }

        public SiloVersion.SiloVersionBuilder app(App app) {
            if (this.apps == null) this.apps = new ArrayList<App>();
            this.apps.add(app);
            return this;
        }

        public SiloVersion.SiloVersionBuilder apps(Collection<? extends App> apps) {
            if (this.apps == null) this.apps = new ArrayList<App>();
            this.apps.addAll(apps);
            return this;
        }

        public SiloVersion.SiloVersionBuilder clearApps() {
            if (this.apps != null)
                this.apps.clear();

            return this;
        }

        public SiloVersion.SiloVersionBuilder silo(Silo silo) {
            if (this.silos == null) this.silos = new ArrayList<Silo>();
            this.silos.add(silo);
            return this;
        }

        public SiloVersion.SiloVersionBuilder silos(Collection<? extends Silo> silos) {
            if (this.silos == null) this.silos = new ArrayList<Silo>();
            this.silos.addAll(silos);
            return this;
        }

        public SiloVersion.SiloVersionBuilder clearSilos() {
            if (this.silos != null)
                this.silos.clear();

            return this;
        }

        public SiloVersion build() {
            List<App> apps;
            switch (this.apps == null ? 0 : this.apps.size()) {
                case 0:
                    apps = Collections.emptyList();
                    break;
                case 1:
                    apps = Collections.singletonList(this.apps.get(0));
                    break;
                default:
                    apps = Collections.unmodifiableList(new ArrayList<App>(this.apps));
            }
            List<Silo> silos;
            switch (this.silos == null ? 0 : this.silos.size()) {
                case 0:
                    silos = Collections.emptyList();
                    break;
                case 1:
                    silos = Collections.singletonList(this.silos.get(0));
                    break;
                default:
                    silos = Collections.unmodifiableList(new ArrayList<Silo>(this.silos));
            }

            return new SiloVersion(siloType, version, sourceInfo, apps, silos);
        }

    }
}
