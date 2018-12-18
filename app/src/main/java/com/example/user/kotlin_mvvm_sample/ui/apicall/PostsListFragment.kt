package com.example.user.kotlin_mvvm_sample.ui.apicall

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.kotlin_mvvm_sample.R
import com.example.user.kotlin_mvvm_sample.data.model.Post
import com.example.user.kotlin_mvvm_sample.ui.main.PostsViewModelFactory
import com.example.user.kotlin_mvvm_sample.databinding.PostListFragmentBinding
import com.example.user.kotlin_mvvm_sample.ui.main.PostsActivity
import com.example.user.kotlin_mvvm_sample.ui.main.PostsViewModel
import com.example.user.kotlin_mvvm_sample.utils.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostsListFragment : Fragment() {

    private lateinit var binding: PostListFragmentBinding

    private var postsAdapter: PostsAdapter? = null
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
//    @Inject
//    lateinit var postsViewModelFactory: PostsViewModelFactory
    lateinit var postsViewModel: PostsViewModel

    companion object {
        fun newInstance() = PostsListFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.post_list_fragment, container, false)
        postsAdapter = PostsAdapter(postClickCallback)
        binding.postsList.setAdapter(postsAdapter)


        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


//        postsViewModel = ViewModelProviders.of(this, postsViewModelFactory).get(
//            PostsViewModel::class.java
//        )

        postsViewModel = ViewModelProviders.of(this, viewModelFactory).get(PostsViewModel::class.java!!)
        postsViewModel.loadPosts()

        postsViewModel.postsResult().observe(this,
            Observer<List<Post>> {
                //                hello_world_textview.text = "Hello ${it?.size} cryptocurrencies"
                postsAdapter!!.setPostList(it!!)

            })

//        postsViewModel.postsError().observe(this, Observer<String> {
////            //            hello_world_textview.text = "Hello error $it"
//            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
//        })

        postsViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null)
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        })

        binding.viewModel = postsViewModel
    }

    private val postClickCallback = object : PostClickCallback {
        override fun onClick(post: Post) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
//                (activity as PostsActivity).show(post)
            }
        }
    }
}
