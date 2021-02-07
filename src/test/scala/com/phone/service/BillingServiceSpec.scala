package com.phone.service

import com.phone.model.{CustomerBill, Customer}
import org.scalatest.{Matchers, WordSpec}

class BillingServiceSpec  extends WordSpec with Matchers{

  val service = new BillingService

  "generateBill" should {

    "return correct bill of 16.5P for A" in {

      val customers = List(Customer("A", "555-333-212", 150),Customer("A", "555-663-111", 180))

      service.generateBill(customers, promotionOn = false) shouldBe List(CustomerBill("A", "16.5p"))
    }

    "return correct bill of 7.5P for A if promotion is applied" in {

      val customers = List(Customer("A", "555-333-212", 150),Customer("A", "555-663-111", 180))

      service.generateBill(customers, promotionOn = true) shouldBe List(CustomerBill("A", "7.5p"))
    }

    "return correct bill of 16.5P for A and 9.6p for B" in {

      val customers = List(Customer("A", "555-333-212", 150),Customer("B", "555-663-111", 200), Customer("A", "555-663-111", 180))

      service.generateBill(customers, promotionOn = false) shouldBe List(CustomerBill("A", "16.5p"), CustomerBill("B", "9.6p"))
    }

    "return correct bill of 9.6p for B" in {

      val customers = List(Customer("B", "555-663-111", 200))

      service.generateBill(customers, promotionOn = false) shouldBe List(CustomerBill("B", "9.6p"))
    }

    "return correct bill of 0p for B if promotion is applied " in {

      val customers = List(Customer("B", "555-663-111", 200))

      service.generateBill(customers, promotionOn = true) shouldBe List(CustomerBill("B", "0.0p"))
    }
  }

  "charges" should {

    "return 7.5p for 3 minute" in {

      service.charges(150) shouldBe 7.5
    }

    "return 9p for 3 minute" in {

      service.charges(180) shouldBe 9
    }

    "return 9.6p for 3 minute" in {

      service.charges(200) shouldBe 9.6
    }
  }

  "updateCurrency" should {

    "return 0.0p for 0" in {
      service.updateCurrency(0) shouldBe "0.0p"
    }

    "return 99p for 99" in {
      service.updateCurrency(99) shouldBe "99.0p"
    }

    "return £1.0 for 100" in {
      service.updateCurrency(100) shouldBe "£1.0"
    }

    "return £1.2 for 120" in {
      service.updateCurrency(120) shouldBe "£1.2"
    }
  }

}
