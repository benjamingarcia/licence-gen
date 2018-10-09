plugins {
    scala
    application
}

application {
    mainClassName = "org.benji.licence.BLicence"
}

dependencies {
    compile("org.scala-lang:scala-library:2.11.12")
    testCompile("org.scalatest:scalatest_2.11:3.0.0")
}

repositories {
    jcenter()
}
