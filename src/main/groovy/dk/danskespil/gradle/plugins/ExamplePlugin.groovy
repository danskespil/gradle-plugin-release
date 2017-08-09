package dk.danskespil.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.DefaultTask

class ExamplePlugin implements Plugin<Project> {
    void apply(Project project) {
        project.getTasks().create('dummyTask', DefaultTask.class)
    }
}
