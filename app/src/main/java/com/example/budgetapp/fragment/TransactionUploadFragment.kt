package com.example.budgetapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class TransactionUploadFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private var map: GoogleMap? = null
    private var selectedLocation: LatLng? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.transaction_upload_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemNameText = view.findViewById<TextInputEditText>(R.id.item_name_edit)
        val amountText = view.findViewById<TextInputEditText>(R.id.item_cost_edit)
        val locationText = view.findViewById<TextInputEditText>(R.id.location_edit)

        view.findViewById<ImageView>(R.id.pinpoint_location_btn)!!.setOnClickListener {
            val smf = SupportMapFragment()
            smf.getMapAsync(this)

            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.uploadKeyDisplay, smf)
                ?.addToBackStack(null)
                ?.commit()
        }

        view.findViewById<CustomMaterialButton>(R.id.barcode_options_btn)!!.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.uploadKeyDisplay, BarcodeScannerFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        view.findViewById<CustomMaterialButton>(R.id.addTransactionBtn)!!.setOnClickListener {
            if (amountText?.text.isNullOrBlank()) {
                Toast.makeText(context, getString(R.string.enter_valid_money), Toast.LENGTH_SHORT).show()
            } else {
                val spendingInfo = SpendingInfo(
                    0,
                    itemNameText?.text.toString(),
                    amountText?.text.toString().toFloat(),
                    "",
                    locationText?.text.toString(),
                    Date(),
                    ""
                )
                Thread {
                    val spendingRecordDb = DatabaseManager(context!!).getSpendingInfoDb()
                    spendingRecordDb!!.spendingInfoDao().insertAll(spendingInfo)
                }.start()
                fragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.key_display, TransactionTrackerFragment())
                    ?.addToBackStack(null)
                    ?.commit()
            }
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
        activity?.runOnUiThread {
            activity?.findViewById<TextInputEditText>(R.id.location_edit)?.setText(
                "${selectedLocation?.latitude}, ${selectedLocation?.longitude}"
            )
            println(activity?.findViewById<TextInputEditText>(R.id.location_edit)?.text.toString())
        }
    }
}