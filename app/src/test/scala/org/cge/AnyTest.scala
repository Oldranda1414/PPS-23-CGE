package org.cge

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith

/** This class is created in order to avoid to use @RunWith annotation in each test class. That means that all test classes will extend this class. */
@RunWith(classOf[org.scalatestplus.junit.JUnitRunner])
class AnyTest extends AnyFunSuite {}
