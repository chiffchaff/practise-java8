package com.sumit.java8.practise1.lamdas.ssr;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by sumijaiswal on 1/23/17.
 */
public class MainConfigLoader {
    public static void main(String args[]) throws IOException{
        final Path ssrPath = Paths.get("ssr");
        Path operationConfigPath = ssrPath.resolve("operational_config");
        // "Linked list" Multimap to maintain insertion order
        final Multimap<String, App> apps = LinkedListMultimap.create();

        final List<SiloVersion> siloVersions = LegacyParsingUtil.loadAllSiloVersions(operationConfigPath);
        final Map<String, Map<String, List<Silo>>> silosByTypeAndVersion =
                LegacyParsingUtil.loadSilos(operationConfigPath);

        //=========================================================
        // Re-arange the raw XML input to a more useful structure:
        //   App --> SiloVersion --> Silos
        //=========================================================

        siloVersions.stream().map(siloVersion -> siloVersion.toBuilder()
                .silos(silosByTypeAndVersion.getOrDefault(siloVersion.getSiloType(), Collections.emptyMap())
                        .getOrDefault(siloVersion.getVersion(), Collections.emptyList())).build())
                .map(SiloVersion::getApps).flatMap(Collection::stream).forEach(app -> apps.put(app.getName(), app));

    }


}
