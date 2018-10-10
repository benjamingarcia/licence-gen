package org.benji.licence

import java.io.File
import java.security.KeyPair

import LicenceConfig._
import LicenceManager._
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.io.FileUtils
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class LicenceManagerTest extends FlatSpec with Matchers {

  it should "validate licence with keypair generated" in {
    val config: Config = ConfigFactory.load()
    val privateKey: File =
      new File(s"$getSavedFile${File.separator}$PRIVATEKEYFILENAME")
    val publicKey: File =
      new File(s"$getSavedFile${File.separator}$PUBLICKEYFILENAME")
    val licenceOutput: File =
      new File(s"$getSavedFile${File.separator}$LICENCEKEYFILENAME")

    val keyGen = new KeyGenerator()
    val security: KeyPair = keyGen.generateKeys(2048)

    keyGen.writeToFile(privateKey, security.getPrivate.getEncoded)
    keyGen.writeToFile(publicKey, security.getPublic.getEncoded)

    //load conf
    val licenceContent: String =
      FileUtils.readFileToString(
        new File(getClass.getClassLoader.getResource("licence.json").toURI),
        ENCODING)

    generateLicence(keyGen, security.getPrivate, ENCODING, licenceContent, licenceOutput)

    assert(checkLicence(keyGen, security.getPublic, ENCODING, licenceOutput))
  }

  it should "not validate licence with different keypair" in {

    val config: Config = ConfigFactory.load()

    //first licence
    val privateKey: File =
      new File(s"$getSavedFile${File.separator}$PRIVATEKEYFILENAME")
    val publicKey: File =
      new File(s"$getSavedFile${File.separator}$PUBLICKEYFILENAME")
    val licenceOutput: File =
      new File(s"$getSavedFile${File.separator}$LICENCEKEYFILENAME")

    val keyGen = new KeyGenerator()
    val security: KeyPair = keyGen.generateKeys(2048)

    keyGen.writeToFile(privateKey, security.getPrivate.getEncoded)
    keyGen.writeToFile(publicKey, security.getPublic.getEncoded)

    //load conf
    val licenceContent: String =
      FileUtils.readFileToString(
        new File(getClass.getClassLoader.getResource("licence.json").toURI),
        ENCODING)

    LicenceManager.generateLicence(
      keyGen,
      security.getPrivate,
      ENCODING,
      licenceContent,
      licenceOutput)

    //second licence
    val privateKey2: File =
      new File(s"$getSavedFile${File.separator}${PRIVATEKEYFILENAME}2")
    val publicKey2: File =
      new File(s"$getSavedFile${File.separator}${PUBLICKEYFILENAME}2")
    val licenceOutput2: File =
      new File(s"$getSavedFile${File.separator}${LICENCEKEYFILENAME}2")

    val security2: KeyPair = keyGen.generateKeys(2048)

    keyGen.writeToFile(privateKey2, security2.getPrivate.getEncoded)
    keyGen.writeToFile(publicKey2, security2.getPublic.getEncoded)


    assert(!LicenceManager.checkLicence(keyGen, security2.getPublic, ENCODING, licenceOutput))
  }
}
