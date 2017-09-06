package com.webtrends.harness.component.consul

import helm.HealthCheckParameter

import scalaz.NonEmptyList

trait ConsulMessage {
}

case class KvSet(key: String, value: AnyRef) extends ConsulMessage
case class KvGet(key: String) extends ConsulMessage
case class KvDelete(key: String) extends ConsulMessage
case class KvListKeys(prefix: String) extends ConsulMessage
case class HealthListChecksForService(service: String,
                                      datacenter: Option[String],
                                      near: Option[String],
                                      nodeMeta: Option[String]) extends ConsulMessage
case class HealthListChecksForNode(node: String,
                                   datacenter: Option[String]) extends ConsulMessage
case class HealthListNodesForService(service: String,
                                     datacenter: Option[String],
                                     near: Option[String],
                                     nodeMeta: Option[String],
                                     tag: Option[String],
                                     passingOnly: Option[Boolean]) extends ConsulMessage
case class HealthListChecksInState(state: String,
                                   datacenter: Option[String],
                                   near: Option[String],
                                   nodeMeta: Option[String]) extends ConsulMessage
case class AgentListServices() extends ConsulMessage
case class AgentRegisterService(service: String,
                                id: Option[String],
                                tags: Option[NonEmptyList[String]],
                                address: Option[String],
                                port: Option[Int],
                                enableTagOverride: Option[Boolean],
                                check: Option[HealthCheckParameter],
                                checks: Option[NonEmptyList[HealthCheckParameter]]) extends ConsulMessage
case class AgentDeregisterService(id: String) extends ConsulMessage
case class AgentEnableMaintenanceMode(id: String, enable: Boolean, reason: Option[String]) extends ConsulMessage