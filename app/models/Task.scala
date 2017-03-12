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
  def time: Integer
  def state: State

  override def toString: String = "Task: name = " + name + ", description = " + description +
    ", time = " + time + ", state = " + state
}

object Task {
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

  def findDeadlineTasks: List[Task] = {
    val all = findAll
    val result = all flatMap {
      case _ : IndefiniteTimeTask => None
      case _ : PeriodicTask => None
      case other => Some(other)
    }
    result
  }

  def findIndefiniteTimeTasks: List[Task] = {
    val all = findAll
    val result = all flatMap {
      case _ : DeadlineTask => None
      case _ : PeriodicTask => None
      case other => Some(other)
    }
    result
  }

  def findAddedTasks: List[Task] = findAll.filter(_.state == ADDED)

  /**
    *
    * @return List[Task] list of tasks that are already DONE
    */
  def findDoneTasks: List[Task] = findAll.filter(_.state == DONE)

  /**
    * Find a task by its name
    * @param name name of task
    * @return Option[Task]
    */

  def findByName(name: String): Option[Task with Product with Serializable] = tasks.find(_.name == name)


  /**
    * Find all tasks
    * @return List[Task] all tasks from 'database'
    */
  def findAll: List[Task] = tasks.toList.sortBy(_.name)

  /**
    * Change the status of the task to DONE
    * @param name name of task
    */
  def setDone(name: String): Boolean = {
    val task = findByName(name)
    if (task.nonEmpty && task.get.state == ADDED) {
      //val newTask = task.get.copy(state = DONE)
      //tasks += newTask
      //tasks -= task.get
      true
    }
    else false
  }

  /**
    * Change the status of the task to CANCELLED
    * @param name name of task
    */
  def setCancelled(name: String): Boolean = {
    val task = findByName(name)
    if (task.nonEmpty) {
      //val newTask = task.get.copy(state = CANCELLED)
      //tasks += newTask
      //tasks -= task.get
      true
    }
    else false
  }
}
