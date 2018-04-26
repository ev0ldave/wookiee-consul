package com.webtrends.harness.component.consul

import com.webtrends.harness.utils.ConfigUtil
import com.webtrends.harness.component.Component
import com.webtrends.harness.component.consul.ConsulManager._
import com.webtrends.harness.health.{ComponentState, HealthComponent}

import scala.concurrent.Future

class ConsulManager(name:String) extends Component(name) with Consul {

  implicit val consulSettings = ConsulSettings(ConfigUtil.prepareSubConfig(config, name))

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
    Future.successful(HealthComponent(ComponentName, ComponentState.NORMAL, details = "Consul Component Up"))
  }
}

object ConsulManager {
  val ComponentName = "wookiee-consul"
}