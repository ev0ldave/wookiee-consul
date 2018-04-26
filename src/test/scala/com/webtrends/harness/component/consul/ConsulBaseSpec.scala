package com.webtrends.harness.component.consul

import ch.qos.logback.classic.Level
import com.webtrends.harness.component.consul.config.ConsulTestConfig
import com.webtrends.harness.service.test.TestHarness
import org.specs2.mutable.SpecificationLike

class ConsulTestBase extends SpecificationLike {
  TestHarness(ConsulTestConfig.config, None, Some(Map("wookiee-consul" -> classOf[ConsulManager])), Level.ALL)
  Thread.sleep(1000)
  implicit val actorSystem = TestHarness.system.get
  val consulManager = TestHarness.harness.get.getComponent("wookiee-consul").get
}