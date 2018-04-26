package com.webtrends.harness.component.consul

import com.typesafe.config.{Config, ConfigFactory}

class ConsulSettings (config: Config = ConfigFactory.load) {

  // What is the consul url
  var ConsulEndpoint         = config getString       "consul-endpoint"

  require(ConsulEndpoint   != "", "consul-endpoint must be set")
}

object ConsulSettings {
  implicit def apply(config: Config = ConfigFactory.load()) : ConsulSettings = new ConsulSettings(config)
}
