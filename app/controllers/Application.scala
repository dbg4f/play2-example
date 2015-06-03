package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models.Place
import models.Location
import models.User

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }


  implicit val locationWrites: Writes[Location] = (
    (JsPath \ "lat").write[Double] and
      (JsPath \ "long").write[Double]
    )(unlift(Location.unapply))

  implicit val placeWrites: Writes[Place] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "location").write[Location]
    )(unlift(Place.unapply))

  def listPlaces = Action {
    val json = Json.toJson(Place.list)
    Ok(json)
  }


  implicit val locationReads: Reads[Location] = (
    (JsPath \ "lat").read[Double] and
      (JsPath \ "long").read[Double]
    )(Location.apply _)

  implicit val placeReads: Reads[Place] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "location").read[Location]
    )(Place.apply _)


  def savePlace = Action(BodyParsers.parse.json) { request =>
    val placeResult = request.body.validate[Place]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
      },
      place => {
        Place.save(place)
        Ok(Json.obj("status" ->"OK", "message" -> ("Place '"+place.name+"' saved.") ))
      }
    )
  }



  implicit val userWrites: Writes[User] = (
    (JsPath \ "name").write[String]
    )(unlift(Place.unapply))

  def userList = Action {
    val json = Json.toJson(Place.list)
    Ok(json)
  }



}
