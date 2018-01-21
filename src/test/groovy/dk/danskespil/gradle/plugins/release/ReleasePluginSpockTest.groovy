package dk.danskespil.gradle.plugins.release

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class ReleasePluginSpockTest extends Specification {
    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "The plugin can be applied"() {
        given:
        buildFile << """
            plugins {
                id 'dk.danskespil.gradle.plugins.release'
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
