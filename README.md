[![Build Status](https://travis-ci.org/danskespil/gradle-plugin-terraform.svg?branch=master)](https://travis-ci.org/danskespil/gradle-plugin-terraform)

# gradle-plugin-release
Under Construction

Handling releases. Based on ajoberstar's gradle plugins and inspired by netflix nebula gradle plugin

Aims to provide 2 plugins:

1. release-classic

included like this
```groovy
plugins {
    id 'dk.danskespil.gradle.plugins.release-classic' version 'VERSION'
}
```

2. release-branch
included like this
```groovy
plugins {
    id 'dk.danskespil.gradle.plugins.release-branch' version 'VERSION'
}
```

# Migrate into using this plugin
## local configuration into release-classic
Replace
```groovy
plugins {
    // ajoberstar working example: https://github.com/katharsis-project/katharsis-vertx/blob/master/build.gradle
    id 'org.ajoberstar.grgit' version '1.7.2'
    id 'org.ajoberstar.release-opinion' version '1.7.2'
}

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
with
```groovy
plugins {
    id 'dk.danskespil.gradle.plugins.release-classic' version 'VERSION'
}
```
## local configuration or release-classic into release-branch
TODO (write it when you know exactly how it works)

# TODO
Lots of stuff, I am sure, but the overall vision is like so:

* DONE Get the basic plugin working with git, travis, oss, ...
  * DONE use latest gradle version.
* DONE Publish it to gradle plugin portal
* DONE Build a plugin that configures ajoberstar like we currently do at Danske Spil (see AS IS Strategy) 
* DONE Replace the current release configuration at some current Danske Spil project with this plugin and validate that it is works the same way
* DONE When releasing this plugin, use its own code to release it
* DONE Configure the plugin to (allow for ?) inclusion of branch name in release tags
* DONE Figure out how multiple gradle plugin portal users can deploy the same plugin

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
## TO BE Strategy classic
This is how we envision it to be done after writing this plugin
```groovy
plugins {
    id 'dk.danskespil.gradle.plugins.release-classic' version '0.1.2'
}
// all the usual, e.g.
gw clean build release -Prelease.stage=milestone -Prelease.scope=patch
```

## TO BE Strategy branch
This is how we envision it to be done after writing this plugin
```groovy
plugins {
    id 'dk.danskespil.gradle.plugins.release-branch' version '0.1.2'
}
// now you have this extra stage 'branch'
gw clean build release -Prelease.stage=branch -Prelease.scope=patch
```

# Releasing this plugin
## To gradles plugin portal
This is done for you by travis. You must "release" first, e.g. create a tag, and then push to the release branch, e.g. something like this
```groovy
git checkout master
gw clean build release -Prelease.stage=final -Prelease.scope=patch
git checkout release
git push
```

# Versions
## 0.0.2
* published to gradle portal
* Configures release plugin, but not exactly like done at Danske Spil

## 0.0.3
* If you use this version of the plugin, you will have the same configuration as when including "AS IS Strategy"

## 0.0.4
* Fix missing call to grgit.open(), causing "Caused by: java.lang.NullPointerException: Cannot invoke method status() on null object"

## 0.0.5
* rename plugin id to dk.danskespil.gradle.plugins.release-classic

## 0.0.8 
* release-branch plugin has initial release

## 0.0.9 
* Bump underlying ajoberstar plugin

## 0.1.0 
* Changed branch strategy by shortening the branch name by replacing / with -

## 0.1.1 
* Changed branch strategy by adding a count to the shortened branch name

## 0.1.2 
* Changed branch strategy by also replacing _ with -

## 0.1.3 
* Allow multiple users to publish to gradle portal

## 0.1.8 
* Testing releasing to gradle portal

