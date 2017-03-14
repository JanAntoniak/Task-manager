package models

import models.State._
import org.joda.time.DateTime



/**
  *  name - name of the task
  *  description - a short description of what to do
  *  time - time the task is expected to be finished in
  *  state - state of a task, can be 'added' to system, already 'done' or cancelled
  */

abstract class Task {

  def name: String
  def description: String
  def time: Int
  def state: State

  override def toString: String = "Task: name = " + name + ", description = " + description +
    ", time = " + time + ", state = " + state
}

object Task {

  def unapply(task: Task) = if(task == null) None else Some(task.name, task.description, task.time, task.state)

  def findDeadlineTasks: List[Task] = {
    val all = findAll
    val result = all flatMap {
      case task : DeadlineTask => Some(task)
      case _ => None
    }
    result
  }

  def findIndefiniteTimeTasks: List[Task] = {
    val all = findAll
    val result = all flatMap {
      case task : IndefiniteTimeTask => Some(task)
      case _ => None
    }
    result
  }

  def findAddedTasks: List[Task] = findAll.filter(_.state == ADDED)

  def findDoneTasks: List[Task] = findAll.filter(_.state == DONE)

  def findByName(name: String): Option[Task with Product with Serializable] = tasks.find(_.name == name)

  def findAll: List[Task] = tasks.toList.sortBy(_.name)

  def addNewTask(): Unit = {

  }

  def setDone(name: String): Boolean = {
    val taskFound = findByName(name)
    if (taskFound.nonEmpty && taskFound.get.state == ADDED) {
      val task = taskFound.get
      val newTask = task match {
        case _ : DeadlineTask => task.asInstanceOf[DeadlineTask].copy(state = DONE)
        case _ : IndefiniteTimeTask => task.asInstanceOf[IndefiniteTimeTask].copy(state = DONE)
        case _ : PeriodicTask => task.asInstanceOf[PeriodicTask].copy(state = DONE)
      }
      tasks += newTask
      tasks -= task
      true
    }
    else false
  }

  def setCancelled(name: String): Boolean = {
      val taskFound = findByName(name)
      if (taskFound.nonEmpty && taskFound.get.state == ADDED) {
        val task = taskFound.get
        val newTask = task match {
          case _: DeadlineTask => task.asInstanceOf[DeadlineTask].copy(state = CANCELLED)
          case _: IndefiniteTimeTask => task.asInstanceOf[IndefiniteTimeTask].copy(state = CANCELLED)
          case _: PeriodicTask => task.asInstanceOf[PeriodicTask].copy(state = CANCELLED)
        }
        tasks += newTask
        tasks -= task
        true
      }
      else false
  }

  def setDeadline(): Unit = {

  }

  /**
    *  changes the time the task is expected to be finished in
    */
  def changeTime(): Unit = {

  }

  def generateReport(): Unit = {

  }

  var tasks = Set(
    DeadlineTask("task1", "Create a web application about task management", 10, ADDED,
      new DateTime(2017, 3, 17, 0, 0, 0)),
    IndefiniteTimeTask("task2", "description of taks2", 1, ADDED),
    PeriodicTask("task3", "description of task 3", 2, DONE, 11),
    PeriodicTask("task4", "description of task 4", 3, ADDED, 24),
    DeadlineTask("task5", "description of task 5", 7, CANCELLED,
      new DateTime(2017, 4, 15, 0, 0, 0)),
    DeadlineTask("task6", "description of task 6", 5, CANCELLED,
      new DateTime(2017, 1, 11, 0, 0, 0))
  )
}
