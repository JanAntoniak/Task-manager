package models

import org.joda.time._

/**
  *
  * @param deadline - date that determines the deadline
  */
case class DeadlineTask(name: String, description: String,
                        time: Int, state: String, deadline: DateTime)
  extends Task {
  override def toString: String = "Task: name = " + name + ", description = " + description +
    ", time = " + time + ", state = " + state + ", deadline = " + deadline
}