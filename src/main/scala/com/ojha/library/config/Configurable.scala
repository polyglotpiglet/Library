package com.ojha.library.config

import com.typesafe.config.ConfigFactory

/**
 * Created by alexandra on 04/05/15.
 */
trait Configurable {
  lazy val config = ConfigFactory.load()
}
