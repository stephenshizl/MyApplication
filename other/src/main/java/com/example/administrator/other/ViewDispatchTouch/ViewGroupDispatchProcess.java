/*
package com.example.administrator.other.ViewDispatchTouch;

import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;

*/
/**
 * Created by Administrator on 2016/3/13.
 *//*

public class ViewGroupDispatchProcess {

    */
/**
     *   第三章， View 的事件体系
     *   View 的事件分发机制
     *   ViewGroup 中 public boolean dispatchTouchEvent(MotionEvent ev) 方法中的整个流程分析。
     *//*


    // Handle an initial down.  处理一个原始的 ACTION_DOWN
    if (actionMasked == MotionEvent.ACTION_DOWN) {
        // Throw away all previous state when starting a new touch gesture.
        // The framework may have dropped the up or cancel event for the previous gesture
        // due to an app switch, ANR, or some other state change.
        cancelAndClearTouchTargets(ev);
        resetTouchState();
    }


    // 获取，是否拦截 intercepted
    // Check for interception.
    final boolean intercepted;
    if (actionMasked == MotionEvent.ACTION_DOWN || mFirstTouchTarget != null) { // 不拦截
        // FLAG_DISALLOW_INTERCEPT 标志位，通过 requestDisallowInterceptTouchEvent 方法来设定
        final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
        if (!disallowIntercept) {
            intercepted = onInterceptTouchEvent(ev);   // 返回结果，表示是否拦截当前事件
            ev.setAction(action); // restore action in case it was changed
        } else {
            intercepted = false;
        }
    } else {  // 拦截
        // There are no touch targets and this action is not an initial down
        // so this view group continues to intercept touches.
        intercepted = true;
    }

    // 获取，是否取消 canceled
    // Check for cancelation.
    final boolean canceled = resetCancelNextUpFlag(this)  || actionMasked == MotionEvent.ACTION_CANCEL;


    //  当 ViewGroup 不拦截事件的时候，事件会向下分发交由它的子 View 进行处理。
    if (!canceled && !intercepted) {

        // If the event is targeting accessiiblity focus we give it to the
        // view that has accessibility focus and if it does not handle it
        // we clear the flag and dispatch the event to all children as usual.
        // We are looking up the accessibility focused host to avoid keeping
        // state since these events are very rare.
        View childWithAccessibilityFocus = ev.isTargetAccessibilityFocus()
                ? findChildWithAccessibilityFocus() : null;

        if (actionMasked == MotionEvent.ACTION_DOWN
                || (split && actionMasked == MotionEvent.ACTION_POINTER_DOWN)
                || actionMasked == MotionEvent.ACTION_HOVER_MOVE) {
            final int actionIndex = ev.getActionIndex(); // always 0 for down
            final int idBitsToAssign = split ? 1 << ev.getPointerId(actionIndex)
                    : TouchTarget.ALL_POINTER_IDS;

            // Clean up earlier touch targets for this pointer id in case they
            // have become out of sync.
            removePointersFromTouchTargets(idBitsToAssign);

            final int childrenCount = mChildrenCount;
            if (newTouchTarget == null && childrenCount != 0) {
                final float x = ev.getX(actionIndex);
                final float y = ev.getY(actionIndex);
                // Find a child that can receive the event.
                // Scan children from front to back.
                final ArrayList<View> preorderedList = buildOrderedChildList();
                final boolean customOrder = preorderedList == null
                        && isChildrenDrawingOrderEnabled();
                final View[] children = mChildren;
                for (int i = childrenCount - 1; i >= 0; i--) {
                    final int childIndex = customOrder
                            ? getChildDrawingOrder(childrenCount, i) : i;
                    final View child = (preorderedList == null)
                            ? children[childIndex] : preorderedList.get(childIndex);

                    // If there is a view that has accessibility focus we want it
                    // to get the event first and if not handled we will perform a
                    // normal dispatch. We may do a double iteration but this is
                    // safer given the timeframe.
                    if (childWithAccessibilityFocus != null) {
                        if (childWithAccessibilityFocus != child) {
                            continue;
                        }
                        childWithAccessibilityFocus = null;
                        i = childrenCount - 1;
                    }

                    if (!canViewReceivePointerEvents(child)
                            || !isTransformedTouchPointInView(x, y, child, null)) {
                        ev.setTargetAccessibilityFocus(false);
                        continue;
                    }

                    newTouchTarget = getTouchTarget(child);
                    if (newTouchTarget != null) {
                        // Child is already receiving touch within its bounds.
                        // Give it the new pointer in addition to the ones it is handling.
                        newTouchTarget.pointerIdBits |= idBitsToAssign;
                        break;
                    }

                    resetCancelNextUpFlag(child);
                    if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign)) {
                        // Child wants to receive touch within its bounds.
                        mLastTouchDownTime = ev.getDownTime();
                        if (preorderedList != null) {
                            // childIndex points into presorted list, find original index
                            for (int j = 0; j < childrenCount; j++) {
                                if (children[childIndex] == mChildren[j]) {
                                    mLastTouchDownIndex = j;
                                    break;
                                }
                            }
                        } else {
                            mLastTouchDownIndex = childIndex;
                        }
                        mLastTouchDownX = ev.getX();
                        mLastTouchDownY = ev.getY();
                        newTouchTarget = addTouchTarget(child, idBitsToAssign);
                        alreadyDispatchedToNewTouchTarget = true;
                        break;
                    }

                    // The accessibility focus didn't handle the event, so clear
                    // the flag and do a normal dispatch to all children.
                    ev.setTargetAccessibilityFocus(false);
                }
                if (preorderedList != null) preorderedList.clear();
            }

            if (newTouchTarget == null && mFirstTouchTarget != null) {
                // Did not find a child to receive the event.
                // Assign the pointer to the least recently added target.
                newTouchTarget = mFirstTouchTarget;
                while (newTouchTarget.next != null) {
                    newTouchTarget = newTouchTarget.next;
                }
                newTouchTarget.pointerIdBits |= idBitsToAssign;
            }
        }
    }


    // Dispatch to touch targets.
    if (mFirstTouchTarget == null) {
        // No touch targets so treat this as an ordinary view.
        // 传进去的 View 为空
        handled = dispatchTransformedTouchEvent(ev, canceled, null,
                TouchTarget.ALL_POINTER_IDS);
    }

    private boolean dispatchTransformedTouchEvent(MotionEvent event, boolean cancel,
                                                  View child, int desiredPointerIdBits) {
        final boolean handled;

        // Canceling motions is a special case.  We don't need to perform any transformations
        // or filtering.  The important part is the action, not the contents.
        final int oldAction = event.getAction();
        if (cancel || oldAction == MotionEvent.ACTION_CANCEL) {
            event.setAction(MotionEvent.ACTION_CANCEL);
            if (child == null) {
                // 这里会调用的 View 的 dispatchTouchEvent 方法
                handled = super.dispatchTouchEvent(event);
            } else {
                handled = child.dispatchTouchEvent(event);
            }
            event.setAction(oldAction);
            return handled;
        }

        ....
    }



    */
