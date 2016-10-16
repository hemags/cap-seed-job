

freeStyleJob('engine') {
    // name 'digital-item/prod1-engine'
    description("Do not change this config directly.")
    // jdk("JDK 1.7.0_67")
    label 'ms_docker'
    wrappers {
        timestamps()
        environmentVariables {
          env("JAVA_HOME", jdk_8)
          env("PATH", jdk_8+"/bin"+":\$PATH")
        }
    }
    logRotator(-1, 10000, 5, -1)
    scm {
        git {
            remote {
                url("git@git.github.com:prod1/prod1-master.git")
                refspec '+refs/pull/*:refs/remotes/origin/pr/*'
            }
            wipeOutWorkspace true
            branch '${sha1}'
        }
    }
    // antInstallation('Ant-1.9.0')
    triggers {
        pullRequest {
            admins adminList
            triggerPhrase 'Ok to test'
            useGitHubHooks true
            permitAll true
            cron '*/2 * * * *'
        }

        githubPush()
        scm("* * * * *")
    }

    steps {
        gradle 'build'
        // gradle 'integrationTest publishIntegrationTestResult'
        // shell("scp -r build/reports/tests/ ubuntu@10.63.170.185:Images/digital-item/integration-test/")
        // shell("scp -r build/distributions/prod1-master-0.1-ALPHA.zip ubuntu@10.63.170.185:~ubuntu/Images/prod1/engine/")
    }
}
