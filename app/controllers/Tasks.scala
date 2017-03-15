package controllers

import models.State.State
import play.api.mvc.{Action, Controller}
import models._
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._


object Tasks extends Controller{
  def list = Action { implicit request =>
    val products = Task.findAll
    Ok(views.html.main("Title")(views.html.tasks.list(products)))
  }

  private implicit val taskFormat: Writes[Task] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[State]
    )(unlift(Task.unapply))

  private implicit val deadlineTaskWriter: Writes[DeadlineTask] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[State] and
      (JsPath \ "deadline").write[DateTime]
    )(unlift(DeadlineTask.unapply))

  private implicit val deadlineTaskReader: Reads[DeadlineTask] = (
    (JsPath \ "name").read[String](pattern("[a-zA-Z]".r,"Error. Wrong value")) and
      (JsPath \ "description").read[String] and
      (JsPath \ "time").read[Int](min(0)) and
      (JsPath \ "state").read[State] and
      (JsPath \ "deadline").read[DateTime]
    )(DeadlineTask.apply _)

  private implicit val IndefiniteTimeTaskWriter: Writes[IndefiniteTimeTask] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[State]
    )(unlift(IndefiniteTimeTask.unapply))

  private implicit val IndefiniteTimeTaskReader: Reads[IndefiniteTimeTask] = (
    (JsPath \ "name").read[String](pattern("[a-zA-Z]".r,"Error. Wrong value")) and
      (JsPath \ "description").read[String] and
      (JsPath \ "time").read[Int](min(0)) and
      (JsPath \ "state").read[State]
    )(IndefiniteTimeTask.apply _)

  private implicit val PeriodicTaskWriter: Writes[PeriodicTask] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[State] and
      (JsPath \ "period").write[Int]
    )(unlift(PeriodicTask.unapply))

  private implicit val PeriodicTaskReader: Reads[PeriodicTask] = (
    (JsPath \ "name").read[String](pattern("[a-zA-Z]".r,"Error. Wrong value")) and
      (JsPath \ "description").read[String] and
      (JsPath \ "time").read[Int](min(0)) and
      (JsPath \ "state").read[State] and
      (JsPath \ "period").read[Int]
    )(PeriodicTask.apply _)


  def getTasks = Action { implicit request =>
    val result = Task.findAll
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

  def getIndefiniteTimeTasks = Action {implicit request =>
    val result = Task.findIndefiniteTimeTasks
    Ok(Json.toJson(result))
  }

  def getDeadlineTasks = Action {implicit request =>
    val result = Task.findDeadlineTasks.asInstanceOf[List[DeadlineTask]].sortBy(_.deadline.toString)
    Ok(Json.toJson(result))
  }

  def addDeadlineTask() = Action { implicit request =>
    val json = request.body.asJson.get
    val newTask = json.as[DeadlineTask]
    val result = Task.addNewTask(newTask).toString
    Ok(result)
  }

  def addIndefiniteTask() = Action { implicit request =>
    val json = request.body.asJson.get
    val newTask = json.as[IndefiniteTimeTask]
    val result = Task.addNewTask(newTask).toString
    Ok(result)
  }

  def addPeriodicTask() = Action { implicit request =>
    val json = request.body.asJson.get
    val newTask = json.as[PeriodicTask]
    val result = Task.addNewTask(newTask).toString
    Ok(result)
  }

  def setDone() = Action { implicit request =>
    val json = request.body.asJson.get
    val name = json.as[String]
    val result = Task.setDone(name).toString
    Ok(result)
  }

  def setCancelled() = Action { implicit request =>
    val json = request.body.asJson.get
    val name = json.as[String]
    val result = Task.setDone(name).toString
    Ok(result)
  }

  def setDeadline() = Action { implicit request =>
    case class NameAndDate(name: String, date: DateTime)
    implicit val nameAndDate: Format[NameAndDate] = (
      (JsPath \ "name").format[String] and
      (JsPath \ "deadline").format[DateTime]
    )(NameAndDate.apply, unlift(NameAndDate.unapply))

    val json = request.body.asJson.get
    val nameAndDate_ = json.as[NameAndDate]
    val name = nameAndDate_.name
    val date = nameAndDate_.date
    val result = Task.setDeadline(name, date).toString
    Ok(result)
  }

  def changeTime() = Action { implicit request =>
    case class NameAndTime(name: String, time: Int)
    implicit val nameAndDate: Format[NameAndTime] = (
      (JsPath \ "name").format[String] and
        (JsPath \ "time").format[Int](min(0))
      )(NameAndTime.apply, unlift(NameAndTime.unapply))

    val json = request.body.asJson.get
    val nameAndTime_ = json.as[NameAndTime]
    val name = nameAndTime_.name
    val time = nameAndTime_.time
    val result = Task.changeTime(name, time).toString
    Ok(result)
  }
}