package com.sumit.java8.practise1.lamdas.ssr;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sumijaiswal on 1/23/17.
 */
public class LegacyParsingUtil {

    public static List<SiloVersion> loadAllSiloVersions(final Path configPath) throws IOException {
        final String siloVersionPrefix = "silo_version.";
        final String xmlSuffix = ".xml";
        try {
            return Files.walk(configPath, 1)
                    .filter(path -> {
                        final String fileName = Objects.toString(path.getFileName(), "");
                        return fileName.startsWith(siloVersionPrefix) && fileName.endsWith(xmlSuffix);
                    }).map(path -> parse(LegacyParsingUtil::parseSiloVersion, path))
                    .collect(Collectors.toList());
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    /* package */ static SiloVersion parseSiloVersion(final InputStream is) throws JAXBException {
        return Parser.parseByAdaptor(is, SiloVersion.Xml.class);
    }

    /* package */ static <R> R parse(final Throwing.Function<InputStream, R, Exception> function, final Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            try {
                return function.apply(is);
            } catch (Exception e) {
                throw new IOException(e);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * @param configPath to read SSR config from
     * @return grouping of {@link Silo} objects by silo type and version
     * @throws IOException if issues during parsing
     */
    public static Map<String, Map<String, List<Silo>>> loadSilos(final Path configPath) throws IOException {
        final Sites sites = parseSites(configPath);
        final String siloPrefix = "silo.";
        final String xmlSuffix = ".xml";
        try {

            List<Silo> silos = Files.walk(configPath, 1)
                    .filter(path -> {
                        final String fileName = path.getFileName().toString();
                        return fileName.startsWith(siloPrefix) && fileName.endsWith(xmlSuffix);
                    }).map(path -> parse(LegacyParsingUtil::parseSilo, path)).collect(Collectors.toList());

            return Files.walk(configPath, 1)
                    .filter(path -> {
                        final String fileName = path.getFileName().toString();
                        return fileName.startsWith(siloPrefix) && fileName.endsWith(xmlSuffix);
                    }).map(path -> parse(LegacyParsingUtil::parseSilo, path))
                    .filter(silo -> silo.getSite()
                            .map(site -> sites.getSites().parallelStream()
                                    .map(Site::getName)
                                    .anyMatch(site::equals))
                            .orElse(false) && silo.getSiloType().isPresent())
                    .collect(Collectors.groupingBy(silo -> silo.getSiloType().get(),
                            Collectors.groupingBy(Silo::getVersion)));
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    /* package */ static Sites parseSites(final Path configPath) throws IOException {
        try {
            return Parser.parseByAdaptor(configPath.resolve("sites.xml"), Sites.Xml.class);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    /* package */ static Silo parseSilo(final InputStream is) throws JAXBException {
        return Parser.parseByAdaptor(is, Silo.Xml.class);
    }

}
