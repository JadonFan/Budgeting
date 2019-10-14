package com.example.budgetapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton

class ManualTransactionFragment: Fragment(), OnMapReadyCallback ***REMOVED***
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? ***REMOVED***
        return inflater.inflate(R.layout.manual_transaction_layout, container, false)
***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) ***REMOVED***
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<MaterialButton>(R.id.pinpointLocationBtn)!!.setOnClickListener ***REMOVED***
            val ft = fragmentManager?.beginTransaction()
            val smf = SupportMapFragment()
            ft?.replace(R.id.uploadKeyDisplay, smf)
            smf.getMapAsync(this)
            ft?.commit()
***REMOVED***
***REMOVED***

    override fun onMapReady(p0: GoogleMap?) ***REMOVED***
        map = p0
        val toronto = LatLng(43.65, -79.38)
        map!!.addMarker(MarkerOptions().position(toronto).title("Toronto"))
        map!!.moveCamera(CameraUpdateFactory.newLatLng(toronto))
***REMOVED***
***REMOVED***