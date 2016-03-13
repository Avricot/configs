/*
 * Copyright 2013-2016 Tsukasa Kitachi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package configs.instance

import com.typesafe.config.ConfigFactory
import configs.Configs
import configs.util._
import scalaprops.Property.forAll
import scalaprops.{Properties, Scalaprops}
import scalaz.std.option._
import scalaz.std.string._

object OptionConfigsTest extends Scalaprops {

  val option = check[Option[java.time.Duration]]

  val missing = forAll { p: String =>
    val config = ConfigFactory.empty()
    Configs[Option[Int]].get(config, q(p)).exists(_.isEmpty)
  }

  val nestedOption = {
    val OO = Configs[Option[Option[Int]]]
    val p1 = forAll { p: String =>
      val config = ConfigFactory.empty()
      OO.get(config, q(p)).exists(_.isEmpty)
    }
    val p2 = forAll { p: String =>
      val config = ConfigFactory.parseString(s"${q(p)} = null")
      OO.get(config, q(p)).exists(_.contains(None))
    }
    Properties.list(
      p1.toProperties("missing"),
      p2.toProperties("null")
    )
  }

}
