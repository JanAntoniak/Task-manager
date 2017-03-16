package controllers

import play.api.mvc.{Action, Controller}
import models._
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._


object Tasks extends Controller{

  private implicit def deadlineTaskWriter: Writes[DeadlineTask] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[String] and
      (JsPath \ "deadline").write[DateTime]
    )(unlift(DeadlineTask.unapply))

  private implicit def deadlineTaskReader: Reads[DeadlineTask] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "description").read[String] and
      (JsPath \ "time").read[Int](min(0)) and
      (JsPath \ "state").read[String] and
      (JsPath \ "deadline").read[DateTime]
    )(DeadlineTask.apply _)

  private implicit def IndefiniteTimeTaskWriter: Writes[IndefiniteTimeTask] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[String]
    )(unlift(IndefiniteTimeTask.unapply))

  private implicit def IndefiniteTimeTaskReader: Reads[IndefiniteTimeTask] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "description").read[String] and
      (JsPath \ "time").read[Int](min(0)) and
      (JsPath \ "state").read[String]
    )(IndefiniteTimeTask.apply _)

  private implicit def PeriodicTaskWriter: Writes[PeriodicTask] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String] and
      (JsPath \ "time").write[Int] and
      (JsPath \ "state").write[String] and
      (JsPath \ "period").write[Int]
    )(unlift(PeriodicTask.unapply))

  private implicit def PeriodicTaskReader: Reads[PeriodicTask] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "description").read[String] and
      (JsPath \ "time").read[Int](min(0)) and
      (JsPath \ "state").read[String] and
      (JsPath \ "period").read[Int]
    )(PeriodicTask.apply _)



  // The same as getTasks
  def list = Action { implicit request =>
    val tasks = Task.findAll
    implicit val taskWriter: Writes[Task] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "description").write[String] and
        (JsPath \ "time").write[Int] and
        (JsPath \ "state").write[String]
      )(unlift(Task.unapply))
    Ok(Json.toJson(tasks))
  }

  def getTasks = Action { implicit request =>
    val result = Task.findAll
    implicit val taskWriter: Writes[Task] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "description").write[String] and
        (JsPath \ "time").write[Int] and
        (JsPath \ "state").write[String]
      )(unlift(Task.unapply))
    Ok(Json.toJson(result))
  }

  def getAddedTasks = Action {implicit request =>
    implicit val taskWriter: Writes[Task] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "description").write[String] and
        (JsPath \ "time").write[Int] and
        (JsPath \ "state").write[String]
      )(unlift(Task.unapply))
    val result = Task.findAddedTasks
    Ok(Json.toJson(result))
  }

  def getDoneTasks = Action {implicit request =>
    implicit val taskWriter: Writes[Task] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "description").write[String] and
        (JsPath \ "time").write[Int] and
        (JsPath \ "state").write[String]
      )(unlift(Task.unapply))
    val result = Task.findDoneTasks
    Ok(Json.toJson(result))
  }

  def getIndefiniteTimeTasks = Action {implicit request =>
    val result = Task.findIndefiniteTimeTasks.asInstanceOf[List[IndefiniteTimeTask]]
    Ok(Json.toJson(result))
  }

  def getDeadlineTasks = Action {implicit request =>
    val result: List[DeadlineTask] = Task.findDeadlineTasks.sortBy(_.deadline.toString)
    Ok(Json.toJson(result))
  }

  def addDeadlineTask() = Action { implicit request =>
    val json = request.body.asJson.get
    val newTask = json.as[DeadlineTask]
    val result = Task.addNewTask(newTask)
    if(result) Ok("Added")
    else BadRequest("Error")
  }

  def addIndefiniteTask() = Action { implicit request =>
    val json = request.body.asJson.get
    val newTask = json.as[IndefiniteTimeTask]
    val result = Task.addNewTask(newTask)
    if(result) Ok("Added")
    else BadRequest("Error")
  }

  def addPeriodicTask() = Action { implicit request =>
    val json = request.body.asJson.get
    val newTask = json.as[PeriodicTask]
    val result = Task.addNewTask(newTask)
    if(result) Ok("Added")
    else BadRequest("Error")
  }
  def setDone() = Action { implicit request =>
    val json = request.body.asJson.get
    val nameReads: Reads[String] = (JsPath \ "name").read[String]
    val nameResult: JsResult[String] = json.validate[String](nameReads)
    val name = nameResult.asOpt.get
    val result = Task.setDone(name)
    if(result) Ok("Added")
    else BadRequest("Error")
  }

  def setCancelled() = Action { implicit request =>
    val json = request.body.asJson.get
    val nameReads: Reads[String] = (JsPath \ "name").read[String]
    val nameResult: JsResult[String] = json.validate[String](nameReads)
    val name = nameResult.asOpt.get
    val result = Task.setCancelled(name)
    if(result) Ok("Added")
    else BadRequest("Error")
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
    val result = Task.setDeadline(name, date)
    if(result) Ok("Added")
    else BadRequest("Error")
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
    val result = Task.changeTime(name, time)
    if(result) Ok("Added")
    else BadRequest("Error")
  }
}