package controllers

import play.api.mvc.{Action, Controller}
import models.{DeadlineTask, IndefiniteTimeTask, Task}
import org.joda.time.{DateTime, ReadableInstant}
import play.api.libs.json.Json
import play.api.libs.json._

object Tasks extends Controller{
  def list = Action { implicit request =>
    val products = Task.findAll
    Ok(views.html.main("Title")(views.html.tasks.list(products)))
  }

  def getDeadlineTasks= Action {implicit request =>

    val instantOrdering = implicitly[Ordering[ReadableInstant]]
    val result = Task.findDeadlineTasks.asInstanceOf[List[DeadlineTask]].sortBy(_.deadline.toString)
    implicit val taskWrites = new Writes[DeadlineTask] {
      def writes(task: DeadlineTask): JsValue = {
        Json.obj(
          "name" -> task.name,
          "description" -> task.description,
          "time" -> task.time.toString,
          "state" -> task.state,
          "deadline" -> task.deadline.toString()
        )
      }
    }
    Ok(Json.toJson(result))
  }

  def getIndefiniteTimeTasks = Action {implicit request =>
    val result = Task.findIndefiniteTimeTasks
    implicit val taskWrites = new Writes[Task] {
      def writes(task: Task): JsValue = {
        Json.obj(
          "name" -> task.name,
          "description" -> task.description,
          "time" -> task.time.toString,
          "state" -> task.state
        )
      }
    }
    Ok(Json.toJson(result))
  }

  def getAddedTasks = Action {implicit request =>
    val result = Task.findAddedTasks
    implicit val taskWrites = new Writes[Task] {
      def writes(task: Task): JsValue = {
        Json.obj(
          "name" -> task.name,
          "description" -> task.description,
          "time" -> task.time.toString,
          "state" -> task.state
        )
      }
    }
    Ok(Json.toJson(result))
  }

  def getDoneTasks = Action {implicit request =>
    val result = Task.findDoneTasks
    implicit val implicitFooWrites = new Writes[Task] {
      def writes(task: Task): JsValue = {
        Json.obj(
          "name" -> task.name,
          "description" -> task.description,
          "time" -> task.time.toString,
          "state" -> task.state
        )
      }
    }
    Ok(Json.toJson(result))
  }


  def getTasks = Action { implicit request =>
    val result = Task.findAll
    implicit val implicitFooWrites = new Writes[Task] {
      def writes(task: Task): JsValue = {
        Json.obj(
          "name" -> task.name,
          "description" -> task.description,
          "time" -> task.time.toString,
          "state" -> task.state
        )
      }
    }
    Ok(Json.toJson(result))
  }


}