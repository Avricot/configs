/*
 * Copyright 2013-2015 Tsukasa Kitachi
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

package com.github.kxbmap.configs.instance

import com.github.kxbmap.configs.util._
import scalaprops.{Gen, Scalaprops}
import scalaz.Equal
import scalaz.std.option._
import scalaz.std.string._

object EitherConfigsTest extends Scalaprops {

  val either = check[Either[Throwable, String]]


  implicit def eitherGen[A: Gen]: Gen[Either[Throwable, A]] =
    Gen.option[A].map(_.toRight(new RuntimeException("dummy")))

  implicit def eitherEqual[A: Equal]: Equal[Either[Throwable, A]] =
    Equal[Option[A]].contramap(_.right.toOption)

  implicit def eitherToConfigValue[A: ToConfigValue]: ToConfigValue[Either[Throwable, A]] =
    ToConfigValue[Option[A]].contramap(_.right.toOption)

}
