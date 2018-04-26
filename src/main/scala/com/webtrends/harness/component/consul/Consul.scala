package com.webtrends.harness.component.consul

import akka.actor.ActorRef
import com.webtrends.harness.component.Component

trait Consul { this: Component =>

  implicit val consulSettings:ConsulSettings

  var ConsulRef:Option[ActorRef] = None

  def startConsul: ActorRef = {
    ConsulRef = Some(context.actorOf(ConsulActor.props(consulSettings:ConsulSettings), Consul.ConsulName))
    ConsulRef.get
  }

  def stopConsul = {
    //TODO Shutdown component
  }
}

object Consul {
  val ConsulName = "Consul"
}