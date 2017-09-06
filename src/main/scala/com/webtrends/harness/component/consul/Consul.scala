package com.webtrends.harness.component.consul

import akka.actor.ActorRef
import com.webtrends.harness.component.Component

trait Consul { this: Component =>

  var ConsulRef:Option[ActorRef] = None

  def startConsul: ActorRef = {
    ConsulRef = Some(context.actorOf(ConsulActor.props(), Consul.ConsulName))
    ConsulRef.get
  }

  def stopConsul = {
    //TODO Shutdown component
  }
}

object Consul {
  val ConsulName = "Consul"
}