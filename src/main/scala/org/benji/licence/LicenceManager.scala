package org.benji.licence

import java.io.File
import java.security.{PrivateKey, PublicKey}

import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.FileUtils

import scala.util.Try

/**
  * An object to manage generation and checking licence
  */
object LicenceManager {

  /**
    * Check if a licence has not be corrupted
    *
    * @param keyGen [[KeyGenerator]] used to encrypt/decrypt licence data
    * @param publicKey [[PublicKey]] used to verify integrity
    * @param encoding default encoding format to read/write in file
    * @param licenceOutput [[File]] contain licence which need to be verified
    * @return a [[Boolean]] return at true if check is valid
    */
  def checkLicence(
      keyGen: KeyGenerator,
      publicKey: PublicKey,
      encoding: String,
      licenceOutput: File): Boolean = {
    //check licence
    val readLicence = FileUtils.readFileToString(licenceOutput, encoding)
    val splittedLicence = readLicence.split('.')
    Try {
      val hashDecrypted =
        keyGen.decryptText(Base64.decodeBase64(splittedLicence.apply(1)), publicKey)
      val plainLicence = new String(Base64.decodeBase64(splittedLicence.apply(0)), encoding)

      //to verify signature
      val checkLicence = keyGen.hash(Base64.encodeBase64String(plainLicence.getBytes()))
      hashDecrypted sameElements checkLicence
    }.getOrElse(false)
  }

  /**
    *  Generate a licence thanks to a private key and some informations
    *
    * @param keyGen [[KeyGenerator]] used to encrypt/decrypt licence data
    * @param privateKey [[PrivateKey]] used to verify integrity
    * @param encoding default encoding format to read/write in file
    * @param licenceContent Licence content
    * @param licenceOutput [[File]] contain licence which need to be verified
    */
  def generateLicence(
      keyGen: KeyGenerator,
      privateKey: PrivateKey,
      encoding: String,
      licenceContent: String,
      licenceOutput: File) = {
    // Generate Licence
    val encodedLicenceData: String = Base64.encodeBase64String(licenceContent.getBytes)
    val hashedLicenceData: Array[Byte] = keyGen.hash(encodedLicenceData)
    val sigLicenceData: String =
      Base64.encodeBase64String(keyGen.encryptText(hashedLicenceData, privateKey))
    FileUtils.write(licenceOutput, s"$encodedLicenceData.$sigLicenceData", encoding)
  }
}
