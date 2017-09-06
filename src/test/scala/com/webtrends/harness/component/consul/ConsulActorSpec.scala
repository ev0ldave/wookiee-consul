package com.webtrends.harness.component.consul

import akka.testkit.{TestProbe, TestActorRef}

class ConsulActorSpec extends ConsulBaseSpec {

  val probe = new TestProbe(actorSystem)
  val actor:TestActorRef[ConsulActor] = TestActorRef[ConsulActor] (ConsulActor.props())

  Thread.sleep(2000)

  sequential

  "wookiee-consul actor" should {

    "be able to set key" in {
      probe.send(actor, KvSet("key1", "foo"))
      probe.expectMsgPF() {
        case r: Boolean =>
          r mustEqual true
      }
    }

    "be able to get a key and equal foo" in {
      probe.send(actor, KvGet("key1"))
      probe.expectMsgPF() {
        case r: String =>
          r mustEqual "foo"
      }
    }

    "be able to delete key" in {
      probe.send(actor, KvDelete("key1"))
      probe.expectMsgPF() {
        case r: Boolean =>
          r mustEqual true
      }

    }

    /*"fail with a get key" in {
      probe.send(actor, GetKey("key1"))
      probe.expectMsgPF() {
        case Failure(t) =>
          throwA(t)
      }
    }*/
  }
}