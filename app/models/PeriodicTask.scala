package models
/**
  *
  * @param period - time expressed by hours
  */
case class PeriodicTask(name: String, description: String,
                        time: Int, state: String, period: Int)
  extends Task {
  override def toString: String = "Task: name = " + name + ", description = " + description +
    ", time = " + time + ", state = " + state + ", period = " + period
}

