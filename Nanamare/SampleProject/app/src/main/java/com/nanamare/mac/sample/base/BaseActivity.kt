package com.nanamare.mac.sample.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.nanamare.mac.sample.R
import com.nanamare.mac.sample.ui.ProgressDialogFragment

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes val layoutResId: Int
) : AppCompatActivity(), BaseNavigator {

    protected lateinit var binding: B

    private val dialog by lazy { ProgressDialogFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    override fun showLoadingDialog() {
        dialog.show(supportFragmentManager, PROGRESS_DIALOG_FRAGMENT)
    }

    override fun hideLoadingDialog() {
        dialog.dismiss()
    }

    override fun goToFragment(cls: Class<*>, args: Bundle?) {
        try {
            val fragment = cls.newInstance() as Fragment
            fragment.arguments = args
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val KET_MARKET_LIST = "KET_MARKET_LIST"
        const val PROGRESS_DIALOG_FRAGMENT = "PROGRESS_DIALOG_FRAGMENT"
    }

}
