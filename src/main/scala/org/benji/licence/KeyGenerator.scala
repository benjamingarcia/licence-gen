package org.benji.licence

import java.io.{File, FileOutputStream}
import java.security._

import javax.crypto.Cipher

/**
  * Utility class to generate asymetric key, and contain some utility method around cryptography
  */
class KeyGenerator {

  val keygen: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
  val cipher: Cipher = Cipher.getInstance("RSA")

  /**
    * Generate key pair
    *
    * @param keylength the keysize. This is an
    * algorithm-specific metric, such as modulus length, specified in
    * number of bits.size in
    * @return the require keypair
    */
  def generateKeys(keylength: Int): KeyPair = {
    keygen.initialize(keylength)
    keygen.generateKeyPair
  }

  /**
    * Hash in SHA-256 the string receive.
    *
    * @param licenceData
    * @return hashed [[Byte]] [[Array]]
    */
  def hash(licenceData: String): Array[Byte] = {
    MessageDigest.getInstance("SHA-256").digest(licenceData.getBytes)
  }

  /**
    * Encrypt some [[Byte]] [[Array]] with the private key
    *
    * @param message Message which need to be encrypted
    * @param key private key used to encrypt
    * @return a [[Byte]] [[Array]] of encrypted message
    */
  def encryptText(message: Array[Byte], key: PrivateKey): Array[Byte] = {
    cipher.init(Cipher.ENCRYPT_MODE, key)
    cipher.doFinal(message)
  }

  /**
    * Decrypt some [[Byte]] [[Array]] with the public key
    *
    * @param message Message which need to be decrypted
    * @param key public key used to decrypt
    * @return a [[Byte]] [[Array]] of decrypted message
    */
  def decryptText(message: Array[Byte], key: PublicKey): Array[Byte] = {
    cipher.init(Cipher.DECRYPT_MODE, key)
    cipher.doFinal(message)
  }

  /**
    * Simple utility method to write a [[Byte]] [[Array]] in a file
    *
    * @param file [[File]] that will contain key
    * @param key the key which you want to save in file
    */
  def writeToFile(file: File, key: Array[Byte]): Unit = {
    file.getParentFile.mkdirs
    val fos = new FileOutputStream(file)
    fos.write(key)
    fos.flush()
    fos.close()
  }
}
