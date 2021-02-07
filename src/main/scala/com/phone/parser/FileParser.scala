package com.phone.parser

import com.google.inject.Inject
import com.phone.model.{Customer, CustomerBill}
import com.phone.service.BillingService

import scala.io.Source.fromURL
import scala.util.matching.Regex

class FileParser@Inject()(service: BillingService) {

	private val lineMatcher: Regex =
		"^([A-Z])( \\d{1,3})(-\\d{1,3})(-\\d{1,3}( [0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?)$".r

	def parse(file: String, promotionOn: Boolean) : List[CustomerBill]  = {

		// Here we can use akka streaming to optimise the memory operation while reading file
		val customers: List[Customer] = fromURL(getClass.getResource(file)).getLines()
			.flatMap(	line =>
				lineMatcher.findFirstIn(line) match {
					case Some(customerData) => buildCustomer(customerData)
					case _ => None
				}
			).toList

		service.generateBill(customers, promotionOn)

	}

	def parseSeconds(timeString : String): Int = {
		timeString.split(":").map(_.toInt).reduceLeft((x,y) => x * 60 + y)
	}

	def buildCustomer(line: String): Option[Customer] ={
		line.split("\\s+") match {
			case Array(id, number, timeSpent) => Some(Customer(id, number, parseSeconds(timeSpent)))
			case _ =>  None
		}
	}

}
