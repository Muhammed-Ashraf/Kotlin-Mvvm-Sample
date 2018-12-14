package com.example.user.kotlin_mvvm_sample.ui.apicall

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.kotlin_mvvm_sample.R

class ApiCallFragment : Fragment() {

    companion object {
        fun newInstance() = ApiCallFragment()
    }

    private lateinit var viewModel: ApiCallViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.api_call_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ApiCallViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
