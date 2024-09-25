package org.example

import org.scalatest.matchers.should.Matchers

class AppTest extends AnyTest with Matchers {
  test("App returns the correct greeting") {
    val expectedGreeting = "Hello, world!"
    val actualGreeting = App.greeting()
    actualGreeting shouldEqual expectedGreeting
  }
}
