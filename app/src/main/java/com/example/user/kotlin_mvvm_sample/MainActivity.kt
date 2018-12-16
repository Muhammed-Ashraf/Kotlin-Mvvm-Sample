package com.example.user.kotlin_mvvm_sample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.user.kotlin_mvvm_sample.data.model.Cryptocurrency
import com.example.user.kotlin_mvvm_sample.data.model.Post
import com.example.user.kotlin_mvvm_sample.ui.apicall.ApiCallFragment
import com.example.user.kotlin_mvvm_sample.ui.main.CryptocurrenciesViewModel
import com.example.user.kotlin_mvvm_sample.ui.main.CryptocurrenciesViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var cryptocurrenciesViewModelFactory: CryptocurrenciesViewModelFactory
    lateinit var cryptocurrenciesViewModel: CryptocurrenciesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        AndroidInjection.inject(this)

        cryptocurrenciesViewModel = ViewModelProviders.of(this, cryptocurrenciesViewModelFactory).get(
            CryptocurrenciesViewModel::class.java
        )

        cryptocurrenciesViewModel.loadPosts()

        cryptocurrenciesViewModel.postsResult().observe(this,
            Observer<List<Post>> {
                //                hello_world_textview.text = "Hello ${it?.size} cryptocurrencies"
                Toast.makeText(this, "Hello ${it?.size}", Toast.LENGTH_LONG).show()
            })

        cryptocurrenciesViewModel.postsError().observe(this, Observer<String> {
            //            hello_world_textview.text = "Hello error $it"
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        })
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ApiCallFragment.newInstance())
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector
}
