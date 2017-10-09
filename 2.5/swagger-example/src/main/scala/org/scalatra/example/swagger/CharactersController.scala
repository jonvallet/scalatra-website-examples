package org.scalatra.example.swagger

import org.scalatra._
import org.scalatra.swagger.ResponseMessage

import scala.util.{Failure, Success}

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
    new MarvelClient().getAll match {
      case Success(value) => value
      case Failure(error) => error
    }
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
    new MarvelClient().getCharacter(params("characterId")) match {
      case Success(value) => value
      case Failure(_) => halt(404, errorResponse)
    }
  }
}


