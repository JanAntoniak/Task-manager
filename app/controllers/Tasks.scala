package controllers

import models.State.State
import play.api.mvc.{Action, Controller}
import models._
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.libs.functional.syntax._


object Tasks extends Controller{
  def list = Action { implicit request =>
    val products = Task.findAll
    Ok(views.html.main("Title")(views.html.tasks.list(products)))
  }

  private implicit val taskWrites: Writes[Task] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[State]
    )(unlift(Task.unapply))

  private implicit val deadlineTaskWrites: Writes[DeadlineTask] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[State] and
      (JsPath \ "deadline").write[DateTime]
    )(unlift(DeadlineTask.unapply))

  def getDeadlineTasks= Action {implicit request =>
    val result = Task.findDeadlineTasks.asInstanceOf[List[DeadlineTask]].sortBy(_.deadline.toString)
    Ok(Json.toJson(result))
  }

  def getIndefiniteTimeTasks = Action {implicit request =>
    val result = Task.findIndefiniteTimeTasks
    Ok(Json.toJson(result))
  }

  def getAddedTasks = Action {implicit request =>
    val result = Task.findAddedTasks
    Ok(Json.toJson(result))
  }

  def getDoneTasks = Action {implicit request =>
    val result = Task.findDoneTasks
    Ok(Json.toJson(result))
  }


  def getTasks = Action { implicit request =>
    val result = Task.findAll
    Ok(Json.toJson(result))
  }


}