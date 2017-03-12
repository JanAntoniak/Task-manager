package models

import java.util.Optional

import models.State._
import org.joda.time.DateTime
import play.libs.Json

/**
  *  name - name of the task
  *  description - a short description of what to do
  *  time - time the task is expected to be finished in
  *  state - state of a task, can be 'added' to system, already 'done' or cancelled
  */

abstract class Task {
  val name: String
  val description: String
  val time: Integer
  val state: State

  override def toString: String = "Task: name = " + name + ", description = " + description +
    ", time = " + time + ", state = " + state
}

object Task {
  var tasks = Set(
    DeadlineTask("task1", "Create a web application about task management", 10, ADDED,
      new DateTime(2017, 3, 17, 0, 0, 0)),
    IndefiniteTimeTask("task2", "description of taks2", 1, ADDED)
  )

  def findByName(name: String): Option[Task with Product with Serializable] = tasks.find(_.name == name)
  def findAll: List[Task] = tasks.toList.sortBy(Task => Task.name)
}
