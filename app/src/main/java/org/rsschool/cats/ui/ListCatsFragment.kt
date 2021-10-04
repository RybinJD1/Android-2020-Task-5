package org.rsschool.cats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.rsschool.cats.R
import org.rsschool.cats.databinding.ListCatsFragmentBinding
import org.rsschool.cats.model.Image
import org.rsschool.cats.ui.adapter.ImagesAdapter
import org.rsschool.cats.ui.adapter.ImagesLoadStateAdapter

@AndroidEntryPoint
class ListCatsFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: ListCatsFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ImagesAdapter(ImagesAdapter.OnClickListener { renderCatFragment(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ListCatsFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views {
            itemList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ImagesLoadStateAdapter { adapter.retry() },
                footer = ImagesLoadStateAdapter { adapter.retry() }
            )
            retryButton.setOnClickListener { adapter.retry() }
            adapter.addLoadStateListener { state ->
                itemList.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
                noConnectImage.isVisible = state.refresh is LoadState.Error
                retryButton.isVisible = state.refresh is LoadState.Error
            }
        }

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.imagesFlow.collectLatest { pagingData ->
                run {
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun renderCatFragment(image: Image) {
        parentFragmentManager.commit {
            addToBackStack("catFragment")
            setCustomAnimations(
                R.animator.card_flip_right_in,
                R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,
                R.animator.card_flip_left_out
            )
            replace(R.id.container, CatFragment.newInstance(image))
        }
    }

    private fun <T> views(block: ListCatsFragmentBinding.() -> T) = binding.block()

    companion object {
        @JvmStatic
        fun newInstance() = ListCatsFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
