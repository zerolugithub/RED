/*
 * Copyright 2017 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.launch.local;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import com.google.common.base.CaseFormat;

class RobotPathsNaming {

    static String createTopLevelSuiteName(final Collection<IResource> dataSources) {
        if (dataSources.size() < 2) {
            return "";
        }
        return dataSources.stream()
                .map(IResource::getLocation)
                .map(IPath::removeFileExtension)
                .map(IPath::lastSegment)
                .map(RobotPathsNaming::toRobotFrameworkName)
                .collect(joining(" & "));
    }

    static List<String> createSuiteNames(final Map<IResource, List<String>> resourcesMapping,
            final String topLevelSuiteName, final Function<IResource, List<String>> pathSegmentsMapper) {
        return resourcesMapping.keySet()
                .stream()
                .map(pathSegmentsMapper::apply)
                .map(pathSegments -> RobotPathsNaming.createSuiteName(topLevelSuiteName, pathSegments))
                .filter(name -> !name.isEmpty())
                .distinct()
                .collect(toList());
    }

    static List<String> createTestNames(final Map<IResource, List<String>> resourcesMapping,
            final String topLevelSuiteName, final Function<IResource, List<String>> pathSegmentsMapper) {
        return resourcesMapping.entrySet().stream().flatMap(e -> {
            final List<String> pathSegments = pathSegmentsMapper.apply(e.getKey());
            return e.getValue()
                    .stream()
                    .map(testName -> RobotPathsNaming.createTestName(topLevelSuiteName, pathSegments, testName));
        }).filter(name -> !name.isEmpty()).distinct().collect(toList());
    }

    static String createSuiteName(final String topLevelSuiteName, final List<String> pathSegments) {
        return Stream
                .concat(Stream.of(topLevelSuiteName), pathSegments.stream().map(RobotPathsNaming::toRobotFrameworkName))
                .filter(segment -> !segment.isEmpty())
                .collect(joining("."));
    }

    static String createTestName(final String topLevelSuiteName, final List<String> pathSegments,
            final String testName) {
        return createSuiteName(topLevelSuiteName, pathSegments) + "." + testName;
    }

    private static String toRobotFrameworkName(final String name) {
        // converts suite/test name to name used by RF
        String resultName = name;
        final int prefixIndex = resultName.indexOf("__");
        if (prefixIndex != -1) {
            resultName = resultName.substring(prefixIndex + 2);
        }
        resultName = resultName.replaceAll("_+", " ");
        return Stream.of(resultName.split(" "))
                .map(namePart -> CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, namePart))
                .collect(joining(" "));
    }
}
