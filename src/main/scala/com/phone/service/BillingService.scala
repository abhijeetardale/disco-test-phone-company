package com.phone.service

import com.phone.model.{CustomerBill, Customer}

class BillingService {

  def generateBill(customerList:List[Customer], promotionOn:Boolean): List[CustomerBill] ={

    customerList.groupBy(_.customerId).map{ case (id, customers) =>

      val sorted = customers.sortBy(_.callDuration).reverse

      val afterPromotionApplied = (promotionOn, sorted) match {
        case (true, head::Nil) => List(head.copy(callDuration = 0))
        case (true, _::tail) => tail
        case (_,_) => sorted
      }

      val bill = afterPromotionApplied.foldLeft(0d)((acc,c) => acc + charges(c.callDuration))

      (id, bill)

    }.map(cust => CustomerBill(cust._1, updateCurrency(cust._2))).toList
  }

  def charges(time:Int): Double ={
    val d = time match {
      case x if x>180 => (180*0.05) + ((x-180)*0.03)
      case x => x*0.05
    }
    "%.2f".format(d).toDouble
  }

  def updateCurrency(amount: Double): String = {
    amount match {
      case x if x < 100 => s"${x}p"
      case p  => s"£${p/100}"
    }
  }
}
