package com.phone

import com.phone.parser.FileParser
import com.phone.service.BillingService

object Main extends App {

  private val service = new BillingService

  new FileParser(service).parse("/calls.log", promotionOn = true).map { generatedBill =>

    println(generatedBill)

  }
}
