package com.budget.budget.ui.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.budget.budget.R
import com.budget.budget.ui.common.CustomMaterialButton
import com.budget.budget.viewmodel.TransactionViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import timber.log.Timber
import javax.inject.Inject

class TransactionUploadFragment: BaseMvRxFragment(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var selectedLocation: LatLng? = null

    @Inject
    lateinit var tvmFractory: TransactionViewModel.Factory
    private val tvm: TransactionViewModel by fragmentViewModel(TransactionViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.transaction_upload_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemNameText = view.findViewById<TextInputEditText>(R.id.item_name_edit)
        val amountText = view.findViewById<TextInputEditText>(R.id.item_cost_edit)
        val locationText = view.findViewById<TextInputEditText>(R.id.location_edit)

        view.findViewById<ImageView>(R.id.pinpoint_location_btn)!!.setOnClickListener {
            val smf = SupportMapFragment()
            smf.getMapAsync(this)

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.uploadKeyDisplay, smf)
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<CustomMaterialButton>(R.id.barcode_options_btn)!!.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.uploadKeyDisplay, BarcodeScannerFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<CustomMaterialButton>(R.id.addTransactionBtn)!!.setOnClickListener {
            if (amountText?.text.isNullOrBlank()) {
                Toast.makeText(context, getString(R.string.enter_valid_money), Toast.LENGTH_SHORT).show()
            } else {
                tvm.addNewTransaction(
                    itemNameText?.text.toString(),
                    amountText?.text.toString().toFloatOrNull() ?: Float.NaN,
                    locationText?.text.toString()
                )
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.key_display, TransactionTrackerFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0?.apply {
            val toronto = LatLng(43.65, -79.38)
            addMarker(MarkerOptions().position(toronto).title("Your Current Location"))
            moveCamera(CameraUpdateFactory.newLatLng(toronto))
            setOnMapClickListener(::onMapClick)
        }
    }

    private fun onMapClick(p0: LatLng?) {
        selectedLocation = p0
        requireActivity().runOnUiThread {
            requireActivity().findViewById<TextInputEditText>(R.id.location_edit)!!.let {
                it.setText(getString(
                    R.string.map_coordinates,
                    selectedLocation?.latitude,
                    selectedLocation?.longitude
                ))
                Timber.i(it.text.toString())
            }
        }
    }

    override fun invalidate() { }
}