package dk.danskespil.gradle.plugins.common

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class CommonTasksPluginTest extends Specification {
    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "The plugin can be applied"() {
        given:
        buildFile << """
            plugins {
                id 'dk.danskespil.gradle.plugins.example'
            }
            task helloWorld {
                doLast {
                    println 'Hello world!'
                }
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('helloWorld')
                .withPluginClasspath()
                .build()

        then:
        result.task(":helloWorld").outcome == SUCCESS
    }
}
