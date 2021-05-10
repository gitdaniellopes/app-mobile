package daniellopes.io.app_mobile.ui.fragment.detailsarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import daniellopes.io.app_mobile.R
import daniellopes.io.app_mobile.data.entities.Articles
import daniellopes.io.app_mobile.data.remote.response.Resource
import daniellopes.io.app_mobile.databinding.FragmentDetailsArticleBinding
import daniellopes.io.app_mobile.extensions.formatForDateBr
import daniellopes.io.app_mobile.extensions.hide
import daniellopes.io.app_mobile.extensions.show
import daniellopes.io.app_mobile.extensions.toast
import daniellopes.io.app_mobile.ui.fragment.base.BaseFragment

@AndroidEntryPoint
class DetailsArticleFragment :
    BaseFragment<FragmentDetailsArticleBinding, DetailsArticleViewModel>() {
    override val viewModel: DetailsArticleViewModel by viewModels()

    private val args: DetailsArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        findArticleById(id)
        subscribeToObserver()
    }

    private fun findArticleById(id: String) {
        viewModel.findById(id)
    }

    private fun subscribeToObserver() = with(binding) {
        viewModel.articleId.observe(viewLifecycleOwner, {
            it?.let { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { article ->
                            progressBarDetails.hide()
                            onArticleLoaded(article)
                        }
                    }
                    is Resource.Error -> {
                        progressBarDetails.hide()
                        toast(result.message ?: getString(R.string.text_unknow_error))
                    }
                    is Resource.Loading -> {
                        progressBarDetails.show()
                    }
                }
            }
        })
    }

    private fun onArticleLoaded(article: Articles) = with(binding) {
        Glide.with(requireContext()).load(article.imageUrl).into(imageArticleDetails)
        tvTitleArticleDetails.text = article.title
        tvNewsSite.text = article.newsSite
        tvPublishedAtDetails.text = article.publishedAt?.let { formatForDateBr(it) }
        tvSummary.text = article.summary
        tvUpdateAtDetails.text = article.updatedAt?.let { formatForDateBr(it) }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsArticleBinding.inflate(inflater, container, false)
}