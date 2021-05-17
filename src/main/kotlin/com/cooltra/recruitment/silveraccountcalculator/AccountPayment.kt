package com.cooltra.recruitment.silveraccountcalculator

data class AccountPayment(val amount: Double, val frequency: Frequency) {

  enum class Frequency {
    BIWEEKLY, CALENDAR_MONTHLY
  }
}
