package org.example

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.junit.runner.RunWith

@RunWith(classOf[org.scalatestplus.junit.JUnitRunner])
class AppTest extends AnyFunSuite with Matchers {

  test("App returns the correct greeting") {
    val expectedGreeting = "Hello, world!"
    val actualGreeting = App.greeting()
    actualGreeting shouldEqual expectedGreeting
  } 
}