/****************************************  ViewGroup  end  ************************************************************************************//*


    */
/**
     *   在View 中
     *//*


    public boolean dispatchTouchEvent(MotionEvent event) {
        ...

        if (onFilterTouchEventForSecurity(event)) {
            //noinspection SimplifiableIfStatement
            ListenerInfo li = mListenerInfo;
            // 判断是否设置了 OnTouchListener
            // 如果 OnTouchListener 的 onTouch 方法返回为 True, 则 不会调用下面的 onTouchEvent() 方法
            if (li != null && li.mOnTouchListener != null
                    && (mViewFlags & ENABLED_MASK) == ENABLED
                    && li.mOnTouchListener.onTouch(this, event)) {
                result = true;
            }

            // 调用 onTouchEvent 方法
            if (!result && onTouchEvent(event)) {
                result = true;
            }
        }
       ...

        return result;
    }

    public boolean onTouchEvent(MotionEvent event) {
       ...

        //  当 View 处于不可用的状态下点击事件，也会消耗点击事件
        if ((viewFlags & ENABLED_MASK) == DISABLED) {
            if (action == MotionEvent.ACTION_UP && (mPrivateFlags & PFLAG_PRESSED) != 0) {
                setPressed(false);
            }
            // A disabled view that is clickable still consumes the touch
            // events, it just doesn't respond to them.
            return (((viewFlags & CLICKABLE) == CLICKABLE
                    || (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE)
                    || (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE);
        }

        // 如果 View 设置了代理， 会执行 它的 onTouchEvent 方法
        if (mTouchDelegate != null) {
            if (mTouchDelegate.onTouchEvent(event)) {
                return true;
            }
        }

        // 对点击事件的具体处理
        if (((viewFlags & CLICKABLE) == CLICKABLE ||
                (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE) ||
                (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE) {
            switch (action) {
                case MotionEvent.ACTION_UP:
                    boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
                    if ((mPrivateFlags & PFLAG_PRESSED) != 0 || prepressed) {

                        ...

                        if (!mHasPerformedLongPress && !mIgnoreNextUpEvent) {
                            // This is a tap, so remove the longpress check
                            removeLongPressCallback();

                            // Only perform take click actions if we were in the pressed state
                            if (!focusTaken) {
                                // Use a Runnable and post this rather than calling
                                // performClick directly. This lets other visual state
                                // of the view update before click actions start.
                                if (mPerformClick == null) {
                                    mPerformClick = new PerformClick();
                                }
                                if (!post(mPerformClick)) {
                                    // 调用
                                    performClick();
                                }
                            }
                        }

                    }
                    break;
            }

            ...
        return true;
    }


    public boolean performClick() {
        final boolean result;
        final ListenerInfo li = mListenerInfo;
        if (li != null && li.mOnClickListener != null) {
           // 播放点击的声音效果
            playSoundEffect(SoundEffectConstants.CLICK);
            // 调用  OnClickLister 的 onClick 方法
            li.mOnClickListener.onClick(this);
            result = true;
        } else {
            result = false;
        }

        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
        return result;
    }

}
*/
