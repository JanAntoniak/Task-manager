package models

import play.api.libs.json._

object State extends Enumeration {
    type State = Value
    var DONE, ADDED, CANCELLED = Value

    def parse(v: String) : Option[State] = values.find(_.toString == v)

    implicit def reads: Reads[State] = new Reads[State] {
        def reads(json: JsValue): JsResult[State] = json match {
            case JsString(v) => parse(v) match {
                case Some(a) => JsSuccess(a)
                case _ => JsError(s"String value ($v) is not a valid enum item ")
            }
            case _ => JsError("String value expected")
        }
    }

}