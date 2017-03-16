package models

/**
  *
  * Class that has not unidentified deadline
  */
case class IndefiniteTimeTask(name: String, description: String,
                              time: Int, state: String)
  extends Task {
  override def toString: String = "Task: name = " + name + ", description = " + description +
    ", time = " + time + ", state = " + state
}
