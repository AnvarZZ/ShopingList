package az.anvar.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import az.anvar.shopinglist.R

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity_Anvar"

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
        })
        viewModel.getShopList()

    }
}