package com.phone.model

import play.api.libs.json.{Format, Json}

case class Customer(customerId:String, phoneNumber:String, callDuration:Int)

object Customer {

  implicit val formats: Format[Customer] = Json.format[Customer]
}

case class CustomerBill(customerId:String, billAmount:String)

object CustomerBill {

  implicit val formats: Format[CustomerBill] = Json.format[CustomerBill]
}