package com.linfeng.mvp.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import com.linfeng.mvp.MVPManager
import com.linfeng.mvp.annotation.JMvpContract
import com.linfeng.mvp.presenter.BasePresenter
import com.linfeng.mvp.property.PresenterProperty
import java.lang.reflect.ParameterizedType

/**
 * @author jlanglang  2016/1/5 9:42
 */
abstract class BaseActivity<T : BasePresenter<*>> : AppCompatActivity() {

    var mPresenter by PresenterProperty<T>(this)
    private var mViews: SparseArray<View>? = null
    //    private var mContentView: View? = null
    private var statusBarView: View? = null

    protected val statusBarDrawable: Int
        @DrawableRes
        @ColorRes
        get() = MVPManager.toolbarOptions.getStatusDrawable()

    private lateinit var mContentView: View

    override fun getContentView(): View {
        return mContentView
    }

    val appcompatActivity: BaseActivity<*>
        get() = this

    val context: Context
        get() = this

    val isFinish: Boolean
        get() = isFinishing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //创建presenter
        mPresenter = initPresenter()
        //绑定Activity
//        mPresenter.onAttach(this)
        //初始化ContentView
        mContentView = initView(layoutInflater, savedInstanceState)
//        if (mContentView != null) {
        super.setContentView(mContentView)
//        }
    }

    fun onNewThrowable(throwable: Throwable) {

    }

    @SuppressLint("ResourceType")
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //初始化Activity
        init(savedInstanceState)
        //初始化presenter
        mPresenter.onCreate()
        onPresentersCreate()
        if (statusBarDrawable > 0) {
            initStatusBar()
            window?.decorView?.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                initStatusBar()
            }
        }
        mPresenter.onRefreshData()
    }

    protected fun initStatusBar() {
        if (statusBarView == null) {
            val identifier = resources.getIdentifier("statusBarBackground", "id", "android")
            statusBarView = window?.findViewById(identifier)
        }
        if (statusBarView != null) {
            statusBarView!!.setBackgroundResource(statusBarDrawable)
        }
    }

    /**
     * 扩展除了默认的presenter的其他Presenter初始化
     */
    protected open fun onPresentersCreate() {

    }

    /**
     * 运行在initView之后
     * 已经setContentView
     * 可以做一些初始化操作
     *
     * @return
     */
    protected open fun init(savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        mPresenter.onStart()
        super.onStart()
    }

    override fun onPause() {
        mPresenter.onPause()
        super.onPause()
    }

    override fun onRestart() {
        mPresenter.onRestart()
        super.onRestart()
    }

    override fun onStop() {
        mPresenter.onStop()
        super.onStop()
    }

    override fun onResume() {
        mPresenter.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onDetachedFromWindow() {
        mPresenter.onDetach()
        super.onDetachedFromWindow()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mPresenter.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    /**
     * 跳转fragment
     *
     * @param tofragment
     */
    override fun startFragment(tofragment: Fragment) {
        startFragment(tofragment, null)
    }

    /**
     * @param fragment 跳转的fragment
     * @param tag      fragment的标签
     */
    fun startFragment(fragment: Fragment, tag: String?, isAdd: Boolean = true) {
        startFragment(fragment, tag,
                MVPManager.enterAnim,
                MVPManager.exitAnim,
                MVPManager.enterPopAnim,
                MVPManager.exitPopAnim, isAdd)
    }

    fun startFragment(fragment: Fragment, tag: String, enter: Int, popExit: Int, isAddBack: Boolean = true) {
        startFragment(fragment, tag,
                enter,
                0,
                0,
                popExit, isAddBack)
    }
    /**
     * @param fragment 跳转的fragment
     * @param tag      fragment的标签
     */
    /**
     * @param fragment 跳转的fragment
     * @param tag      fragment的标签
     */
    fun startFragment(fragment: Fragment, tag: String?, enterAnim: Int, exitAnim: Int, popEnter: Int, popExit: Int, isAddBack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnter, popExit)
        fragmentTransaction.add(android.R.id.content, fragment, tag)
        if (isAddBack) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }


    /**
     * @param rootFragment Activity内部fragment
     * @param containerId  fragment父容器
     */
    fun replaceFragment(rootFragment: Fragment, containerId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(containerId, rootFragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    /**
     * 跳转Activity
     */
    fun startActivity(aClass: Class<*>) {
        val intent = Intent(this, aClass)
        startActivity(intent)
    }

    /**
     * 跳转Activity
     */
    fun startActivity(aClass: Class<*>, bundle: Bundle) {
        val intent = Intent(this, aClass)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     * 跳转Activity
     */
    fun startActivity(aClass: Class<*>, bundle: Bundle, flag: Int) {
        val intent = Intent(this, aClass)
        intent.putExtras(bundle)
        intent.addFlags(flag)
        startActivity(intent)
    }

    /**
     * 跳转Activity
     */
    fun startActivity(aClass: Class<*>, flag: Int) {
        val intent = Intent(this, aClass)
        intent.addFlags(flag)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

//    /**
//     * 通过viewId获取控件
//     *
//     * @param viewId 资源id
//     * @return
//     */
//    fun <V : View> findView(@IdRes viewId: Int): V {
//        var view: View? = views.get(viewId)
//        if (view == null) {
//            view = findViewById(viewId)
//            views.put(viewId, view)
//        }
//        return view as V?
//    }

    /**
     * 初始化ContentView
     * 建议不要包含toolbar

     * @param inflater
     * *
     * @param savedInstanceState
     * *
     * @return
     */
    protected open fun initView(inflater: LayoutInflater, savedInstanceState: Bundle?): View {
        val initView = initView(savedInstanceState)
        return inflater.inflate(initView, null)
    }

    /**
     * 初始化ContentView
     * 建议不要包含toolbar
     * @param savedInstanceState
     * @return
     */
    protected open fun initView(savedInstanceState: Bundle?): Int {
        val annotation = this.javaClass.getAnnotation(JMvpContract::class.java)
        if (annotation != null) {
            return annotation.layout
        }
        return 0
    }

    fun finishActivity() {
        finish()
    }

    protected open fun initPresenter(): T {
        // 通过反射机制获取子类传递过来的实体类的类型信息
        val type = this.javaClass.genericSuperclass as ParameterizedType
        return (type.actualTypeArguments[0] as Class<T>).newInstance()
    }


}

