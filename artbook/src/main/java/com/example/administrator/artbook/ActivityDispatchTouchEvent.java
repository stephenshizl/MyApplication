//package com.example.administrator.artbook;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewConfiguration;
//
//import java.util.ArrayList;
//
///**
// * Created by Administrator on 2016/4/3.
// */
//public class ActivityDispatchTouchEvent {
//
//
//    // Actvity # attach(...)方法
//    final void attach(...) {
//        ...
//        mWindow = new PhoneWindow(this);
//        ...
//    }
//
//    // PhoneWindow#superDispatchTouchEvent(..)
//    // 在 PhoneWindow 中 mDecor 为 DecorView, DecorView 继承 FrameLayout
//    public boolean superDispatchTouchEvent(MotionEvent event) {
//        // 将点击事件传递给 DecorView
//        return mDecor.superDispatchTouchEvent(event);
//    }
//
//    // Activity#onTouchEvent(...)
//    public boolean onTouchEvent(MotionEvent event) {
//        if (mWindow.shouldCloseOnTouch(this, event)) {
//            finish();
//            return true;
//        }
//        return false;
//    }
//
//    // Window#shouldCloseOnTouch(...)
//    public boolean shouldCloseOnTouch(Context context, MotionEvent event) {
//        if (mCloseOnTouchOutside && event.getAction() == MotionEvent.ACTION_DOWN
//                && isOutOfBounds(context, event) && peekDecorView() != null) {
//            return true;
//        }
//        return false;
//    }
//
//
//    // VieGroup#dispatchTouchEvent(...)
//    // 1. action 为 ACTION_DOWN 或者 mFirstTouchTarget 不为空是，执行 if 里面的内容，否则执行
//    // else 里面的内容，intercept = true
//    // 2. mFirstTouchTarget 是接收点击事件的 View 组成的单链表
//    // Check for interception.
//    final boolean intercepted;
//    if(actionMasked==MotionEvent.ACTION_DOWN||mFirstTouchTarget!=null){
//        // FLAG_DISALLOW_INTERCEPT 标记位，通过调用 requestDisallowInterceptTouchEvent()  设置，
//        // 1.设置之后，FLAG_DISALLOW_INTERCEPT 为 true, 禁止其父类对点击事件进行拦截， 可参考 ViewPager
//        // 2.设置之后，ViewGroup 将无法拦截 ACTION_DOWN 以外的点击事件。
//        final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
//        if (!disallowIntercept) {
//            // 如果 FLAG_DISALLOW_INTERCEPT 为 false, 调用 onInterceptTouchEvent(ev) 方法，并返回是否拦截；
//            // onInterceptTouchEvent(...) 方法为 false，如果想拦截，则重写该方法，让它返回 true;
//            intercepted = onInterceptTouchEvent(ev);
//            ev.setAction(action); // restore action in case it was changed
//        } else {
//            intercepted = false;
//        }
//    }else{
//        // There are no touch targets and this action is not an initial down
//        // so this view group continues to intercept touches.
//        intercepted = true;
//    }
//
//
//    // ViewGroup#dispatchTouchEvent(...)
//    // 没有被取消并且非拦截的状态下
//    if(!canceled&&!intercepted){
//
//        。。。
//
//        // 遍历所有的子 View，并对点击事件进行分发
//        final View[] children = mChildren;
//        for (int i = childrenCount - 1; i >= 0; i--) {
//            final int childIndex = customOrder ? getChildDrawingOrder(childrenCount, i) : i;
//            final View child = (preorderedList == null) ? children[childIndex] : preorderedList.get(childIndex);
//
//            。。。
//
//            // 1.canViewReceivePointerEvents(...) 判断子 View 能否接收到点击事件
//            // 判断的条件是 child 可见 或者 不可见但出于动画状态
//            // 2. isTransformedTouchPointInView(...) 判断点击坐标(x,y) 是否在 child 可视范围之内
//            // 如果当前的 child 能接收点击事件并且点击的坐标在 child 的可视范围只能，点击事件交给当前的 child 处理，
//            // 否则执行 continue。
//            if (!canViewReceivePointerEvents(child) || !isTransformedTouchPointInView(x, y, child, null)) {
//                continue;
//            }
//
//            newTouchTarget = getTouchTarget(child);
//            if (newTouchTarget != null) {
//                // Child is already receiving touch within its bounds.
//                // Give it the new pointer in addition to the ones it is handling.
//                newTouchTarget.pointerIdBits |= idBitsToAssign;
//                break;
//            }
//
//            resetCancelNextUpFlag(child);
//            // 调用 dispatchTransformedTouchEvent(...) 将点击事件分发给子 View,
//            if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign)) {
//                // Child wants to receive touch within its bounds.
//                mLastTouchDownTime = ev.getDownTime();
//                if (preorderedList != null) {
//                    // childIndex points into presorted list, find original index
//                    for (int j = 0; j < childrenCount; j++) {
//                        if (children[childIndex] == mChildren[j]) {
//                            mLastTouchDownIndex = j;
//                            break;
//                        }
//                    }
//                } else {
//                    mLastTouchDownIndex = childIndex;
//                }
//                mLastTouchDownX = ev.getX();
//                mLastTouchDownY = ev.getY();
//                // 如果 child 接受点击事件，即 child 对点击事件进行了消耗或者拦截
//                // 调用 addTouchTarget(...) 将 child 添加到 mFirstTouchTarget 链表的表头，并返回链表 TouchTarget
//                // 将 alreadyDispatchedToNewTouchTarget 设置为true
//                newTouchTarget = addTouchTarget(child, idBitsToAssign);
//                alreadyDispatchedToNewTouchTarget = true;
//                break;
//            }
//
//            // The accessibility focus didn't handle the event, so clear
//            // the flag and do a normal dispatch to all children.
//            ev.setTargetAccessibilityFocus(false);
//        }
//        if (preorderedList != null) preorderedList.clear();
//    }
//
//
//    // Dispatch to touch targets.
//    // 进一步分发点击事件
//    // (01) 如果mFirstTouchTarget为null，意味着还没有任何View来接受该触摸事件；
//    // 此时，将当前ViewGroup看作一个View；
//    // 将会调用"当前的ViewGroup的父类View的dispatchTouchEvent()"对触摸事件进行分发处理。
//    // 即，会将触摸事件交给当前ViewGroup的onTouch(), onTouchEvent()进行处理。
//    // (02) 如果mFirstTouchTarget不为null，意味着有ViewGroup的子View或子ViewGroup中，
//    // 有可以接受触摸事件的。那么，就将触摸事件分发给这些可以接受触摸事件的子View或子ViewGroup。
//    if(mFirstTouchTarget==null){
//        // No touch targets so treat this as an ordinary view.
//        // 第三年参数为空，则意味中交给当前
//        handled = dispatchTransformedTouchEvent(ev, canceled, null, TouchTarget.ALL_POINTER_IDS);
//    }else{
//        // Dispatch to touch targets, excluding the new touch target if we already
//        // dispatched to it.  Cancel touch targets if necessary.
//        TouchTarget predecessor = null;
//        TouchTarget target = mFirstTouchTarget;
//        while (target != null) {
//            final TouchTarget next = target.next;
//            if (alreadyDispatchedToNewTouchTarget && target == newTouchTarget) {
//                handled = true;
//            } else {
//                final boolean cancelChild = resetCancelNextUpFlag(target.child)
//                        || intercepted;
//                if (dispatchTransformedTouchEvent(ev, cancelChild,
//                        target.child, target.pointerIdBits)) {
//                    handled = true;
//                }
//                if (cancelChild) {
//                    if (predecessor == null) {
//                        mFirstTouchTarget = next;
//                    } else {
//                        predecessor.next = next;
//                    }
//                    target.recycle();
//                    target = next;
//                    continue;
//                }
//            }
//            predecessor = target;
//            target = next;
//        }
//    }
//
//
//
//    // View#dispatchTouchEvent(...)
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        ...
//        boolean result = false;
//        ...
//
//        if (onFilterTouchEventForSecurity(event)) {
//            //noinspection SimplifiableIfStatement
//            ListenerInfo li = mListenerInfo;
//            // 1、是否设置了 onTouchListener
//            // 2.  是否设置 ENABLED, 在 xml 中是 android: enabled 或者 在 java 代码中 设置 View#setEnabled
//            // 3.  OnTouchLisetener.onTouch(...) 方法返回
//            //  如果mOnTouchListener.onTouch(...) 方法返回 true, 则不会执行后面的 onTouchEvent(...) 方法，从这里可以看出
//            // OnTouch（...）方法比 onTouchEvent(...)高级。
//            if (li != null && li.mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED
//                    && li.mOnTouchListener.onTouch(this, event)) {
//                result = true;
//            }
//            // 执行 onTouchEvent(...) 方法
//            if (!result && onTouchEvent(event)) {
//                result = true;
//            }
//        }
//        ...
//
//        return result;
//    }
//
//
//
//    // View#onTouchEvent(...)
//    public boolean onTouchEvent(MotionEvent event) {
//        final float x = event.getX();
//        final float y = event.getY();
//        final int viewFlags = mViewFlags;
//        final int action = event.getAction();
//
//        // 即使 View 被禁用了, 也会消耗点击事件
//        if ((viewFlags & ENABLED_MASK) == DISABLED) {
//            if (action == MotionEvent.ACTION_UP && (mPrivateFlags & PFLAG_PRESSED) != 0) {
//                setPressed(false);
//            }
//            // A disabled view that is clickable still consumes the touch
//            // events, it just doesn't respond to them.
//            return (((viewFlags & CLICKABLE) == CLICKABLE
//                    || (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE)
//                    || (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE);
//        }
//
//        // 是否设置了代理
//        if (mTouchDelegate != null) {
//            if (mTouchDelegate.onTouchEvent(event)) {
//                return true;
//            }
//        }
//
//        //  点击状态时
//        if (((viewFlags & CLICKABLE) == CLICKABLE ||
//                (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE) ||
//                (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE) {
//            switch (action) {
//                case MotionEvent.ACTION_UP:
//                    boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
//                    if ((mPrivateFlags & PFLAG_PRESSED) != 0 || prepressed) {
//                        ...
//                        if (!mHasPerformedLongPress && !mIgnoreNextUpEvent) {
//                            // This is a tap, so remove the longpress check
//                            removeLongPressCallback();
//
//                            // Only perform take click actions if we were in the pressed state
//                            if (!focusTaken) {
//                                // Use a Runnable and post this rather than calling
//                                // performClick directly. This lets other visual state
//                                // of the view update before click actions start.
//                                if (mPerformClick == null) {
//                                    mPerformClick = new PerformClick();
//                                }
//                                if (!post(mPerformClick)) {
//                                    // 调用 performClick() 方法，里面会调用 playSoundEffect(...) 方法和
//                                    // OnClickListener.onClick(...) 方法
//                                    performClick();
//                                }
//                            }
//                        }
//                        ....
//                    }
//                    break;
//                    ...
//            }
//            return true;
//        }
//        return false;
//    }
//
//    // View#getDefaultSize
//    public static int getDefaultSize(int size, int measureSpec) {
//        int result = size;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        switch (specMode) {
//            case MeasureSpec.UNSPECIFIED:
//                result = size;
//                break;
//            case MeasureSpec.AT_MOST:
//            case MeasureSpec.EXACTLY:
//                result = specSize;
//                break;
//        }
//        return result;
//    }
//
//    protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSpeceMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthSpeceSize = View.MeasureSpec.getSize(widthMeasureSpec);
//        int heightSepceSize = View.MeasureSpec.getSize(heightMeasureSpec);
//
//        if (widthMeasureSpec == MeasureSpec.AT_MOST
//                && heightMeasureSpec == MeasureSpec.AT_MOST){
//            // 设置一个默认的宽高
//             setMeasuredDimension(mWidth, mHeight);
//        } else if (widthSpecMode == View.MeasureSpec.AT_MOST){
//            setMeasuredDimension(mWidth, heightSepceSize);
//        } else if (heightSpeceMode == View.MeasureSpec.AT_MOST){
//            setMeasuredDimension(widthSpeceSize, mHeight);
//        }
//    }
//
//
//    // View#layout
//    public void layout(int l, int t, int r, int b) {
//        if ((mPrivateFlags3 & PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT) != 0) {
//            onMeasure(mOldWidthMeasureSpec, mOldHeightMeasureSpec);
//            mPrivateFlags3 &= ~PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
//        }
//
//        int oldL = mLeft;
//        int oldT = mTop;
//        int oldB = mBottom;
//        int oldR = mRight;
//
//        // setOpticalFrame / setFrame 设定 View 的四个顶点
//        boolean changed = isLayoutModeOptical(mParent) ? setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);
//
//        if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {
//            // 空方法
//            onLayout(changed, l, t, r, b);
//
//            ...
//        }
//        ...
//    }
//
//
//    // View#draw
//    public void draw(Canvas canvas) {
//        final int privateFlags = mPrivateFlags;
//        final boolean dirtyOpaque = (privateFlags & PFLAG_DIRTY_MASK) == PFLAG_DIRTY_OPAQUE &&
//                (mAttachInfo == null || !mAttachInfo.mIgnoreDirtyState);
//        mPrivateFlags = (privateFlags & ~PFLAG_DIRTY_MASK) | PFLAG_DRAWN;
//
//        /*
//         * Draw traversal performs several drawing steps which must be executed
//         * in the appropriate order:
//         *
//         *      1. Draw the background
//         *      2. If necessary, save the canvas' layers to prepare for fading
//         *      3. Draw view's content
//         *      4. Draw children
//         *      5. If necessary, draw the fading edges and restore layers
//         *      6. Draw decorations (scrollbars for instance)
//         */
//
//        // Step 1, draw the background, if needed
//        int saveCount;
//
//        if (!dirtyOpaque) {
//            // 第一步，绘制背景
//            drawBackground(canvas);
//        }
//
//        // 正常情况下，跳过第二步和第五步
//        // skip step 2 & 5 if possible (common case)
//        final int viewFlags = mViewFlags;
//        boolean horizontalEdges = (viewFlags & FADING_EDGE_HORIZONTAL) != 0;
//        boolean verticalEdges = (viewFlags & FADING_EDGE_VERTICAL) != 0;
//        if (!verticalEdges && !horizontalEdges) {
//
//            // Step 3, draw the content
//            // 第三步， 绘制自身内容
//            if (!dirtyOpaque) onDraw(canvas);
//
//            // Step 4, draw the children
//            // 第四不，绘制子元素
//            dispatchDraw(canvas);
//
//            // Overlay is part of the content and draws beneath Foreground
//            if (mOverlay != null && !mOverlay.isEmpty()) {
//                mOverlay.getOverlayView().dispatchDraw(canvas);
//            }
//
//            // Step 6, draw decorations (foreground, scrollbars)
//            // 第六步， 绘制 foreground, scrollbars
//            onDrawForeground(canvas);
//
//            // we're done...
//            return;
//        }
//    }
//
//
//
//}
//
//
