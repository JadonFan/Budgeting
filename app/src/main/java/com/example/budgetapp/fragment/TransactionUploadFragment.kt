package com.example.budgetapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.model.SpendingInfo
import com.example.budgetapp.view.CustomMaterialButton
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class TransactionUploadFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private var map: GoogleMap? = null
    private var selectedLocation: LatLng? = null


    private data class RecordInfo(val spendingInfo: SpendingInfo, val context: Context)

    private class RetrieveSpendingRecordTask: AsyncTask<RecordInfo, Void, Void>() {
        override fun doInBackground(vararg params: RecordInfo): Void? {
            val spendingRecordDb = DatabaseManager(params[0].context).getSpendingInfoDb()
            spendingRecordDb!!.spendingInfoDao().insertAll(params[0].spendingInfo)
            return null
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.transaction_upload_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemNameText = activity?.findViewById<TextInputEditText>(R.id.requestedItemNameText)
        val amountText = activity?.findViewById<TextInputEditText>(R.id.requestedAmountText)
        val locationText = activity?.findViewById<TextInputEditText>(R.id.requestedLocationText)

        activity?.findViewById<ImageView>(R.id.pinpointLocationBtn)!!.setOnClickListener {
            val smf = SupportMapFragment()
            smf.getMapAsync(this)

            fragmentManager?.beginTransaction()
                ?.replace(R.id.uploadKeyDisplay, smf)
                ?.addToBackStack(null)
                ?.commit()
        }

        activity?.findViewById<CustomMaterialButton>(R.id.barcodeOptionBtn)!!.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.uploadKeyDisplay, BarcodeScannerFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        activity?.findViewById<CustomMaterialButton>(R.id.addTransactionBtn)!!.setOnClickListener {
            val spendingInfo = SpendingInfo(0, itemNameText?.text.toString(),
                amountText?.text.toString().toFloat(), "", locationText?.text.toString(),
                Date(), "")
            RetrieveSpendingRecordTask().execute(RecordInfo(spendingInfo, context as Context))
        }
    }


    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        val toronto = LatLng(43.65, -79.38)
        map!!.addMarker(MarkerOptions().position(toronto).title("Your Current Location"))
        map!!.moveCamera(CameraUpdateFactory.newLatLng(toronto))
        map!!.setOnMapClickListener(this)
    }


    @SuppressLint("SetTextI18n")
    override fun onMapClick(p0: LatLng?) {
        selectedLocation = p0
        println("CLICKED!!!")
        activity?.runOnUiThread {
            activity?.findViewById<TextInputEditText>(R.id.requestedLocationText)?.setText(
                "${selectedLocation!!.latitude}, ${selectedLocation!!.longitude}"
            )
            println(activity?.findViewById<TextInputEditText>(R.id.requestedLocationText)?.text.toString())
        }
    }
}