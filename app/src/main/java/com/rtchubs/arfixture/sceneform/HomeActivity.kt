package com.rtchubs.arfixture.sceneform

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.media.CamcorderProfile
import android.net.Uri
import android.os.*
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.PixelCopy
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.PlaneRenderer
import com.google.ar.sceneform.rendering.PlaneRenderer.MATERIAL_UV_SCALE
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.TransformableNode
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.rtchubs.arfixture.BuildConfig
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.models.Product
import com.rtchubs.arfixture.ui.product_ar_view.ProductARViewListAdapter
import com.rtchubs.arfixture.util.AppConstants
import com.rtchubs.arfixture.util.showErrorToast
import com.rtchubs.arfixture.util.showSuccessToast
import kotlinx.android.synthetic.main.activity_ar_texture.*
import kotlinx.coroutines.Dispatchers
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.progress.ProgressMonitor
import java.io.*
import java.util.concurrent.Callable
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {

    private var arFragment: WritingArFragment? = null
    private var andyRenderable: ModelRenderable? = null
    private val MIN_OPENGL_VERSION = 3.0
    private val TAG = HomeActivity::class.java.getSimpleName()
    // VideoRecorder encapsulates all the video recording functionality.
    private var videoRecorder: VideoRecorder? = null
    // The UI to record.
    private var recordButton: FloatingActionButton? = null

    private var anchorNode: AnchorNode? = null
    private var isTracking: Boolean = false
    private var isHitting: Boolean = false
    private var pointer: PointerDrawable = PointerDrawable()
    private var arModelsFolderPath = ""
    private lateinit var productARViewListAdapter: ProductARViewListAdapter

    companion object {
        var productList = ArrayList<Product>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra(AppConstants.KEY_FILE_PATH) && intent.hasExtra(AppConstants.KEY_FILE_NAME)) {
            val filePath = intent.getStringExtra(AppConstants.KEY_FILE_PATH) ?: ""
            val fileName = intent.getStringExtra(AppConstants.KEY_FILE_NAME) ?: ""
            if (filePath.isNotBlank() && fileName.isNotBlank()) {
                arModelsFolderPath = "${filePath}/${fileName}"
            } else {
                showErrorToast(this, "Unable to show AR View!")
            }
        } else {
            showErrorToast(this, "Unable to show AR View!")
            onBackPressed()
        }

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }
        setContentView(R.layout.activity_ar_texture)

        window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.app_name)
        if (intent.hasCategory("tree_co")) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as WritingArFragment?

//        val adapter = TreeAdapter()
//        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = adapter

//        adapter.updateList(getArTreeData())
//        adapter.setOnItemClickListener(object : TreeAdapter.OnItemClickListener {
//            override fun onItemClick(view: View?, position: Int) {
//                val clickedData = adapter.runHistoryList[position]
////                createNewModelRender(clickedData.rendable)
//                addObject(clickedData.rendable)
//            }
//        })

        productARViewListAdapter = ProductARViewListAdapter {
            addObject()
        }
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = productARViewListAdapter

        productARViewListAdapter.submitList(productList)

        arFragment?.arSceneView?.scene?.addOnUpdateListener { frameTime ->
            arFragment?.onUpdate(frameTime)
            onUpdate()
        }

//        setPlaneTexture("trigrid.png")

        /*arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            if (andyRenderable != null) {

                // Create the Anchor.
                val anchor = hitResult.createAnchor()
                anchorNode = AnchorNode(anchor)
                anchorNode?.setParent(arFragment?.arSceneView?.scene)

                // Create the transformable andy and add it to the anchor.
                val andy = TransformableNode(arFragment?.transformationSystem)
                andy.setParent(anchorNode)
                andy.renderable = andyRenderable
                andy.select()
            }

        }*/

        initVideoRecorder()
        recordButton = fabvideo

        recordButton?.setOnClickListener {
            toggleRecording(it)
        }

        fabcapture.setOnClickListener {
            takePhoto()
        }

        fabclear.setOnClickListener {
            clearObjectsFromScene()
        }

        fabrate.setOnClickListener {
            rateApp()
        }

    }

    private fun rateApp() {
        val uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)
        var intent = Intent(Intent.ACTION_VIEW, uri)
