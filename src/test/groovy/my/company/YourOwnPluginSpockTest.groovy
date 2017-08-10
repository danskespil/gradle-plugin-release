package my.company

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class YourOwnPluginSpockTest extends Specification {
    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "The plugin can be applied"() {
        given:
        buildFile << """
            plugins {
                id 'my.company.your-own-plugin'
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

    def "When the plugin is applied, the caller can call task emitVersion and the version is printed"() {
        given:
        buildFile << """
            plugins {
                id 'my.company.your-own-plugin'
            }
            version="MY_VERSION"
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments("emitVersion")
                .withPluginClasspath()
                .build()
        then:
        result.output.contains('MY_VERSION')
        result.task(":emitVersion").outcome == SUCCESS
    }



    def "The user can tell emitVersion to write the version to a file"() {
        given:
        buildFile << """
            plugins {
                id 'my.company.your-own-plugin'
            }
            version="MY_VERSION"
            yourownplugin {
                writeToFile=file("here")
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments("emitVersion")
                .withPluginClasspath()
                .build()
        File writtenFile = new File(testProjectDir.root.getAbsolutePath()+"/here")

        then:
        result.task(":emitVersion").outcome == SUCCESS
        writtenFile.exists()
        writtenFile.text.contains("MY_VERSION")
    }

    def "When the plugin is applied without configuring the extension, no file is written"() {
        given:
        buildFile << """
            plugins {
                id 'my.company.your-own-plugin'
            }
            version="MY_VERSION"
            yourownplugin {                
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments("emitVersion")
                .withPluginClasspath()
                .build()
        File writtenFile = new File(testProjectDir.root.getAbsolutePath()+"/here")

        then:
        result.task(":emitVersion").outcome == SUCCESS
        !writtenFile.exists()
    }
}
