package com.magma.aomlati.presentation.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.magma.aomlati.R
import com.magma.aomlati.databinding.ActivityShareBinding
import com.magma.aomlati.utils.LocalHelper
import com.magma.aomlati.utils.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.R.attr.label
import android.content.*

import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.magma.aomlati.utils.Const

class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        val isDarkTheme = sharedPreferences.getBoolean(Const.PREF_IS_DARK_THEME, false)
        if (isDarkTheme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        //lang
        LocalHelper.onCreate(this)

        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnDone.setOnClickListener {
            finish()
        }

        binding.btnCopy.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label.toString(), getString(R.string.app_link))
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@ShareActivity, getString(R.string.copied), Toast.LENGTH_SHORT)
                .show()
        }

        binding.btnShareApp.setOnClickListener {
            val intentInvite = Intent(Intent.ACTION_SEND)
            intentInvite.type = "text/plain"
            val body = getString(R.string.app_link)
            val subject = resources.getString(R.string.about_us)
            intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject)
            intentInvite.putExtra(Intent.EXTRA_TEXT, body)
            startActivity(Intent.createChooser(intentInvite, "Share using"))
        }
    }
}