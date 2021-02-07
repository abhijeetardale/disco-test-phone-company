package com.phone.model

import org.scalatest.{Matchers, WordSpec}

class CustomerSpec extends WordSpec with Matchers{

  val customer: Customer = Customer("A", "555-333-212", 123)

}
