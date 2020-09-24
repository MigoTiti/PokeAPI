package com.lucasrodrigues.pokemonshowcase.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


abstract class BaseActivity<T : ViewDataBinding, R : ViewModel> : AppCompatActivity() {

    protected val alertService by inject<AlertService> { parametersOf(this as Activity) }
    protected val resourceService by inject<ResourceService> { parametersOf(this as Activity) }
    protected val navigationService by inject<NavigationService> { parametersOf(this as Activity) }

    abstract val viewModel: R
    abstract val layoutId: Int

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            layoutId
        )

        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
    }
}