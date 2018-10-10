plugins {
    scala
    application
}

application {
    mainClassName = "org.benji.licence.BLicence"
}

dependencies {
    compile("org.scala-lang:scala-library:2.11.12")
    compile("commons-io:commons-io:2.6")
    compile("commons-codec:commons-codec:1.11")
    compile("commons-codec:commons-codec:1.11")
    compile("com.typesafe:config:1.3.2")
    compile("org.slf4j:slf4j-api:1.7.21")
    compile("ch.qos.logback:logback-classic:1.2.3")
    testCompile("org.scalatest:scalatest_2.11:3.0.3")
    testCompile("junit:junit:4.12")
}

repositories {
    jcenter()
}
