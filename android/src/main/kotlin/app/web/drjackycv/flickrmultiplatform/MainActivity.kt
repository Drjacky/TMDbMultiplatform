package app.web.drjackycv.flickrmultiplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.web.drjackycv.flickrmultiplatform.shared.HelloWorldMessage
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
