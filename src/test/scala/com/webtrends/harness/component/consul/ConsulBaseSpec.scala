package com.webtrends.harness.component.consul

import ch.qos.logback.classic.Level
import com.webtrends.harness.service.test.TestHarness
import org.specs2.mutable.SpecificationLike
import com.webtrends.harness.service.test.config.TestConfig

class ConsulBaseSpec extends SpecificationLike {
  TestHarness(TestConfig.conf(""""""), None, Some(Map("wookiee-consul" -> classOf[ConsulManager])), Level.ALL)
  Thread.sleep(1000)
  implicit val actorSystem = TestHarness.system.get
  val consulManager = TestHarness.harness.get.getComponent("wookiee-consul").get
}