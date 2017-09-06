package com.webtrends.harness.component.consul

import akka.testkit.TestProbe
import com.webtrends.harness.health.{ComponentState, HealthComponent}
import com.webtrends.harness.service.messages.CheckHealth

class ConsulManagerSpec extends ConsulBaseSpec {

  "ConsulManager" should {
    "be ready" in {
      val probe = TestProbe()
      probe.send(consulManager, CheckHealth)
      probe.expectMsgPF() {
        case health: HealthComponent  =>
          health.state mustEqual ComponentState.NORMAL
        case _ =>
          false mustEqual true
      }
    }
  }
}

