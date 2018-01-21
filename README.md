[![Build Status](https://travis-ci.org/danskespil/gradle-plugin-terraform.svg?branch=master)](https://travis-ci.org/danskespil/gradle-plugin-terraform)

# gradle-plugin-release
Under Construction

Handling releases. Based on ajoberstar's gradle plugins and inspired by netflix nebula gradle plugin

# TODO
Lots of stuff, I am sure, but the overall vision is like so:

* DONE Get the basic plugin working with git, travis, oss, ...
  * DONE use latest gradle version.
* DONE Publish it to gradle plugin portal
* Build a plugin that configures ajoberstar like we currently do at Danske Spil (see AS IS Strategy) 
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
    id 'dk.danskespil.gradle.plugins.release' version '0.0.4'
}

dsRelease {
    whateverFlagsWeNeedToMakeThisWorkTheWayWeWantTo = true
}
```

# Versions
## 0.0.2
* published to gradle portal
* Configures release plugin, but not exactly like done at Danske Spil

## 0.0.3
* If you use this version of the plugin, you will have the same configuration as when including "AS IS Strategy"

## 0.0.4
* Fix missing call to grgit.open(), causing "Caused by: java.lang.NullPointerException: Cannot invoke method status() on null object"
