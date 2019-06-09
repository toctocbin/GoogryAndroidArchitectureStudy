package ado.sabgil.studyproject.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(
    private val layoutId: Int
) : Fragment() {

    protected lateinit var binding: B
        private set

    protected var progressBar: View? = null

    protected val viewModelContainer = mutableListOf<BaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        for (viewModel in viewModelContainer) {
            viewModel.onDestroy()
        }
        super.onDestroyView()
    }

    protected fun showToastMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    protected fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }

    protected fun bind(block: B.() -> Unit) {
        binding.block()
    }

    protected fun <T : BaseViewModel> addingToContainer(block: () -> T): T {
        return block().apply {
            viewModelContainer.add(this)
        }
    }
}