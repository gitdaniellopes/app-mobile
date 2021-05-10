package daniellopes.io.app_mobile.ui.fragment.listarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import daniellopes.io.app_mobile.R
import daniellopes.io.app_mobile.data.remote.response.Resource
import daniellopes.io.app_mobile.databinding.FragmentListArticleBinding
import daniellopes.io.app_mobile.extensions.hide
import daniellopes.io.app_mobile.extensions.show
import daniellopes.io.app_mobile.extensions.toast
import daniellopes.io.app_mobile.ui.adapter.ArticleAdapter
import daniellopes.io.app_mobile.ui.fragment.base.BaseFragment

@AndroidEntryPoint
class ListArticleFragment : BaseFragment<FragmentListArticleBinding, ListArticleViewModel>() {

    override val viewModel: ListArticleViewModel by viewModels()

    private val adapterArticle by lazy {
        ArticleAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        goToDetailsArticleFragment()
        subscribeToObserver()
    }

    private fun goToDetailsArticleFragment() {
        adapterArticle.setOnClickListener { article ->
            val directions = article.id?.let { articleId ->
                ListArticleFragmentDirections
                    .actionListArticleFragmentToDetailsArticleFragment(articleId)
            }
            if (directions != null) {
                findNavController().navigate(directions)
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvArticles.apply {
            adapter = adapterArticle
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun subscribeToObserver() = with(binding) {
        viewModel.articles.observe(viewLifecycleOwner, {
            it?.let { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { articles ->
                            progressBar.hide()
                            adapterArticle.articles = articles
                        }
                    }
                    is Resource.Error -> {
                        progressBar.hide()
                        toast(result.message ?: getString(R.string.text_unknow_error))
                    }
                    is Resource.Loading -> {
                        progressBar.show()
                    }
                }
            }
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListArticleBinding.inflate(inflater, container, false)
}