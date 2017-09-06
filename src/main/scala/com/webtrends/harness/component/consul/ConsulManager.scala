package com.webtrends.harness.component.consul

import com.webtrends.harness.utils.ConfigUtil
import com.webtrends.harness.component.Component
import com.webtrends.harness.component.consul.ConsulManager._
import com.webtrends.harness.health.HealthComponent

import scala.concurrent.Future

class ConsulManager(name:String) extends Component(name) with Consul {

  override protected def defaultChildName: Option[String] = Some(Consul.ConsulName)

  override def start = {
    startConsul
    super.start
  }

  override def stop = {
    stopConsul
    super.stop
  }

  override def getHealth: Future[HealthComponent] = {
    Future.successful(HealthComponent(ComponentName, details = "Consul Component Up"))
  }
}

object ConsulManager {
  val ComponentName = "wookiee-consul"
}