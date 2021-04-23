package com.phone.parser

import com.google.inject.Inject
import com.phone.model.Customer

import scala.io.Source.fromURL
import scala.util.matching.Regex

class FileParser@Inject()() {

  private val totalSeconds = 60

  private val lineMatcher: Regex =
    "^([A-Z])( \\d{1,3})(-\\d{1,3})(-\\d{1,3}( [0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?)$".r

  def parse(file: String, promotionOn: Boolean) : List[Customer]  = {

    // Here we can use akka streaming to optimise the memory operation while reading file
    val source = fromURL(getClass.getResource(file))

    val customers: List[Customer] = source.getLines()
      .flatMap(  line =>
        lineMatcher.findFirstIn(line) match {
          case Some(customerData) => buildCustomer(customerData)
          case _ => throw new RuntimeException("failed to process records for line : " + line)
        }
      ).toList

    source.close()

    customers
  }

  def parseSeconds(timeString : String): Int = timeString.split(":")
    .map(_.toInt).reduceLeft((x, y) => x * totalSeconds + y)

  def buildCustomer(line: String): Option[Customer] = line.split("\\s+") match {
    case Array(id, number, timeSpent) => Some(Customer(id, number, parseSeconds(timeSpent)))
    case _ =>  None
  }

}
