package com.phone.parser

import com.phone.model.{Customer, CustomerBill}
import com.phone.service.BillingService
import org.scalatest.{Matchers, WordSpec}

class FileParserSpec extends WordSpec with Matchers{

  val service = new BillingService
  val parser = new  FileParser(service)

  "parse" should{

    "return correct bill for A with one record" in {
      parser.parse("/calls0.log", promotionOn = false) shouldBe List(
        CustomerBill("A", "6.15p"))
    }

    "return correct bill for A and B with one record each" in {
      parser.parse("/calls1.log", promotionOn = false) shouldBe List(
        CustomerBill("A", "6.15p"), CustomerBill("B", "4.0p"))
    }

    "return correct bill for A and B with A with multiple records" in {
      parser.parse("/calls2.log", promotionOn = false) shouldBe List(
        CustomerBill("A", "12.3p"), CustomerBill("B", "0.15p"))
    }

    "return correct bill for valid record" in {
      parser.parse("/calls3.log", promotionOn = false) shouldBe List(
        CustomerBill("A", "6.15p"))
    }

    "return correct bill for A and B if promotion is not applied" in {
      parser.parse("/calls.log", promotionOn = false) shouldBe List(
        CustomerBill("A", "52.67p"), CustomerBill("B", "51.96p"))
    }

    "return correct bill for A and B if promotion is applied" in {
      parser.parse("/calls.log", promotionOn = true) shouldBe List(
        CustomerBill("A", "37.04p"), CustomerBill("B", "30.63p"))
    }

    "return runtime exception if log file is not available" in{
      intercept[RuntimeException](
        parser.parse("/invalid.log", promotionOn = false)
      )
    }
  }

  "parseSeconds" should{

    "return 123 seconds for 00:02:03" in {
      parser.parseSeconds("00:02:03") shouldBe 123
    }

    "return 0 seconds for 00:00:00" in {
      parser.parseSeconds("00:00:00") shouldBe 0
    }
  }

  "buildCustomer" should{

    "return valid customer object if string is valid seconds for 00:02:03" in {
      parser.buildCustomer("A 555-333-212 00:02:03") shouldBe Some(Customer("A", "555-333-212", 123))
    }

    "return None if customer string is empty" in {
      parser.buildCustomer("") shouldBe None
    }

    "return None if customer string is not valid" in {
      parser.buildCustomer("A 555-333-212 00:02:03 00") shouldBe None
    }
  }
}
