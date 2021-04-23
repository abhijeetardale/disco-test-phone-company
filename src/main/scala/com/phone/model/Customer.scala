package com.phone.model

case class Customer(customerId:String, phoneNumber:String, callDuration:Int)

case class CustomerBill(customerId:String, billAmount:String)