//      To count with Play market backstack, After pressing back button,
//      to taken back to our application, we need to add following flags to intent.
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
        else {
            intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "No play store or browser app found", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Sets the plane renderer texture.
     * @param texturePath - Path to texture to use in the assets directory.
     */
    private fun setPlaneTexture(texturePath: String) {
        val sampler = Texture.Sampler.builder()
                .setMinFilter(Texture.Sampler.MinFilter.LINEAR_MIPMAP_LINEAR)
                .setMagFilter(Texture.Sampler.MagFilter.LINEAR)
                .setWrapModeR(Texture.Sampler.WrapMode.REPEAT)
                .setWrapModeS(Texture.Sampler.WrapMode.REPEAT)
                .setWrapModeT(Texture.Sampler.WrapMode.REPEAT)
                .build()

        Texture.builder().setSource { assets.open(texturePath) }
                .setSampler(sampler)
                .build().thenAccept { texture ->
                    arFragment?.arSceneView?.getPlaneRenderer()!!.getMaterial()
                            .thenAccept { material ->
                                material.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture)
                                material.setFloat(MATERIAL_UV_SCALE, 10f)
                            }
                }.exceptionally { ex ->
                    Log.e("HomeActivity", "Failed to read an asset file", ex)
                    null
                }
    }

    private fun addARObject() {

    }

    private fun placeARObject(arFragment: WritingArFragment, anchor: Anchor?) {
//        val path = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        val modelPath = "$path/unzippedARModels/ar_models/tree_nine.sfb"
        val modelPath = "$arModelsFolderPath/tree_nine.sfb"
        val objectFile = File(modelPath)
        val callable: Callable<InputStream> = Callable {
            FileInputStream(objectFile);
        }
        ModelRenderable.builder()
            .setSource(applicationContext, callable)
            .build()
            .thenAccept { renderable -> addNodeToScene(arFragment, anchor!!, renderable) }
            .exceptionally { throwable ->
                val builder = AlertDialog.Builder(this)
                builder.setMessage(throwable.message)
                    .setTitle("Something wrong!")
                val dialog = builder.create()
                dialog.show()
                null

            }
    }

    private fun addObject() {
        var frame: Frame = arFragment?.arSceneView?.arFrame!!
        var pt: Point = getScreenCenter()
        val hits: List<HitResult>
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    placeARObject(arFragment!!, hit.createAnchor())
                    //placeObject(arFragment!!, hit.createAnchor(), rendable)
                    break

                }
            }
        }
    }

    private fun placeObject(arFragment: WritingArFragment, anchor: Anchor?, rendable: Int) {
        val renderableFuture = ModelRenderable.builder()
                .setSource(arFragment.context, rendable)
                .build()
                .thenAccept { renderable -> addNodeToScene(arFragment, anchor!!, renderable) }
                .exceptionally { throwable ->
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(throwable.message)
                            .setTitle("Something wrong!")
                    val dialog = builder.create()
                    dialog.show()
                    null

                }
    }

    fun createNewModelRender(modelID: Int) {
        ModelRenderable.builder()
                .setSource(this, modelID)
                .build()
                .thenAccept { renderable ->
                    andyRenderable = renderable
                    Toast.makeText(this, "Tree loaded", Toast.LENGTH_LONG).show()
                }
                .exceptionally { throwable ->
                    Toast.makeText(this, "Unable to load tree renderable", Toast.LENGTH_LONG).show()
                    null
                }
    }


    /*
    *
    *
    * */
