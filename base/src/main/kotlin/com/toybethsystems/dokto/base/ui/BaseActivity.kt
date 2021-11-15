package com.toybethsystems.dokto.base.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<ViewModel : BaseViewModel, Binding: ViewBinding>
    : AppCompatActivity(),
    BaseFragmentCommunicator {

    lateinit var binding: Binding

    abstract val viewModel: ViewModel

    abstract val bindingInflater: ((inflater: LayoutInflater) -> Binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(viewModel)
    }

    override fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
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

    override fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean) {
        setSupportActionBar(toolbar)
        if (enableBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    override fun showOrHideActionBar(show: Boolean) {
        if(show) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }
}