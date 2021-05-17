package com.cooltra.recruitment.silveraccountcalculator

import com.cooltra.recruitment.internal.PromotionServiceServer

/**
 * The user declares how much they currently pay and their promotion is calculated based on that figure minus a number of deductions.
 * The amount they get back is capped based on number of Spotlight days.
 */
class SubscriptionAccountCalculator : Calculator {

  override fun calculate(spotlightDays: Int, whatTheyCurrentlyPay: AccountPayment) {
    if (convertToCalendarMonthlyAmount(whatTheyCurrentlyPay) > cap(spotlightDays)) {
      PromotionServiceServer.connect().recordCalculatedAmount(cap(spotlightDays))
    }
  }

  private fun convertToCalendarMonthlyAmount(whatTheyCurrentlyPay: AccountPayment): Double {
    return if (whatTheyCurrentlyPay.frequency == AccountPayment.Frequency.CALENDAR_MONTHLY) {
      whatTheyCurrentlyPay.amount
    } else {
      twoWeeksInMonth() * whatTheyCurrentlyPay.amount
    }
  }

  private fun twoWeeksInMonth(): Int {
    return 52 / 2 / 12
  }

  private fun cap(numDays: Int): Int {
    var cap = -1
    when (numDays) {
      1 -> cap = 250
      2 -> cap = 400
     else -> cap = 600
    }
    return cap
  }

}
