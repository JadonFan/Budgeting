package com.budget.budget.ui.transaction

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.camera.core.*
import com.airbnb.mvrx.BaseMvRxFragment
import com.budget.budget.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import org.json.JSONObject
import java.net.URL

// import com.google.android.gms.vision.barcode.Barcode
// import com.google.android.gms.vision.barcode.BarcodeDetector

class BarcodeScannerFragment: BaseMvRxFragment() {
    private class MyImageAnalyzer: ImageAnalysis.Analyzer {
        var image: FirebaseVisionImage? = null

        private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw Exception("Rotation must be 0, 90, 180, 270")
        }
        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(image: ImageProxy, rotationDegrees: Int) {
            val mediaImage = image.image
            val imageRotation = degreesToFirebaseRotation(rotationDegrees)
            if (mediaImage != null) {
                this.image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            }
        }
    }

    private var lensFacing = CameraX.LensFacing.BACK
    private var cameraPreviewView: TextureView? = null
    private val pTransactions = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.barcode_scanner_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraPreviewView?.post {
            startCamera()
        }
    }

    private fun startCamera() {
        val metrics = DisplayMetrics().also {
            cameraPreviewView?.display!!.getRealMetrics(it)
        }
        val screenSize = Size(metrics.widthPixels, metrics.heightPixels)
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)

        val previewConfig = PreviewConfig.Builder().apply {
            setLensFacing(lensFacing)
            setTargetResolution(screenSize)
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setTargetRotation(activity?.windowManager?.defaultDisplay!!.rotation)
            setTargetRotation(cameraPreviewView?.display!!.rotation)
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            cameraPreviewView?.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setLensFacing(lensFacing)
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setTargetRotation(cameraPreviewView?.display!!.rotation)
            setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
        }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)
        activity?.findViewById<MaterialButton>(R.id.scanTransactionBtn)!!.setOnClickListener {
            imageCapture.takePicture({}, object: ImageCapture.OnImageCapturedListener() {
                override fun onError(
                    imageCaptureError: ImageCapture.ImageCaptureError,
                    message: String,
                    cause: Throwable?
                ) {
                    Toast.makeText(context, "Photo could not be taken due to $message", Toast.LENGTH_SHORT).show()
                }

                override fun onCaptureSuccess(image: ImageProxy?, rotationDegrees: Int) {
                    super.onCaptureSuccess(image, rotationDegrees)
                    processBarcode(image, rotationDegrees)
                }
            })
        }
    }

    private fun updateTransform() {
        val matrix = Matrix()
        val centreX = cameraPreviewView!!.width / 2f
        val centreY = cameraPreviewView!!.height / 2f
        val view = cameraPreviewView

        val rotationDegrees = when (view?.display!!.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centreX, centreY)
        view.setTransform(matrix)
    }

    private fun processBarcode(image: ImageProxy?, rotationDegrees: Int) {
//        val detector = BarcodeDetector.Builder(context)
//            .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
//            .build()
//        if (!detector.isOperational) {
//            Toast.makeText(context, "Unable to set up the detector!", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val barcodes = SparseArray<Barcode>()
//        val productDetails = HashMap<String,Barcode.UrlBookmark>()
//        for (barcode in barcodes.valueIterator()) {
//            productDetails[barcode.rawValue] = barcode.url
//        }

        val mia = MyImageAnalyzer()
        mia.analyze(image as ImageProxy, rotationDegrees)

//        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
//            .build()
        val detector = FirebaseVision.getInstance().visionBarcodeDetector
        val result = detector.detectInImage(mia.image as FirebaseVisionImage)
            .addOnSuccessListener {
                for (barcode in it) {
                    when (barcode.valueType) {
                        FirebaseVisionBarcode.FORMAT_UPC_A, FirebaseVisionBarcode.FORMAT_UPC_E,
                            FirebaseVisionBarcode.FORMAT_EAN_8, FirebaseVisionBarcode.FORMAT_EAN_13,
                            FirebaseVisionBarcode.TYPE_ISBN -> {
                            val url = URL("https://api.barcodelookup.com/v2/products?barcode=${barcode.rawValue}" +
                                    "&formatted=y&key=1") // TODO store API key securely
                            val jsonObject = JSONObject(url.readText())
                            val stores = jsonObject.getJSONArray("Stores")
                            for (index in 0 until stores.length()) {
                                val store: String = stores[index] as String
                                pTransactions.add(Integer.parseInt(JSONObject(store).get("store_price") as String))
                            }
                        }
                        FirebaseVisionBarcode.TYPE_URL -> {
                            val url = barcode.url!!.url
                        }
                        else -> {
                            Toast.makeText(context, "Currently not available", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .addOnFailureListener {

            }

        if (result.isSuccessful) {
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun invalidate() { }
}