package models

import models.State.State

/**
  *
  * Class that has not unidentified deadline
  */
case class IndefiniteTimeTask(name: String, description: String, time: Integer, state: State)
  extends Task
