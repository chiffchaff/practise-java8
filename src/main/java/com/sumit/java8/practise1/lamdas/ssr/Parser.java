package com.sumit.java8.practise1.lamdas.ssr;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public abstract class Parser {

    public static <T> T parse(final Path configPath, Class<T> type) throws JAXBException, IOException {
        if (log.isInfoEnabled()) {
            log.info("Config path for " + type + " materialization: " + configPath);
        }
        try (final InputStream inputStream = Files.newInputStream(configPath)) {
            return parse(inputStream, type);
        }
    }

    public static <T> T parse(final InputStream inputStream, Class<T> type) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(type);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(new StreamSource(inputStream), type).getValue();
    }

    public static <T extends Adaptor<R>, R> R parseByAdaptor(final Path configPath, Class<T> type)
            throws JAXBException, IOException {
        return parse(configPath, type).build();
    }

    public static <T extends Adaptor<R>, R> R parseByAdaptor(final InputStream inputStream, Class<T> type)
            throws JAXBException {
        return parse(inputStream, type).build();
    }

    /**
     * Implemented by JAXB mappings that are used to build an immutable instance of {@code <T>}
     *
     * @param <T> immutable type
     */
    public interface Adaptor<T> {
        T build();
    }

}
