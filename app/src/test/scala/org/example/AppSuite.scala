/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.matchers.should.Matchers

class AppSuite extends AnyFunSuite with Matchers {
  test("App has a greeting") {
    App.greeting() should not be null
  }
}
