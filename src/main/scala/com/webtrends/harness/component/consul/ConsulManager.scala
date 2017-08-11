/*
 * Copyright (c) 2014. Webtrends (http://www.webtrends.com)
 * @author cuthbertm on 11/20/14 12:16 PM
 */
package com.webtrends.harness.component.consul

import akka.actor.ActorSystem
import com.webtrends.harness.command.CommandHelper
import com.webtrends.harness.component.Component
import com.webtrends.harness.component.consul.ConsulManager._
import com.webtrends.harness.health.HealthComponent
import org.http4s.client.blaze.PooledHttp1Client

import scala.concurrent.{ExecutionContextExecutor, Future}

class ConsulManager(name:String) extends Component(name) with CommandHelper {
  implicit val system: ActorSystem = context.system
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  override def start = {
    // TODO Put your setup code here
    val client = PooledHttp1Client()
    super.start
  }

  override def stop = {
    // TODO Put your teardown here
    super.stop
  }

  override def getHealth: Future[HealthComponent] = {
    Future.successful(HealthComponent(ComponentName, details = "Consul Component Up"))
  }
}

object ConsulManager {
  val ComponentName = "wookiee-consul"
}