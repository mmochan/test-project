import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps.script

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.1"

project {

    buildType(Parseyaml)

    features {
        feature {
            id = "PROJECT_EXT_5"
            type = "storage_settings"
            param("secure:aws.secret.access.key", "credentialsJSON:cdb060ee-6584-479e-914f-a8dcc8f29bf1")
            param("aws.external.id", "TeamCity-server-89eb39bf-80e8-46c5-ba6d-9379769bd285")
            param("storage.name", "lambda")
            param("storage.s3.bucket.name", "origin-ig-dev-lambda-bucket")
            param("storage.type", "S3_storage")
            param("aws.access.key.id", "AKIAJHNIWIWGAFBYUQFA")
            param("aws.credentials.type", "aws.access.keys")
            param("aws.region.name", "ap-southeast-2")
            param("storage.s3.upload.presignedUrl.enabled", "true")
        }
        feature {
            id = "PROJECT_EXT_6"
            type = "active_storage"
            param("active.storage.feature.id", "PROJECT_EXT_5")
        }
    }
}

object Parseyaml : BuildType({
    name = "parseyaml"

    vcs {
        root(AbsoluteId("TestProject"))
    }

    steps {
        script {
            name = "FooBar"
            scriptContent = "echo 'Hello World'"
        }
        myMetaRunner {
            // name = "This name will be used by maven step"
            // goals = "build whatever_goal"
            // tasks = "more ant tasks"
        }
    }
    fun BuildSteps.myMetaRunner(config: MyConfigClass.() -> Unit) {
    val actualConfig = MyConfigClass() // new config instance
    actualConfig.config()  // apply closure to fill the config
    // use the config to create actual steps
    //   maven {
    //       name = actualConfig.name
    //       goals = actualConfig.goals
    //   }

    //   ant {
    //       name = actualConfig.tasks
    //   }
    }
})


class MyConfigClass {
  var name = "Default Name"
  var goals = "build"
  var tasks = "build test"
  var someUnusedProperty = 0
}