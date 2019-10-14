package com.example.budgetapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.google.android.material.card.MaterialCardView

class TransactionUploadFragment: Fragment() ***REMOVED***
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? ***REMOVED***
        return inflater.inflate(R.layout.transaction_upload_layout, container, false)
***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) ***REMOVED***
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<MaterialCardView>(R.id.manualOptionCard)!!.setOnClickListener ***REMOVED***
            val ft = fragmentManager?.beginTransaction()
            ft?.replace(
                R.id.uploadKeyDisplay,
                ManualTransactionFragment()
            )
            ft?.commit()
***REMOVED***
        activity?.findViewById<MaterialCardView>(R.id.barcodeOptionCard)!!.setOnClickListener ***REMOVED***
            val ft = fragmentManager?.beginTransaction()
            ft?.replace(R.id.uploadKeyDisplay, BarcodeScannerFragment())
            ft?.commit()
***REMOVED***
***REMOVED***
***REMOVED***