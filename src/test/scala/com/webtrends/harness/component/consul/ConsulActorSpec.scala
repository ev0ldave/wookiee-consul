package com.webtrends.harness.component.consul

import akka.testkit.{TestProbe, TestActorRef}

class ConsulActorSpec extends ConsulTestBase {

  val probe = new TestProbe(actorSystem)
  val actor:TestActorRef[ConsulActor] = TestActorRef[ConsulActor] (ConsulActor.props(ConsulSettings(actorSystem.settings.config.getConfig("wookiee-consul"))))

  Thread.sleep(2000)

  sequential

  "wookiee-consul actor" should {

    "be able to set key" in {
      probe.send(actor, KvSet("foo", "bar"))
      probe.expectMsgPF() {
        case r: String =>
          r mustEqual ""
      }
    }

    "be able to get a key and equal bar" in {
      probe.send(actor, KvGet("foo"))
      probe.expectMsgPF() {
        case r: String =>
          r mustEqual "bar"
      }
    }

    "be able to list keys" in {
      probe.send(actor, KvListKeys(""))
      probe.expectMsgPF() {
        case r: Set[String] =>
          r.contains("foo")
      }
    }

    "be able to delete a key" in {
      probe.send(actor, KvDelete("foo"))
      probe.expectMsgPF() {
        case r: Boolean =>
          r mustEqual false
      }
    }
  }
}