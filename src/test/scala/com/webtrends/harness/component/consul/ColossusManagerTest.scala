package com.webtrends.harness.component.consul

import akka.testkit.TestProbe
import com.typesafe.config.ConfigFactory
import com.webtrends.harness.health.{ComponentState, HealthComponent}
import com.webtrends.harness.service.messages.CheckHealth
import com.webtrends.harness.service.test.TestHarness
import org.scalatest.{MustMatchers, WordSpecLike}

class ConsulManagerTest extends MustMatchers with WordSpecLike {
  val th = TestHarness(ColossusManagerTest.config, None, Some(Map(ConsulManager.ComponentName -> classOf[ConsulManager])))
  val colManager = th.getComponent(ConsulManager.ComponentName).get
  implicit val svc = TestHarness.system.get

  "ColossusManager" should {
    "be ready" in {
      val probe = TestProbe()
      probe.send(colManager, CheckHealth)
      probe.expectMsgPF() {
        case health: HealthComponent  =>
          health.state mustEqual ComponentState.NORMAL
        case _ =>
          false mustEqual true
      }
    }
  }
}

object ColossusManagerTest {
  val config = ConfigFactory.parseString("""
      wookiee-consul {
      }
  """)
}
