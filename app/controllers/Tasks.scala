package controllers

import play.api.mvc.{Action, Controller}
import models.Task

/**
  * Created by jan on 12.03.17.
  */
object Tasks extends Controller{
  def list = Action { implicit request =>
    val products = Task.findAll
    Ok(views.html.main("Title")(views.html.tasks.list(products)))
  }
}