package com.example.user.kotlin_mvvm_sample.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.user.kotlin_mvvm_sample.R
import com.example.user.kotlin_mvvm_sample.data.model.Post

import com.example.user.kotlin_mvvm_sample.ui.apicall.PostsListFragment
import com.example.user.kotlin_mvvm_sample.databinding.PostsActivityBinding
import com.example.user.kotlin_mvvm_sample.utils.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class PostsActivity : AppCompatActivity(), HasSupportFragmentInjector {

    private var errorSnackbar: Snackbar? = null

    private lateinit var binding: PostsActivityBinding

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    //    @Inject
//    lateinit var postsViewModelFactory: PostsViewModelFactory
    lateinit var postsViewModel: PostsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.posts_activity)
        AndroidInjection.inject(this)

        postsViewModel = ViewModelProviders.of(this, viewModelFactory).get(PostsViewModel::class.java!!)

//        postsViewModel = ViewModelProviders.of(this, postsViewModelFactory).get(
//            PostsViewModel::class.java
//        )

        postsViewModel.loadPosts()

//        postsViewModel.postsResult().observe(this,
//            Observer<List<Post>> {
//                //                hello_world_textview.text = "Hello ${it?.size} cryptocurrencies"
//                Toast.makeText(this, "Hello ${it?.size}", Toast.LENGTH_LONG).show()
//
//            })

//        postsViewModel.postsError().observe(this, Observer<String> {
////            //            hello_world_textview.text = "Hello error $it"
//            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
//        })

        postsViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = postsViewModel
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PostsListFragment.newInstance())
                .commitNow()
        }
    }

    private fun showError(errorMessage: String) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction("Retry", postsViewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector
}
