package org.rsschool.cats.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import org.rsschool.cats.R
import org.rsschool.cats.databinding.CatFragmentBinding
import org.rsschool.cats.getDefaultRequestOptions
import org.rsschool.cats.model.Image

private const val ARG_IMAGE = "item"

@AndroidEntryPoint
class CatFragment : Fragment() {

    private val binding get() = requireNotNull(_binding)
    private var listener: CatListener? = null
    private var image: Image? = null
    private var _binding: CatFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CatListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        CatFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = arguments?.getParcelable(ARG_IMAGE)
        views {
            toolBar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
            image?.let {
                Glide
                    .with(this@CatFragment)
                    .asDrawable()
                    .load(it.url)
                    .apply(
                        getDefaultRequestOptions()
                    ).into(detailImage)
            } ?: detailImage.setImageResource(R.drawable.ic_baseline_cloud_download_24)
            saveButton.setOnClickListener {
                listener?.saveImage(detailImage)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(image: Image) =
            CatFragment().apply {
                arguments = bundleOf(
                    ARG_IMAGE to image
                )
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun <T> views(block: CatFragmentBinding.() -> T) = binding.block()
}
