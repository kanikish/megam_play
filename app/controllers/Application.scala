/* 
** Copyright [2012-2013] [Megam Systems]
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
** http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/
package controllers

import controllers.stack._
import controllers.funnel.FunnelErrors._
import controllers.funnel._
import jp.t2v.lab.play2.stackc.{ RequestWithAttributes, RequestAttributeKey, StackableController }
import models._
import play.api._
import play.api.mvc._
import scalaz._
import Scalaz._
import scalaz.Validation._

/**
 * @author rajthilak
 *
 */
object Application extends Controller with APIAuthElement {

  /**
   * Make this role based (Admin),
   * based on a private key.
   */
  def init = Action { implicit request =>
    PlatformAppPrimer.prep match {
      case Success(succ) => {
        val fu = List(("success" -> "Megam Cloud Platform is ready.")) ++ FunnelResponses.toTuple2(succ) 
        Redirect("/").flashing(fu: _*) //a hack to covert List[Tuple2] to varargs of Tuple2. flashing needs it.
      }
      case Failure(err) => {
        val rn: FunnelResponses = new HttpReturningError(err)
        val rnjson = FunnelResponses.toJson(rn, false)
        val fu = List(("error" -> "Duh Megam Cloud Platform couldn't be primed.")) ++ FunnelResponses.toTuple2(rn)
        Logger.debug(rnjson)
        Redirect("/").flashing(fu: _*)
      }
    }

  }

  def index = Action { implicit request =>
    Ok(views.html.index("Megam play at your service. Lets kick the tyres."))
  }
  /**
   * POST : Authenticate, verifies if the auth setup is OK.
   * Output: FunnelResponse as JSON with the msg.
   */
  def authenticate = StackAction(parse.tolerantText) { implicit request =>
    val resp = FunnelResponse(apiAccessed.getOrElse("Something strange. Authentication successful, but sans success message. Contact support"), "Megam::Auth").toJson(true)
    Logger.debug(resp)
    Ok(resp)
  }

}