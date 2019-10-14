package com.example.budgetapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.google.android.material.card.MaterialCardView

class TransactionUploadFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transaction_upload_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<MaterialCardView>(R.id.manualOptionCard)!!.setOnClickListener {
            val ft = fragmentManager?.beginTransaction()
            ft?.replace(
                R.id.uploadKeyDisplay,
                ManualTransactionFragment()
            )
            ft?.commit()
        }
        activity?.findViewById<MaterialCardView>(R.id.barcodeOptionCard)!!.setOnClickListener {
            val ft = fragmentManager?.beginTransaction()
            ft?.replace(R.id.uploadKeyDisplay, BarcodeScannerFragment())
            ft?.commit()
        }
    }
}