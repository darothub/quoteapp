package com.student.quoteapp

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.student.quoteapp.databinding.CarouselItemBinding


class CarouselAdapter( var viewPager2: ViewPager2, var clickEvents: ClickEvents) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>(), ViewPagerCallBack {
    lateinit var binding: CarouselItemBinding
    val innerList = ArrayList<Quote>()
    inner class CarouselViewHolder(val binding: CarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote, clickEvents: ClickEvents) {
            var contxt = binding.like.context
            var likeDrawable = ContextCompat.getDrawable(contxt, R.drawable.heart)
            when(quote.liked){
                true -> likeDrawable?.setColorFilter(ContextCompat.getColor(binding.like.context,
                    R.color.quote_red), PorterDuff.Mode.SRC_IN)
            }
            binding.quoteTv.text = quote.text
            binding.like.setImageDrawable(likeDrawable)

            binding.like.setOnClickListener {
                quote.liked = !quote.liked
                clickEvents.likeEventListener(quote)
            }

        }
    }
    fun submitList(list: List<Quote>,){
        innerList.clear()
        innerList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = innerList[position]
        holder.bind(item, clickEvents)
        continuousScrolling(position, viewPager2)
    }

    override fun getItemCount(): Int {
        return innerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun runnable() = Runnable {
        innerList.addAll(innerList)
        notifyDataSetChanged()
    }

    override fun continuousScrolling(position: Int, viewPager2: ViewPager2) {
        if (position == innerList.size - 2) {
            viewPager2.post(runnable())
        }
    }


}
interface ClickEvents{
    fun likeEventListener(quote: Quote)
}
interface ViewPagerCallBack {
    fun continuousScrolling(position: Int, viewPager2: ViewPager2)
}