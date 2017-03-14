package models

import models.State._

/**
  *
  * @param period - time expressed by hours
  */
case class PeriodicTask(name: String, description: String,
                        time: Int, state: State, period: Int)
  extends Task
