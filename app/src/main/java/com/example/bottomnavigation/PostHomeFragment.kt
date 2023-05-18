package com.example.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bottomnavigation.BaseFragment
import com.example.bottomnavigation.CreatePostFragment
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapter.PostsAdapter
import com.example.bottomnavigation.database.PostsDatabase
import com.example.bottomnavigation.entities.Posts
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_post_home.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

//inherit from BaseFragment
class PostHomeFragment : BaseFragment() {

    // creates an ArrayList of Posts
    var arrPosts = ArrayList<Posts>()

    //create instance of the PostsAdapter class called postsAdapter.
    var postsAdapter: PostsAdapter = PostsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    //default fun
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_post_home, container, false)

    }


    //create with a newInstance() method that returns a new instance of HomeFragment with no arguments
    //to provide a way to create instances of a class without having to call the constructor directly
    companion object {
        @JvmStatic
        fun newInstance() =
            PostHomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sets the recycler_view's fixed size to true
        recycler_view.setHasFixedSize(true)

        //sets layout manager to a new instance of StaggeredGridLayoutManager with 2 columns and a vertical orientation
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        // launches a coroutine that retrieves all the posts from a PostsDatabase
        // sets the data in the postsAdapter and arrPosts ArrayList
        // then sets the recycler_view's adapter to the postsAdapter.
        launch {
            context?.let {
                var posts = PostsDatabase.getDatabase(it).postDao().getAllPosts()
                postsAdapter!!.setData(posts)
                arrPosts = posts as ArrayList<Posts>
                recycler_view.adapter = postsAdapter
            }
        }

        //sets the postsAdapter's onClickListener to a new instance of the PostsAdapter.OnItemClickListener interface
        postsAdapter!!.setOnClickListener(onClicked)

        fabBtnCreatePost.setOnClickListener {
            //replaces the current fragment with a new instance of CreatePostFragment
            replaceFragment(CreatePostFragment.newInstance(), false)
        }


        //search method
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //p0 parameter represents the new query text entered by the user.
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            //p0 parameter represents the new query text entered by the user.
            override fun onQueryTextChange(p0: String?): Boolean {

                //This array will hold the search results
                var tempArr = ArrayList<Posts>()

                for (arr in arrPosts) {
                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())) {
                        //if title matches search, added to the tempArr list
                        tempArr.add(arr)
                    }
                }

                // setting the search results in the PostsAdapter instance
                // so that the adapter can show the search results in the RecyclerView
                postsAdapter.setData(tempArr)

                // to notify the RecyclerView that the data set has changed
                // it should refresh the view with the new data
                postsAdapter.notifyDataSetChanged()

                //the query text has been settle
                return true
            }

        })


    }


    private val onClicked = object : PostsAdapter.OnItemClickListener {

        //postsId, which represents the ID of the clicked post
        override fun onClicked(postsId: Int) {
            var fragment: Fragment
            //Bundle, is used to pass data between fragments.
            var bundle = Bundle()
            //CreatePostFragment will receive the postId
            bundle.putInt("postId", postsId)
            fragment = CreatePostFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment, false)
        }

    }


    //istransition, a Boolean value indicating whether or not to include a transition animation
    fun replaceFragment(fragment: Fragment, istransition: Boolean) {

        // to perform a set of operations (such as adding, removing, or replacing fragments) on the FragmentManager
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        //istransition is true
        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        //addToBackStack method allowing the user to navigate back to the previous fragment with the back button
        // commit is called to execute the transaction
        fragmentTransition.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }


}