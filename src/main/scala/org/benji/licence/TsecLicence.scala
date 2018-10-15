package org.benji.licence

import java.io.File
import java.security.KeyPair

import org.benji.licence.LicenceConfig._
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory

object TsecLicence extends App {

  val LOGGER = LoggerFactory.getLogger(TsecLicence.getClass)

  // save folder for keys
  private val privateKey: File = new File(s"$getSavedFile${File.separator}$PRIVATEKEYFILENAME")
  private val publicKey: File = new File(s"$getSavedFile${File.separator}$PUBLICKEYFILENAME")
  private val licenceOutput: File = new File(s"$getSavedFile${File.separator}$LICENCEKEYFILENAME")

  LOGGER.info("Let's create org.benji.licence \uD83D\uDD12")

  // generate class
  LOGGER.debug("Instantiate keyGenerator")
  private val keyGen = new KeyGenerator()
  private val security: KeyPair = keyGen.generateKeys(2048)

  LOGGER.debug("Save keys")
  keyGen.writeToFile(privateKey, security.getPrivate.getEncoded)
  keyGen.writeToFile(publicKey, security.getPublic.getEncoded)

  //load conf
  LOGGER.debug("Open org.benji.licence file")
  val licenceContent: String =
    FileUtils.readFileToString(new File(s"$getLicenceFile"), ENCODING)

  LOGGER.debug("Generate org.benji.licence")
  LicenceManager.generateLicence(
    keyGen,
    security.getPrivate,
    ENCODING,
    licenceContent,
    licenceOutput)

  LOGGER.debug("Check org.benji.licence")
  val checked = LicenceManager.checkLicence(keyGen, security.getPublic, ENCODING, licenceOutput)

  if (checked) {
    LOGGER.info("Licence generated and valid ✅")
  }
  else {
    LOGGER.info("Licence invalid : error on check ❌")
  }
}
