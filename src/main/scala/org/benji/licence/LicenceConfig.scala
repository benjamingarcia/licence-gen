package org.benji.licence

import com.typesafe.config.{Config, ConfigFactory}

object LicenceConfig extends Serializable {

  val ENCODING = "UTF-8"
  val PRIVATEKEYFILENAME = "private"
  val PUBLICKEYFILENAME = "public"
  val LICENCEKEYFILENAME = "licence"
  private val SAVEDFILE = "savedpath"
  private val LICENCEFILE = "licencefile"

  private val config: Config = ConfigFactory.load()

  val getSavedFile: String = config.getString(SAVEDFILE)
  val getLicenceFile: String = config.getString(LICENCEFILE)
}
