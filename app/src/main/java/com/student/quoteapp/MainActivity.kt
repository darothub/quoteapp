package com.student.quoteapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.student.quoteapp.QuoteList.list
import com.student.quoteapp.databinding.ActivityMainBinding
import com.student.quoteapp.databinding.DialogLayoutBinding
import com.student.quoteapp.helpers.makeFullScreen
import com.wajahatkarim3.easyflipviewpager.CardFlipPageTransformer2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import android.graphics.drawable.InsetDrawable
import android.widget.Toast
import kotlinx.coroutines.withContext


object QuoteList {
    var list = arrayListOf(
        Quote(text = "I am worthy of a wealthy life."),
        Quote(text ="Even though I have messed up in the past, " +
                "I can still create a positive future."),
        Quote(text ="I am perfect for myself, I don't need to be perfect for anyone else."),
        Quote(text ="Healing is a process and I make sure " +
                "I dedicate time to it when going through a stressful" +
                "situation.")
    )
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quoteViewModel: QuoteViewModel by viewModels{ QuoteViewModelFactory((application as MainApp).repository) }
    lateinit var vpAdapter:CarouselAdapter
    lateinit var dialog: Dialog
    lateinit var dialogLayoutBinding: DialogLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeFullScreen(binding.root)
        val cardFlipPageTransformer = CardFlipPageTransformer2()
        dialog = Dialog(this, android.R.style.Theme_Dialog)
        vpAdapter = CarouselAdapter( binding.vp, object: ClickEvents{
            override fun likeEventListener(quote: Quote) {
                quoteViewModel.insertQuote(quote)

            }
        })
        binding.vp.apply {
            adapter = vpAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            setPageTransformer(cardFlipPageTransformer)
        }

        quoteViewModel.allQuotes?.observe(this){
            vpAdapter.submitList(it)
        }

        binding.fab.setOnClickListener {
            launchDialog()
        }

    }

    private fun launchDialog(){
        dialogLayoutBinding = DialogLayoutBinding.inflate(layoutInflater)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 70)
        dialog.apply {
            dialog.setContentView(dialogLayoutBinding.root)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(inset)
            show()
        }

        dialogLayoutBinding.dialogBtn.setOnClickListener {
            val newQuoteText = dialogLayoutBinding.editText.text.toString()
            CoroutineScope(IO).launch {
                when(newQuoteText.isBlank()){
                    true -> {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Empty text is not allowed", Toast.LENGTH_SHORT).show()
                        }
                    }
                    false -> addNewQuote(newQuoteText)
                }

            }
        }

    }
    private fun addNewQuote(quoteText:String){
        quoteViewModel.insertQuote(Quote(text = quoteText))
        dialog.dismiss()
    }

}