/*    private fun addObject(model: Uri){
        var frame : Frame = arFragment?.arSceneView?.arFrame!!
        var pt : Point = getScreenCenter()
        val hits: List<HitResult>
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    placeObject(arFragment!!, hit.createAnchor(), model)
                    break

                }
            }
        }
    }

    private fun placeObject(fragment: ArFragment, anchor : Anchor, model: Uri){
        val renderableFuture = ModelRenderable.builder()
                .setSource(fragment.context, model)
                .build()
                .thenAccept { renderable -> addNodeToScene(fragment, anchor, renderable) }
                .exceptionally { throwable ->
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(throwable.message)
                            .setTitle("Something wrong!")
                    val dialog = builder.create()
                    dialog.show()
                    null
                }
    }*/

    private fun addNodeToScene(fragment: WritingArFragment, anchor: Anchor, renderable: Renderable) {
        var anchorNode = AnchorNode(anchor)
        var nodeObj = TransformableNode(fragment.getTransformationSystem())
        nodeObj.renderable = renderable
        nodeObj.setParent(anchorNode)
        fragment.arSceneView.scene.addChild(anchorNode)
        nodeObj.select()
    }

    /*
    *
    *
    * */

    private fun clearObjectsFromScene() {
        val children = ArrayList<Node>(arFragment?.getArSceneView()?.getScene()?.getChildren()!!);
        for (node in children) {
            if (node is AnchorNode) {
                if (node.anchor != null) {
                    node.anchor!!.detach()
                }
            }
        }
    }

    private fun onUpdate() {
        var trackingChanged: Boolean = updateTracking()
        var contentView: View = findViewById(android.R.id.content)
        if (trackingChanged) {
            contentView.overlay.add(pointer!!)
        } else {
            contentView.overlay.remove(pointer!!)
        }
        contentView.invalidate()
        if (isTracking) {
            var hitTestChanged: Boolean = updateHitTest()
            if (hitTestChanged) {
                pointer?.enabled = isHitting

                if (isHitting){
                    tv_surface.text = "Updated!"
                }else{
                    tv_surface.text = "Updating surface, please wait..."
                }

                contentView.invalidate()

            }
        }
    }

    private fun showUpdateMessage(){
//        isFound = false
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "Updating surface, please wait!", Snackbar.LENGTH_INDEFINITE)
        snackbar.show()
    }

    private fun showSuccessMessage(){
//        if (isFound){
//            return
//        }
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "Done!", Snackbar.LENGTH_SHORT)
        snackbar.show()

