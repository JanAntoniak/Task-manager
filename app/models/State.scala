package models

/**
  * Created by jan on 12.03.17.
  */
object State extends Enumeration {
    type State = Value
    val DONE, ADDED, CANCELLED = Value
}
