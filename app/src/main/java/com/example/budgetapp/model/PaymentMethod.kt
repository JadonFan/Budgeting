package com.example.budgetapp.model

enum class PaymentMethod(private val category: Int) ***REMOVED***
    CASH(0),
    CREDIT_CARD(1),
    DEBIT_CARD(1),
    CHEQUE(2),
    BITCOIN(3),
    GIFT_CARD(4),
    PREPAID_CARD(4),
    FREE(5),
    OTHER(6)
    ;

    fun getCategory(): Int ***REMOVED***
        return category
***REMOVED***
***REMOVED***