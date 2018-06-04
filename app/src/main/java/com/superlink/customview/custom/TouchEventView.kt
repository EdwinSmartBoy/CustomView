package com.superlink.customview.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * 创 建 者   edwin
 * 创建时间   2018/6/4 0004
 * 描    述   事件分发机制
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class TouchEventView : View {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 事件分发流程
     *
     * Activity －> PhoneWindow －> DecorView －> ViewGroup －> ... －> View  (责任链模式)
     *
     * 1.如果事件被消费，就意味着事件信息传递终止。
     * 2.如果事件一直没有被消费，最后会传给Activity，如果Activity也不需要就被抛弃。
     * 3.判断事件是否被消费是根据返回值，而不是根据你是否使用了事件。
     *
     * 责任链模式：
     *
     * 当有多个对象均可以处理同一请求的时候，将这些对象串联成一条链，并沿着这条链传递改请求，直到有对象处理它为止。
     *
     *
     * 事件分发机制详解
     *
     * 常见事件
     *
     * 事件	           简介
     * ACTION_DOWN	   手指 初次接触到屏幕 时触发。
     * ACTION_MOVE	   手指 在屏幕上滑动 时触发，会会多次触发。
     * ACTION_UP	   手指 离开屏幕 时触发。
     * ACTION_CANCEL   事件 被上层拦截 时触发。
     *
     *
     * View 相关
     *
     * View事件的调度顺序应该是 onTouchListener > onTouchEvent > onLongClickListener > onClickListener。
     *
     * 1. 不论 View 自身是否注册点击事件，只要 View 是可点击的就会消费事件。
     * 2. 事件是否被消费由返回值决定，true 表示消费，false 表示不消费，与是否使用了事件无关。
     *
     *
     * ViewGroup 相关
     *
     * 1.判断自身是否需要(询问 onInterceptTouchEvent 是否拦截)，如果需要，调用自己的 onTouchEvent。
     * 2.自身不需要或者不确定，则询问 ChildView ，一般来说是调用手指触摸位置的 ChildView。
     * 3.如果子 ChildView 不需要则调用自身的 onTouchEvent。
     *
     * 给 View 注册 OnTouchListener 不会影响 View 的可点击状态。即使给 View 注册 OnTouchListener ，只要不返回 true 就不会消费事件。
     *
     * 事件优先给 ChildView，会被 ChildView消费掉，ViewGroup 不会响应。
     */


    /**
     * 核心要点
     *
     * 1.事件分发原理: 责任链模式，事件层层传递，直到被消费。
     *
     * 2.View 的 dispatchTouchEvent 主要用于调度自身的监听器和 onTouchEvent。
     *
     * 3.View的事件的调度顺序是 onTouchListener > onTouchEvent > onLongClickListener > onClickListener 。
     *
     * 4.不论 View 自身是否注册点击事件，只要 View 是可点击的就会消费事件。
     *
     * 5.事件是否被消费由返回值决定，true 表示消费，false 表示不消费，与是否使用了事件无关。
     *
     * 6.ViewGroup 中可能有多个 ChildView 时，将事件分配给包含点击位置的 ChildView。
     *
     * 7.ViewGroup 和 ChildView 同时注册了事件监听器(onClick等)，由 ChildView 消费。
     *
     * 8.一次触摸流程中产生事件应被同一 View 消费，全部接收或者全部拒绝。
     *
     * 9.只要接受 ACTION_DOWN 就意味着接受所有的事件，拒绝 ACTION_DOWN 则不会收到后续内容。
     *
     * 10.如果当前正在处理的事件被上层 View 拦截，会收到一个 ACTION_CANCEL，后续事件不会再传递过来。
     */
}