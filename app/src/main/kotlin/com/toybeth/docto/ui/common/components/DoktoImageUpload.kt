package com.toybeth.docto.ui.common.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.R
import com.toybeth.docto.base.theme.DoktoError
import com.toybeth.docto.base.theme.DoktoRegistrationFormTextFieldBackground
import com.toybeth.docto.base.theme.DoktoSecondary

@Composable
@Suppress("DEPRECATION")
fun DoktoImageUpload(
    uploadedImage: Bitmap?,
    errorMessage: String? = null,
    onImageUpload: (Bitmap?, Uri) -> Unit
) {

    val context = LocalContext.current
    var identityImageUri by remember { mutableStateOf<Uri?>(null) }

    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val cornerRadius = LocalDensity.current.run { 16.dp.toPx() }

    val identityImageLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        identityImageUri = uri
        identityImageUri?.let {
            if (Build.VERSION.SDK_INT >= 28) {
                val bitmap = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(context.contentResolver, it)
                )
                onImageUpload(bitmap, it)
            } else {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                onImageUpload(bitmap, it)
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(
                color = DoktoRegistrationFormTextFieldBackground,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = Color.White,
                style = stroke,
                cornerRadius = CornerRadius(
                    x = cornerRadius,
                    y = cornerRadius
                )
            )
        }
        if (uploadedImage != null) {
            Image(
                bitmap = uploadedImage.asImageBitmap(),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        shape = RoundedCornerShape(16.dp)
                        clip = true
                    }
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton(
                    modifier = Modifier
                        .then(Modifier.size(24.dp))
                        .clip(CircleShape)
                        .background(
                            color = DoktoSecondary
                        ),
                    onClick = {
                        identityImageLauncher.launch("image/*")
                    }) {
                    Icon(
                        Icons.Filled.Edit,
                        "change image",
                        tint = Color.White
                    )
                }
            }
        } else {
            Row {
                DoktoButton(textResourceId = R.string.choose_image) {
                    identityImageLauncher.launch("image/*")
                }
            }
        }
    }

    // ---------------------- ERROR MESSAGE ------------------- //
    errorMessage?.let {
        Text(
            text = it,
            color = DoktoError,
            fontSize = 14.sp
        )
    }
}