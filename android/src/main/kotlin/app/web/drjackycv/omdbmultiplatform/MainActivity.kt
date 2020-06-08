package app.web.drjackycv.omdbmultiplatform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.web.drjackycv.omdbmultiplatform.shared.HelloWorldMessage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        mainMessageTxt.text = HelloWorldMessage()
    }

}
