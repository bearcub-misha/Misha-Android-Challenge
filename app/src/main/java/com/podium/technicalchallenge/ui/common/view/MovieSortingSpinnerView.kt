package com.podium.technicalchallenge.ui.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.MovieSortingSpinnerBinding

class MovieSortingSpinnerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = MovieSortingSpinnerBinding.inflate(LayoutInflater.from(context), this)

    private var onChangedListener: OnChangedListener? = null

    init {
        ArrayAdapter.createFromResource(
            context,
            R.array.sort_movies_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.orderBy.adapter = adapter
        }
        binding.orderBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.direction.setOnClickListener {
            binding.direction.scaleY = binding.direction.scaleY * -1
            onChange()
        }
    }

    fun onChange() {
        onChangedListener?.let {
            it.onChanged(
                binding.orderBy.selectedItem.toString().uppercase(),
                if (binding.direction.scaleY > 0) SortDirection.ASC else SortDirection.DESC
            )
        }
    }

    fun setOnChangedListener(onChangedListener: OnChangedListener) {
        this.onChangedListener = onChangedListener
    }
}

interface OnChangedListener {
    fun onChanged(sortBy: String, direction: SortDirection)
}

enum class SortDirection {
    ASC,
    DESC
}