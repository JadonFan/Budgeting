package com.example.budgetapp.fragment

import android.graphics.Matrix
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.camera.core.*
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executor

// import com.google.android.gms.vision.barcode.Barcode
// import com.google.android.gms.vision.barcode.BarcodeDetector

class BarcodeScannerFragment: Fragment() ***REMOVED***
    private class MyImageAnalyzer: ImageAnalysis.Analyzer ***REMOVED***
        var image: FirebaseVisionImage? = null

        private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) ***REMOVED***
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw Exception("Rotation must be 0, 90, 180, 270")
***REMOVED***

        override fun analyze(imageProxy: ImageProxy?, rotationDegrees: Int) ***REMOVED***
            val mediaImage = imageProxy?.image
            val imageRotation = degreesToFirebaseRotation(rotationDegrees)
            if (mediaImage != null) ***REMOVED***
                this.image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
    ***REMOVED***
***REMOVED***
***REMOVED***

    private var lensFacing = CameraX.LensFacing.BACK
    private var cameraPreviewView: TextureView? = null
    private val pTransactions = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? ***REMOVED***
        return inflater.inflate(R.layout.camera_viewer_layout, container, false)
***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) ***REMOVED***
        super.onViewCreated(view, savedInstanceState)
        cameraPreviewView?.post ***REMOVED***
            startCamera()
***REMOVED***
***REMOVED***

    private fun startCamera() ***REMOVED***
        val metrics = DisplayMetrics().also ***REMOVED***
            cameraPreviewView?.display!!.getRealMetrics(it)
***REMOVED***
        val screenSize = Size(metrics.widthPixels, metrics.heightPixels)
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)

        val previewConfig = PreviewConfig.Builder().apply ***REMOVED***
            setLensFacing(lensFacing)
            setTargetResolution(screenSize)
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setTargetRotation(activity?.windowManager?.defaultDisplay!!.rotation)
            setTargetRotation(cameraPreviewView?.display!!.rotation)
***REMOVED***.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener ***REMOVED***
            cameraPreviewView?.surfaceTexture = it.surfaceTexture
            updateTransform()
***REMOVED***

        val imageCaptureConfig = ImageCaptureConfig.Builder().apply ***REMOVED***
            setLensFacing(lensFacing)
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setTargetRotation(cameraPreviewView?.display!!.rotation)
            setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
***REMOVED***.build()

        val imageCapture = ImageCapture(imageCaptureConfig)
        activity?.findViewById<MaterialButton>(R.id.scanTransactionBtn)!!.setOnClickListener ***REMOVED***
            imageCapture.takePicture(***REMOVED******REMOVED***, object: ImageCapture.OnImageCapturedListener() ***REMOVED***
                override fun onError(
                    imageCaptureError: ImageCapture.ImageCaptureError,
                    message: String,
                    cause: Throwable?
                ) ***REMOVED***
                    Toast.makeText(context, "Photo could not be taken due to $message", Toast.LENGTH_SHORT).show()
        ***REMOVED***

                override fun onCaptureSuccess(image: ImageProxy?, rotationDegrees: Int) ***REMOVED***
                    super.onCaptureSuccess(image, rotationDegrees)
                    processBarcode(image, rotationDegrees)
        ***REMOVED***
    ***REMOVED***)
***REMOVED***
***REMOVED***

    private fun updateTransform() ***REMOVED***
        val matrix = Matrix()
        val centreX = cameraPreviewView!!.width / 2f
        val centreY = cameraPreviewView!!.height / 2f
        val view = cameraPreviewView

        val rotationDegrees = when (view?.display!!.rotation) ***REMOVED***
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
***REMOVED***
        matrix.postRotate(-rotationDegrees.toFloat(), centreX, centreY)
        view.setTransform(matrix)
***REMOVED***

    private fun processBarcode(image: ImageProxy?, rotationDegrees: Int) ***REMOVED***
//        val detector = BarcodeDetector.Builder(context)
//            .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
//            .build()
//        if (!detector.isOperational) ***REMOVED***
//            Toast.makeText(context, "Unable to set up the detector!", Toast.LENGTH_SHORT).show()
//            return
//***REMOVED***
//
//        val barcodes = SparseArray<Barcode>()
//        val productDetails = HashMap<String,Barcode.UrlBookmark>()
//        for (barcode in barcodes.valueIterator()) ***REMOVED***
//            productDetails[barcode.rawValue] = barcode.url
//***REMOVED***

        val mia = MyImageAnalyzer()
        mia.analyze(image, rotationDegrees)

//        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
//            .build()
        val detector = FirebaseVision.getInstance().visionBarcodeDetector
        val result = detector.detectInImage(mia.image as FirebaseVisionImage)
            .addOnSuccessListener ***REMOVED***
                for (barcode in it) ***REMOVED***
                    when (barcode.valueType) ***REMOVED***
                        FirebaseVisionBarcode.FORMAT_UPC_A, FirebaseVisionBarcode.FORMAT_UPC_E,
                            FirebaseVisionBarcode.FORMAT_EAN_8, FirebaseVisionBarcode.FORMAT_EAN_13,
                            FirebaseVisionBarcode.TYPE_ISBN -> ***REMOVED***
                            val url = URL("https://api.barcodelookup.com/v2/products?barcode=$***REMOVED***barcode.rawValue***REMOVED***" +
                                    "&formatted=y&key=1") // TODO store API key securely
                            val jsonObject = JSONObject(url.readText())
                            for (index in 0 until jsonObject.getJSONArray("Stores").length()) ***REMOVED***
                                val store: String = jsonObject.getJSONArray("Stores")[index] as String
                                pTransactions.add(Integer.parseInt(JSONObject(store).get("store_price") as String))
                    ***REMOVED***
                ***REMOVED***
                        FirebaseVisionBarcode.TYPE_URL -> ***REMOVED***
                            val url = barcode.url!!.url
                ***REMOVED***
                        else -> ***REMOVED***
                            Toast.makeText(context, "Currently not available", Toast.LENGTH_SHORT).show()
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
            .addOnFailureListener ***REMOVED***

    ***REMOVED***

        if (result.isSuccessful) ***REMOVED***
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
***REMOVED***
***REMOVED***
***REMOVED***