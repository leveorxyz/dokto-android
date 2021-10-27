package com.toybethsystems.dokto.ui.features.maps

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.orhanobut.logger.Logger
import com.toybethsystems.dokto.base.ui.BaseViewBindingFragment
import com.toybethsystems.dokto.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.CameraPosition










class MapsFragment : BaseViewBindingFragment<MapsViewModel, FragmentMapsBinding>(),
    OnMapReadyCallback {

    override val viewModel: MapsViewModel by viewModels()
    private lateinit var mapView: MapView

    override val inflater: (
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) -> FragmentMapsBinding
        get() = FragmentMapsBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        try {
            MapsInitializer.initialize(requireActivity())
        } catch (e: GooglePlayServicesNotAvailableException) {
            Logger.e("Google Play Services missing")
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(-33.852, 151.211)
        googleMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )
        val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}