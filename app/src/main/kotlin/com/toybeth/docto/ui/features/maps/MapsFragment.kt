package com.toybeth.docto.ui.features.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.docto.databinding.FragmentMapsBinding

class MapsFragment : BaseViewBindingFragment<MapsViewModel, FragmentMapsBinding>() {

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
}