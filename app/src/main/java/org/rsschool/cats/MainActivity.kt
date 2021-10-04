package org.rsschool.cats

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.resource.gif.GifDrawable
import dagger.hilt.android.AndroidEntryPoint
import org.rsschool.cats.ui.CatListener
import org.rsschool.cats.ui.ListCatsFragment
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer

private const val CODE = 100

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CatListener {

    private lateinit var photo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListCatsFragment.newInstance())
                .commitNow()
        }
    }

    override fun saveImage(image: ImageView) {
        photo = image
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    CODE
                )
            } else {
                saveImageToStorage(image)
            }
        } else {
            saveImageToStorage(image)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImageToStorage(photo)
            } else {
                Toast.makeText(
                    this, getString(R.string.permission_not_granted),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveImageToStorage(image: ImageView) {
        when (image.drawable) {
            is GifDrawable -> saveGif((image.drawable as GifDrawable).buffer)
            is BitmapDrawable -> saveJPG((image.drawable as BitmapDrawable).bitmap)
        }
    }

    private fun saveGif(buffer: ByteBuffer) {
        val filename = "${System.currentTimeMillis()}.gif"
        val storageDirectory = Environment.getExternalStorageDirectory().toString()
        val file = File(storageDirectory, filename)
        FileOutputStream(file).use {
            val bytes = ByteArray(buffer.capacity())
            (buffer.duplicate().clear() as ByteBuffer).get(bytes)
            it.write(bytes, 0, bytes.size)
            it.close()
            Toast.makeText(
                this,
                getString(R.string.saved_successfully, Uri.parse(file.absolutePath)),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveJPG(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, filename)
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, CODE, it)
                it.flush()
                it.close()
                Toast.makeText(
                    this,
                    getString(R.string.saved_successfully, Uri.parse(file.absolutePath)),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.unable_access), Toast.LENGTH_SHORT).show()
        }
    }
}
