package models

import models.State._
import org.joda.time._

/**
  *
  * @param deadline - date that determines the deadline
  */
case class DeadlineTask(name: String, description: String, time: Integer, state: State, deadline: DateTime)
  extends Task
