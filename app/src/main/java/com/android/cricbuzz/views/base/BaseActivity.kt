package com.android.cricbuzz.views.base
import android.annotation.SuppressLint
import android.app.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewbinding.ViewBinding
import com.android.cricbuzz.R
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {
     lateinit var binding: V

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = createBinding()
        this.binding = binding
        setContentView(binding.root)
    }

    abstract fun createBinding(): V


    fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}