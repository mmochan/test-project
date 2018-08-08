defaultTasks "testMe"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath group: 'org.yaml', name: 'snakeyaml', version: '1.19'
    }
}

def cfg = new org.yaml.snakeyaml.Yaml().load( new File("cfg.yml").newInputStream() )

task testMe( ){
    doLast {
        println "make "
        println "profile =  ${cfg.spring.profiles.active}"
        assert cfg.spring.profiles.active == "prod"
    }
}