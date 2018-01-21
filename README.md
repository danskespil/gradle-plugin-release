[![Build Status](https://travis-ci.org/danskespil/gradle-plugin-terraform.svg?branch=master)](https://travis-ci.org/danskespil/gradle-plugin-terraform)

# gradle-plugin-release
Under Construction

Handling releases. Based on ajoberstar xx gradle plugins and inspired by netflix nebula gradle plugin

# TODO
Lots of stuff, I am sure, but the overall vision is like so:

* DONE Get the basic plugin working with git, travis, oss, ...
  * DONE use latest gradle version.
* Build a plugin that configures ajoberstar like we currently do at Danske Spil (see AS IS Strategy) 
* Publish it to gradle plugin portal
* Replace the current release configuration at some current Danske Spil project with this plugin and validate that it is works the same way
* Let the plugin be released by itself
* Configure the plugin to (allow for ?) inclusion of branch name in release tags

# Strategies

## AS IS Strategy
This is how its done before writing this plugin
```groovy
plugins {
    // ajoberstar working example: https://github.com/katharsis-project/katharsis-vertx/blob/master/build.gradle
    id 'org.ajoberstar.grgit' version '1.7.1'
    id 'org.ajoberstar.release-opinion' version '1.7.1'
}

group 'dk.danskespil.gradle.plugins'

apply plugin: 'java'

//// START This block has to be after "apply plugin: 'maven'"
// https://github.com/ajoberstar/gradle-git/wiki/Release%20Plugins
// to deploy as maven artifact
apply plugin: 'maven'

import org.ajoberstar.gradle.git.release.opinion.Strategies

release {
    grgit = org.ajoberstar.grgit.Grgit.open()
    defaultVersionStrategy = Strategies.SNAPSHOT
    versionStrategy Strategies.SNAPSHOT
    tagStrategy {
        prefixNameWithV = false // defaults to true
        generateMessage = { version -> "My new version $version.version" }
    }
}
```
## TO BE Strategy
This is how we envision it to be done after writing this plugin
```groovy
plugins {
    // ajoberstar working example: https://github.com/katharsis-project/katharsis-vertx/blob/master/build.gradle
    id 'org.danskespil.gradle.plugins.release' version '0.0.1'
}

dsRelease {
    whateverFlagsWeNeedToMakeThisWorkTheWayWeWantTo = true
}
```