package io.surepass.facesdkhelper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import io.surepass.facescanner.ui.activities.InitSDK
import io.surepass.facesdkhelper.databinding.ActivityStartBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var faceScanActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var response: String
    private var env: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerActivityForResult()
        binding.btnGetStarted.setOnClickListener {
            //val token = binding.etApiToken.text.toString().trim()
            val token = "YOUR TOKEN"
            val env = "PREPROD"
            openActivity(env,token)
        }

        binding.btnCopyButton.setOnClickListener {
            if (response.isNotEmpty()) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Response", response)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Response Copied...", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun openActivity(env: String, token: String) {
        val intent = Intent(this, InitSDK::class.java)
        intent.putExtra("token", token)
        intent.putExtra("env", env)
        faceScanActivityResultLauncher.launch(intent)
    }

    private fun registerActivityForResult() {
        faceScanActivityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val resultCode = result.resultCode
                val data = result.data
                if (resultCode == RESULT_OK && data != null) {
                    val faceScanResponse = data.getStringExtra("faceScanResponse")
                    Log.e("MainActivity", "FaceScan Response $faceScanResponse")
                    showResponse(faceScanResponse)
                }
            }
    }

    private fun showResponse(faceScanResponse: String?) {
        binding.etResponse.visibility = View.VISIBLE
        binding.btnCopyButton.visibility = View.VISIBLE
        binding.etResponse.setText(faceScanResponse.toString())
        response = faceScanResponse.toString()
    }
}
