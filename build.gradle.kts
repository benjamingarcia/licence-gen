plugins {
    scala
    application
}

application {
    mainClassName = "org.benji.licence.TsecLicence"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compile("io.github.jmcardon:tsec-password_2.11:0.0.1-RC1")
    compile("org.scala-lang:scala-library:2.11.12")
    compile("commons-io:commons-io:2.6")
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
