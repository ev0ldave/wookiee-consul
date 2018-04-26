package com.webtrends.harness.component.consul

import akka.actor._
import com.webtrends.harness.app.HActor
import com.webtrends.harness.component.ComponentHelper
import helm._
import helm.ConsulOp
import helm.http4s._
import org.http4s._
import scalaz.concurrent.Task
import scalaz.NonEmptyList
import org.http4s.client.blaze.PooledHttp1Client

object ConsulActor {
  def props(settings: ConsulSettings): Props = Props(classOf[ConsulActor], settings)
}

class ConsulActor(settings: ConsulSettings) extends HActor with ComponentHelper{

  val client = PooledHttp1Client()
  val baseUri:Uri = Uri.fromString(settings.ConsulEndpoint).valueOr(throw _)

  def interpreter = new Http4sConsulClient(baseUri, client)

  override def receive = super.receive orElse {

    case KvSet(key: String, value: String) =>
      val task: Task[Unit] = helm.run(interpreter, ConsulOp.kvSet(key, value))
      sender ! task.attemptRunFor(5000L)

    case KvGet(key: String) =>
      val task: Task[Option[String]] = helm.run(interpreter, ConsulOp.kvGet(key))
      sender ! task.attemptRunFor(5000L)

    case KvDelete(key: String) =>
      val task: Task[Unit] = helm.run(interpreter, ConsulOp.kvDelete(key))
      sender ! task.attemptRunFor(5000L)

    case KvListKeys(prefix: String) =>
      val task: Task[Set[String]] = helm.run(interpreter, ConsulOp.kvListKeys(prefix))
      sender ! task.attemptRunFor(5000L)

    case HealthListChecksForService(service: String,
                                    datacenter: Option[String],
                                    near: Option[String],
                                    nodeMeta: Option[String]) =>
      val task: Task[List[HealthCheckResponse]] = helm.run(interpreter, ConsulOp.healthListChecksForService(service,
                                                           datacenter, near, nodeMeta))
      sender ! task.attemptRunFor(5000L)

    case HealthListChecksForNode(node: String, datacenter: Option[String]) =>
      val task: Task[List[HealthCheckResponse]] = helm.run(interpreter, ConsulOp.healthListChecksForNode(node,
                                                           datacenter))
      sender ! task.attemptRunFor(5000L)

    case HealthListNodesForService(service: String,
                                   datacenter: Option[String],
                                   near: Option[String],
                                   nodeMeta: Option[String],
                                   tag: Option[String],
                                   passingOnly: Option[Boolean]) =>
      val task: Task[List[HealthNodesForServiceResponse]] = helm.run(interpreter,
                                                                     ConsulOp.healthListNodesForService(service,
                                                                                                        datacenter,
                                                                                                        near,
                                                                                                        nodeMeta,
                                                                                                        tag,
                                                                                                        passingOnly))
      sender ! task.attemptRunFor(5000L)

    case AgentListServices =>
      val task: Task[Map[String,ServiceResponse]] = helm.run(interpreter, ConsulOp.agentListServices)
      sender ! task.attemptRunFor(5000L)

    case AgentRegisterService(service: String,
                              id: Option[String],
                              tags: Option[NonEmptyList[String]],
                              address: Option[String],
                              port: Option[Int],
                              enableTagOverride: Option[Boolean],
                              check: Option[HealthCheckParameter],
                              checks: Option[NonEmptyList[HealthCheckParameter]]) =>
      val task: Task[Unit] = helm.run(interpreter, ConsulOp.agentRegisterService(service, id, tags, address, port,
                                                                                 enableTagOverride, check, checks))
      sender ! task.attemptRunFor(5000L)

    case AgentDeregisterService(id: String) =>
      val task: Task[Unit] = helm.run(interpreter, ConsulOp.agentDeregisterService(id))
      sender ! task.attemptRunFor(5000L)
  }
}