//        isFound = true
    }

    private fun updateHitTest(): Boolean {
        var frame: Frame = arFragment?.arSceneView?.arFrame!!
        var pt: Point = getScreenCenter()
        var hits: List<HitResult>
        var wasHitting: Boolean = isHitting
        isHitting = false;
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && (trackable as Plane).isPoseInPolygon(hit.hitPose)) {
                    isHitting = true
                    break
                }
            }
        }
        return wasHitting !== isHitting
    }

    private fun getScreenCenter(): Point {
        var vw: View = findViewById(android.R.id.content)
        return android.graphics.Point(vw.width / 2, vw.height / 2)
    }

    private fun updateTracking(): Boolean {
        var frame: Frame = arFragment?.arSceneView?.arFrame!!
        var wasTracking: Boolean = isTracking
        isTracking = frame != null &&
                frame.getCamera().getTrackingState() == TrackingState.TRACKING;
        return isTracking != wasTracking;
    }

    private fun removeAnchorNode(nodeToremove: AnchorNode) {
        //Remove an anchor node
        if (nodeToremove != null) {
            arFragment?.arSceneView?.scene?.removeChild(nodeToremove)
            nodeToremove.anchor?.detach();
            nodeToremove.setParent(null);
            Toast.makeText(this, "Test Delete - anchorNode removed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Test Delete - markAnchorNode was null", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onPause() {
        if (videoRecorder!!.isRecording) {
            toggleRecording(null)
        }
        super.onPause()

    }

    private fun initVideoRecorder() {
        // Initialize the VideoRecorder.
        videoRecorder = VideoRecorder()
        val orientation = resources.configuration.orientation
        videoRecorder?.setVideoQuality(CamcorderProfile.QUALITY_2160P, orientation)
        videoRecorder?.setSceneView(arFragment!!.getArSceneView())
    }

    /*
   * Used as a handler for onClick, so the signature must match onClickListener.
   */
    private fun toggleRecording(unusedView: View?) {
        if (!arFragment!!.hasWritePermission()) {
            Log.e(TAG, "Video recording requires the WRITE_EXTERNAL_STORAGE permission")
            Toast.makeText(
                    this,
                    "Video recording requires the WRITE_EXTERNAL_STORAGE permission",
                    Toast.LENGTH_LONG)
                    .show()
            arFragment?.launchPermissionSettings()
            return
        }
        val recording = videoRecorder!!.onToggleRecord()
        if (recording) {
            recordButton?.colorNormal = ContextCompat.getColor(this , R.color.colorRed)
            recordButton?.setImageResource(R.drawable.ic_stop)
        } else {
            recordButton?.colorNormal = ContextCompat.getColor(this , R.color.colorAccent)
            recordButton?.setImageResource(R.drawable.ic_recording)

            val videoPath = videoRecorder?.videoPath!!.absolutePath
            Toast.makeText(this, "Video saved at: $videoPath", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Video saved: $videoPath")

            // Send notification of updated content.
            val values = ContentValues()
            values.put(MediaStore.Video.Media.TITLE, "Sceneform Video")
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATA, videoPath)
            contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)


            val snackbar = Snackbar.make(findViewById(android.R.id.content), "Video saved", Snackbar.LENGTH_LONG)
            snackbar.setAction("Open Video") { v ->
                val intent = Intent(this, VideoViewActivity::class.java)
                intent.putExtra("videoPath", videoPath)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(intent)

            }
            snackbar.show()

        }
    }


    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     *
     * Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     *
     * Finishes the activity if Sceneform can not run
     */
    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later")
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show()
            activity.finish()
            return false
        }
        val openGlVersionString = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show()
            activity.finish()
            return false
        }
        return true
    }

    private fun takePhoto() {
        var filename = generateFilename()
        var view: ArSceneView = arFragment!!.arSceneView
        // Create a bitmap the size of the scene view.
        var bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var handlerThread = HandlerThread("PixelCopier")
        handlerThread.start()
        PixelCopy.request(view, bitmap, { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename);
                } catch (ex: Exception) {
                    val toast = Toast.makeText(this@HomeActivity, ex.localizedMessage, Toast.LENGTH_LONG)
                    toast.show()
                    //return@PixelCopy.request
                    return@request
                }
                val snackbar = Snackbar.make(findViewById(android.R.id.content), "Photo saved", Snackbar.LENGTH_LONG)
                snackbar.setAction("Open Photo") { v ->
                    val photoFile = File(filename)
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this@HomeActivity,
                        "${BuildConfig.APPLICATION_ID}.provider",
                        photoFile
                    )
//                    val photoURI = FileProvider.getUriForFile(this@HomeActivity,
//                            this@HomeActivity.getPackageName() + ".ar.codelab.name.provider",
//                            photoFile)
//                    val intent = Intent(Intent.ACTION_VIEW, photoURI)
//                    intent.setDataAndType(photoURI, "image/*")
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    startActivity(intent)

                    val intent = Intent(this, PhotoViewActivity::class.java)
                    intent.putExtra("imageUri", photoURI.toString())
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(intent)

                }
                snackbar.show()
            } else {
                val toast = Toast.makeText(this@HomeActivity, "Failed to copyPixels: $copyResult", Toast.LENGTH_LONG)
                toast.show()
            }
            handlerThread.quitSafely()
        }, Handler(handlerThread.looper))
    }

    private fun generateFilename(): String {
        var date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date());
        var path = "${getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}" +
                File.separator + "Sceneform/" + date + "_screenshot.jpg";
        return path
    }

    private fun saveBitmapToDisk(bitmap: Bitmap, filename: String) {
        var out = File(filename)
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        try {
            var outputStream = FileOutputStream(filename)
            var outputData = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData)
            outputData.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (ex: IOException) {
            throw IOException("Failed to save bitmap to disk", ex)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
