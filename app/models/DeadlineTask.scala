package models

import models.State._
import org.joda.time._

/**
  *
  * @param deadline - date that determines the deadline
  */
case class DeadlineTask(name: String, description: String,
                        time: Int, state: State, deadline: DateTime)
  extends Task {

  def unapply(task: DeadlineTask) =
    if(task == null) None
    else Some(name, description, time, state, deadline)
}
