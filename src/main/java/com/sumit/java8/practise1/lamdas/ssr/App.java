package com.sumit.java8.practise1.lamdas.ssr;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Fully immutable representation of silo version application configuration.
 */
@SuppressWarnings("PMD.UnusedPrivateField") // TODO (kmckenney): Remove when we can used PMD 5.4 (supports Lombok)
@Getter
@Builder(toBuilder = true)
@XmlJavaTypeAdapter(AppAdapter.class)
public class App {

    private final String name;
    private final String version;
    @Singular
    private final List<HttpMatch> httpMatches;
    private Optional<SiloVersion> siloVersion;

    /**
     * Override some of the default Lombok Builder behavior.
     */
    @SuppressWarnings("unused")
    public static class AppBuilder {
        private Optional<SiloVersion> siloVersion = Optional.empty();
    }

    @XmlType(name = "app")
    public static class Xml implements Parser.Adaptor<App> {
        @XmlAttribute
        private String name;
        @XmlAttribute
        private final String version = null;
        @XmlElement(name = "http_match")
        private final List<HttpMatch> httpMatches = new ArrayList<>();

        /**
         * {@inheritDoc}
         */
        public App build() {
            return App.builder().name(name).version(version).httpMatches(httpMatches).build();
        }

        /**
         * JAXB
         */
        @SuppressWarnings("unused")
        private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
            // TODO (kmckenney): What is this? Is this valid?
            //invoicingnodewebnew -> invoicingnodeweb
            // By doing this, there can be two apps witht he same name
            // In silo_version.WEB.880.XML, there are both bizexpnodewebold and bizexpnodeweb
            name = name.replaceAll("webold$", "web").replaceAll("webnew$", "web");
        }
    }

}

/* package */ class AppAdapter extends XmlAdapter<App.Xml, App> {
    /**
     * {@inheritDoc}
     */
    @Override
    public App unmarshal(final App.Xml v) throws Exception {
        return v.build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public App.Xml marshal(final App v) throws Exception {
        throw new UnsupportedOperationException();
    }
}
