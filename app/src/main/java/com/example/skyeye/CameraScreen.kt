package com.example.skyeye

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("ClickableViewAccessibility")
@Composable
fun CameraScreen() {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    var camera by remember { mutableStateOf<Camera?>(null) }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // Get the camera's current zoom ratio
            val currentZoomRatio = camera?.cameraInfo?.zoomState?.value?.zoomRatio ?: 0F

            // Get the pinch gesture's scaling factor
            val delta = detector.scaleFactor

            // Update the camera's zoom ratio. This is an asynchronous operation that returns
            // a ListenableFuture, allowing you to listen to when the operation completes.
            camera?.cameraControl?.setZoomRatio(currentZoomRatio * delta)

            // Return true, as the event was handled
            return true
        }
    }
    val scaleGestureDetector = ScaleGestureDetector(context, listener)
    previewView.setOnTouchListener { _, event ->
        scaleGestureDetector.onTouchEvent(event)
        return@setOnTouchListener true
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView(
            { previewView },
            modifier = Modifier.fillMaxSize()
        )
        camera?.let { ZoomSlider(it.cameraControl) }

        InfoTypeToggle()
    }
}

@Composable
fun BoxScope.ZoomSlider(cameraControl: CameraControl) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Slider(
        value = sliderPosition,
        onValueChange = { newValue ->
            sliderPosition = newValue
            cameraControl.setLinearZoom(newValue)
        },
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = (90).dp, y = (-115).dp)
            .rotate(270f)
            .width(250.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTypeToggle() {
    val options = listOf("Overview", "Details")
    var selectedIndex by remember { mutableStateOf(0) }
    SingleChoiceSegmentedButtonRow (
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                border = BorderStroke(0.dp, Color.Transparent),
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size, baseShape = MaterialTheme.shapes.small),
                colors = SegmentedButtonDefaults.colors().copy(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = Color.White,
                    activeContentColor = Color.Black,
                    inactiveContentColor = Color.Black
                ),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex
            ) {
                Text(label)
            }
        }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }