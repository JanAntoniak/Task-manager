package models

import models.State._

/**
  *
  * @param period - time expressed by hours
  */
case class PeriodicTask(name: String, description: String, time: Integer, state: State, period: Integer)
  extends Task
