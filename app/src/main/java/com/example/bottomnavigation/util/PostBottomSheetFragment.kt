package com.example.bottomnavigation.util

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.bottomnavigation.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_posts_bottom_sheet.*

class PostBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        var postId = -1
        fun newInstance(id:Int): PostBottomSheetFragment{
            val args = Bundle()
            val fragment = PostBottomSheetFragment()
            fragment.arguments = args
            postId = id
            return fragment
        }
    }


    // default fragment func
    // mainly about set the layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts_bottom_sheet,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (postId != -1){
            layoutDeletePost.visibility = View.VISIBLE
        }else{
            layoutDeletePost.visibility = View.GONE
        }
        setListener()
    }

    private fun setListener(){

        layoutShare.setOnClickListener{
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Your post content goes here")
            startActivity(Intent.createChooser(shareIntent, "Share Post"))
            dismiss()
        }
        layoutDeletePost.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","DeletePost")
            //sends a broadcast message with an action named "DeletePost" to all components within the same app that are registered to receive broadcasts with this action
            //it is used to notify other components that the user has selected to delete a post from the application.
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }

    }

}