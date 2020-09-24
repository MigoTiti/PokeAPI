package com.lucasrodrigues.pokemonshowcase.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.lucasrodrigues.pokemonshowcase.view.BaseActivity

abstract class BaseFragment<T : ViewDataBinding, R : ViewModel> : Fragment() {
    val alertService by lazy { (activity as BaseActivity<*, *>).alertService }
    val resourceService by lazy { (activity as BaseActivity<*, *>).resourceService }
    val navigationService by lazy { (activity as BaseActivity<*, *>).navigationService }

    abstract val viewModel: R
    abstract val layoutId: Int

    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )

        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this

        return binding.root
    }
}