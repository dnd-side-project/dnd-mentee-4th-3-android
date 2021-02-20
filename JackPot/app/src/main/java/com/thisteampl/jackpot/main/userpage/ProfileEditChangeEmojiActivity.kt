package com.thisteampl.jackpot.main.userpage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_profile_edit_change_emoji.*

//https://m.blog.naver.com/l5547/221845481754, OnActivityResult

class ProfileEditChangeEmojiActivity: AppCompatActivity() {

    private var emoji = "result"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit_change_emoji)

        setupView()
    }

    private fun setupView(){
        profile_edit_change_emoji_back_button.setOnClickListener { onBackPressed() }

        for(i in 0 until profile_edit_change_emoji_layout.childCount) {
            val child: View = profile_edit_change_emoji_layout.getChildAt(i)
            if(child is Button) {
                child.setOnClickListener{ this.btnOnClick(child) }
            }
        }

        profile_edit_change_state_confirm_button.setOnClickListener {
            if(emoji == "result") {
                Toast.makeText(this, "이모티콘을 골라주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent().putExtra("emoji", emoji)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }


    private fun btnOnClick(button: Button) {

        var bg = ContextCompat.getDrawable(this, R.drawable.android_emoji_select_background)
        for(i in 0 until profile_edit_change_emoji_layout.childCount) {
            val child: View = profile_edit_change_emoji_layout.getChildAt(i)

            if(child == profile_edit_change_state_confirm_button) { continue }

            if(child is Button) {
                if(child == button) {
                    emoji = child.text.toString()
                    child.background = bg
                } else {
                    child.background = ContextCompat.getDrawable(this, R.color.transparent)
                }
            }
        }
    }
}