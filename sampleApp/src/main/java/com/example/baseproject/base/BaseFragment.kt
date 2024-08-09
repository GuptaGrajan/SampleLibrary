package com.example.baseproject.base


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.baseproject.R
import com.example.baseproject.imagebottompicker.ImageBottomPicker
import com.example.baseproject.utils.InternetCheck


abstract class BaseFragment<DB : ViewDataBinding> : Fragment(), View.OnTouchListener,
    ImageBottomPicker.OnImageSelectedListener, ImageBottomPicker.OnErrorListener,
    ImageBottomPicker.OnMultiImageSelectedListener {

    open lateinit var binding: DB

    private var isLoaded = false

    lateinit var baseActivity: BaseActivity


    @LayoutRes
    abstract fun getLayoutRes(): Int

    private fun init(
        inflater: LayoutInflater, container: ViewGroup?
    ) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        init(inflater, container)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.lifecycleOwner = this
        return binding.root


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // baseActivity.checkLanguage()
        if (baseActivity.window != null) {
            baseActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    override fun onResume() {
        super.onResume()
        isLoaded = true

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.baseActivity = context
        }
    }

    fun hideKeyBoard(input: View?) {
        input?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input.windowToken, 0)
        }
    }




    fun isInternetConnected(): Boolean {
        return InternetCheck.isConnectedToInternet(requireContext())
    }

    open fun backPress() {
    }


    fun updateStatusBarColor(
        @ColorRes colorId: Int,
        isStatusBarFontDark: Boolean = true,
        statusBarColor: Int = R.color.transparent
    ) {
        baseActivity.updateStatusBarColor(colorId, isStatusBarFontDark, statusBarColor)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v is AppCompatEditText && v.hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return true
                }
            }
        }
        return false
    }


    fun chooseImagePicker(
        requestCode: Int,
        showRemove: Boolean = false,
        isVideo: Boolean = false,
        minCount: Int = 5
    ) {
        val bottomPicker = if (isVideo) {
            ImageBottomPicker.Builder(baseActivity, requestCode)
                .showVideoMedia()
                .setTitle("Select Video")
                .setEmptySelectionText("No Video")
                .setOnMultiImageSelectedListener(this)
                .setSelectMaxCount(minCount)
                .setOnErrorListener(this).showRemoved(showRemove)
                .create()

        } else {
            ImageBottomPicker.Builder(baseActivity, requestCode)
                .setOnMultiImageSelectedListener(this)
                .setSelectMaxCount(minCount)
                .setOnErrorListener(this)
                .setCrop(false)
                .showRemoved(showRemove)
                .create()
        }


        bottomPicker.show(baseActivity.supportFragmentManager)
    }


    fun chooseImagePicker(requestCode: Int, crop: Boolean = false) {
        val bottomPicker = ImageBottomPicker.Builder(baseActivity, requestCode)
            .setOnImageSelectedListener(this)
            .setOnErrorListener(this)
            .setCrop(crop)
            .showRemoved(true)
            .create()


        bottomPicker.show(baseActivity.supportFragmentManager)
    }


    override fun onImageSelected(uri: Uri?, requestCode: Int) {

    }

    override fun onImageRemoved(isRemoved: Boolean, requestCode: Int) {

    }

    override fun onError(message: String?) {


    }

    override fun onImagesSelected(uriList: ArrayList<Uri>?, requestCode: Int) {


    }


}