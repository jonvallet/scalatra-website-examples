package org.scalatra.example.swagger

import org.scalatra._
import org.scalatra.swagger.ResponseMessage

// Swagger-specific Scalatra imports
import org.scalatra.swagger._

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._

class CharactersController(implicit val swagger: Swagger) extends ScalatraServlet with NativeJsonSupport with SwaggerSupport  {

  // Sets up automatic case class to JSON output serialization, required by
  // the JValueResult trait.
  protected implicit val jsonFormats: Formats = DefaultFormats

  // A description of our application. This will show up in the Swagger docs.
  protected val applicationDescription = "The Marvel characters API. It exposes operations for browsing and searching marvel characters"

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  val getCharacters =
    (apiOperation[List[Character]]("getCharactersIds")
      summary "Show all marvel characters ids"
      tags ("characters")
      notes "Shows all the marvel characters ids."
      )

  get("/", operation(getCharacters)) {
    MarvelData.all
  }

  val errorResponse = ResponseMessage(404, "Character Not Found")
  val findByCharacterId =
    (apiOperation[Character]("findByCharacterId")
      summary "Find a marvel character by id"
      tags("characters")
      parameters (
      pathParam[String]("characterId").description("Character Id need to be fetched"))
      responseMessage errorResponse)

  get("/:characterId", operation(findByCharacterId)) {
    MarvelData.all find (_.characterId == params("characterId")) match {
      case Some(b) => b
      case None => halt(404, errorResponse)
    }
  }
}


// A Flower object to use as a data model
case class Character(characterId: String, powers: String)

// An amazing datastore!
object MarvelData {

  /**
   * Some fake flowers data so we can simulate retrievals.
   */
  var all = List(
    Character("100001", "Fly"),
    Character("100002", "Strong"),
    Character("100003", "Lighting"))
}