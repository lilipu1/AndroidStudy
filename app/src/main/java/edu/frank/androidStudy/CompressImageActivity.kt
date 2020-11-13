package edu.frank.androidStudy

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import edu.frank.androidStudy.databinding.ActivityCompressImageBinding
import edu.frank.androidStudy.extension.setBindingLayout
import java.io.File

class CompressImageActivity : AppCompatActivity() {

    lateinit var binding: ActivityCompressImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setBindingLayout(this, R.layout.activity_compress_image)

        requestPermissions(
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1002
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1002 && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
            binding.cameraView.setLifecycleOwner(this)
            binding.cameraView.open()
            binding.btTakePhoto.setOnClickListener {
                binding.cameraView.takePicture()
            }

            binding.cameraView.addCameraListener(object : CameraListener() {

                override fun onPictureTaken(result: PictureResult) {
                    super.onPictureTaken(result)
                    val dir = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath,
                        "study"
                    )
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    val file = File(dir, "测试.jpg")
                    if (file.exists()) {
                        file.delete()
                    }
                    result.toFile(file) { file ->
                        file?.let { image ->
                            Glide.with(this@CompressImageActivity)
                                .load(file)
                                .skipMemoryCache(true)
                               // .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(binding.ivPhoto)
                        }
                    }
                }
            })
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}