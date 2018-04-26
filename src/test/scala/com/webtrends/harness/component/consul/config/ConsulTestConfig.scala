package com.webtrends.harness.component.consul.config

import com.webtrends.harness.service.test.config.TestConfig

object ConsulTestConfig {
  val config = TestConfig.conf("""
      wookiee-consul {
        consul-endpoint = "%s"
      }
  """.format(System.getProperty("consul-url", "http://localhost:8500")))
}
