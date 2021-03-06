package org.jetbrains.gradle.ext

import groovy.transform.CompileStatic
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

import javax.inject.Inject

@CompileStatic
class CopyrightConfiguration implements MapConvertible {

    String useDefault
    Map<String, String> scopes = [:]
    final NamedDomainObjectContainer<CopyrightProfile> profiles

    @Inject
    CopyrightConfiguration(Project project) {
        profiles = project.container(CopyrightProfile)
    }

    def profiles(Action<NamedDomainObjectContainer<CopyrightProfile>> action) {
        action.execute(profiles)
    }

    @Override
    Map<String, ?> toMap() {
        def map = [:]
        if (useDefault) map.put("useDefault", useDefault)
        if (!scopes.isEmpty()) {
            map.put("scopes", scopes)
        }
        if (!profiles.isEmpty()) {
            map.put("profiles", profiles.asMap.collectEntries { k, v -> [k, v.toMap()] })
        }
        return map
    }
}

@CompileStatic
class CopyrightProfile {

    final String name
    String notice
    String keyword
    String allowReplaceRegexp

    CopyrightProfile(String name) {
        this.name = name
    }

    def toMap() {
        return [
                "name"              : name,
                "notice"            : notice,
                "keyword"           : keyword,
                "allowReplaceRegexp": allowReplaceRegexp
        ]
    }
}