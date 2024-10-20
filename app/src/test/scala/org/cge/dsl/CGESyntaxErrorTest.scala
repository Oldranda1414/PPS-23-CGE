package org.cge.dsl

import org.scalatest.matchers.should.Matchers._
import org.cge.AnyTest
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.cge.dsl.exception.CGESyntaxError

class CGESyntaxErrorTest extends AnyTest:

  test("CGESyntaxError should print the error message"):
    val outputStream = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outputStream)):
      new CGESyntaxError("Test Error Message")
    outputStream.toString.contains("Test Error Message") should be (true)

  test("CGESyntaxError should extend Exception"):
    val ex = new CGESyntaxError("Another Test")
    ex shouldBe a [Exception]
