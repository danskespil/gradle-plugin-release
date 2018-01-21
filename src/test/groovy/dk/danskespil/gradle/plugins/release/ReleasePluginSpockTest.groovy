package dk.danskespil.gradle.plugins.release

import dk.danskespil.gradle.test.spock.helpers.TemporaryFolderSpecification
import org.gradle.testkit.runner.GradleRunner

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class ReleasePluginSpockTest extends TemporaryFolderSpecification {
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
