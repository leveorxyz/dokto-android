package com.toybethsystems.dokto.twilio.ui.preparation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybethsystems.dokto.twilio.R
import com.toybethsystems.dokto.twilio.databinding.FragmentPreparationBinding
import com.toybethsystems.dokto.twilio.ui.common.TwilioCallViewModel
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class PreparationFragment :
//    BaseViewBindingFragment<TwilioCallViewModel, FragmentPreparationBinding>() {
//
//    companion object {
//        const val CAMERA_MIC_PERMISSION_REQUEST_CODE = 1
//    }
//
//    override val viewModel: TwilioCallViewModel by viewModels()
//
//    override val inflater: (
//        inflater: LayoutInflater,
//        parent: ViewGroup?,
//        attachToParent: Boolean
//    ) -> FragmentPreparationBinding
//        get() = FragmentPreparationBinding::inflate
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.preparationScreen.apply {
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//            setContent {
//                PreparationScreen(::navigateToCallFragment)
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        requestPermissionForCameraAndMicrophone()
//    }
//
//    override fun onPause() {
//        Logger.d("STOP")
//        viewModel.pause()
//        super.onPause()
//    }
//
//    override fun onDestroy() {
//        viewModel.destroy()
//        super.onDestroy()
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
//            var cameraAndMicPermissionGranted = true
//
//            for (grantResult in grantResults) {
//                cameraAndMicPermissionGranted = cameraAndMicPermissionGranted and
//                        (grantResult == PackageManager.PERMISSION_GRANTED)
//            }
//
//            if (cameraAndMicPermissionGranted) {
//                viewModel.initializeTwilio()
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    R.string.permissions_needed,
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }
//
//    private fun requestPermissionForCameraAndMicrophone() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA) ||
//            ActivityCompat.shouldShowRequestPermissionRationale(
//                requireActivity(),
//                Manifest.permission.RECORD_AUDIO
//            )
//        ) {
//            Toast.makeText(
//                requireContext(),
//                R.string.permissions_needed,
//                Toast.LENGTH_LONG
//            ).show()
//        } else {
//            if(viewModel.checkPermissionForCameraAndMicrophone()) {
//                viewModel.initializeTwilio()
//            } else {
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
//                    CAMERA_MIC_PERMISSION_REQUEST_CODE
//                )
//            }
//        }
//    }
//
//    private fun navigateToCallFragment() {
//        findNavController().navigate(
//            PreparationFragmentDirections.actionPreparationFragmentToTwilioCallFragment()
//        )
//    }
//}