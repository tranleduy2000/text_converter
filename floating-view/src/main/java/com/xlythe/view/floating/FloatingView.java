package com.xlythe.view.floating;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

public abstract class FloatingView extends Service implements OnTouchListener {
    private static final String TAG = "FloatingView";
    private static final boolean DEBUG = false;

    private static final int MARGIN_VIEW = 20;
    private static final int MARGIN_VERTICAL = 5;
    private static final int MARGIN_HORIZONTAL = -10;
    private static final int VIBRATION = 25;
    private static final int DELETE_ANIM_DURATION = 300;

    private static final int NOTIFICATION_ID = 1;
    public static final String ACTION_OPEN = "com.xlythe.view.floating.OPEN";

    private int CLOSE_ANIMATION_DISTANCE;
    private int DRAG_DELTA;
    private int STARTING_POINT_Y;
    private int DELETE_BOX_WIDTH;
    private int DELETE_BOX_HEIGHT;
    private int MAGIC_OFFSET;

    // Drag variables
    private float mPrevDragX;
    private float mPrevDragY;
    private float mOrigX;
    private float mOrigY;
    private boolean mDragged;
    private int mIconSize;
    private final Point mStartingPositionPoint = new Point();
    private final Point mOpenPositionPoint = new Point();

    // View variables
    private ViewGroup mRootView;
    private BroadcastReceiver mBroadcastReceiver;
    private WindowManager mWindowManager;
    private View mDraggableIcon;
    private ViewGroup mInactiveButton;
    private WindowManager.LayoutParams mInactiveParams;
    private View mView;
    private ViewGroup mDeleteView;
    private View mDeleteBoxView;
    private boolean mDeleteBoxVisible = false;
    private boolean mIsDestroyed = false;
    private boolean mIsBeingDestroyed = false;
    private int mCurrentPosX = -1;
    private int mCurrentPosY = -1;

    // Animation variables
    private LimitedQueue<Float> mDeltaXArray;
    private LimitedQueue<Float> mDeltaYArray;
    private AnimationTask mAnimationTask;
    private final Point mIconPositionInDeleteModePoint = new Point();

    // Open/Close variables
    private boolean mIsViewOpen = false;
    private final Point mWiggle = new Point(0, 0);
    private boolean mEnableWiggle = false;

    // Close logic
    private boolean mIsInDeleteMode = false;
    private boolean mIsAnimatingToDeleteMode = false;
    private View mDeleteIcon;
    private View mDeleteIconHolder;
    private boolean mIsAnimationLocked = false;
    private boolean mDontVibrate = false;
    private BroadcastReceiver mHomeKeyReceiver;

    @NonNull
    protected abstract View inflateButton(@NonNull ViewGroup parent);

    @NonNull
    protected abstract View inflateView(@NonNull ViewGroup parent);

    @NonNull
    protected abstract Notification createNotification();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_OPEN.equals(intent.getAction())) {
            open();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(NOTIFICATION_ID, createNotification());

        // Load margins, distances, etc
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        float density = getResources().getDisplayMetrics().density;
        CLOSE_ANIMATION_DISTANCE = (int) (250 * density);
        DRAG_DELTA = vc.getScaledTouchSlop();
        STARTING_POINT_Y = (int) (10 * density);
        DELETE_BOX_WIDTH = (int) getResources().getDimension(R.dimen.floating_window_delete_box_width);
        DELETE_BOX_HEIGHT = (int) getResources().getDimension(R.dimen.floating_window_delete_box_height);
        MAGIC_OFFSET = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        // The window manager lets us add/remove views to the window directly
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Create the master view. This guy will fill the entire screen when the floating view is active
        mRootView = new FrameLayout(getContext()) {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                // We can only detect KEYCODE_BACK (but not KEYCODE_HOME). sad face :(
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    close();
                    return true;
                }
                return super.dispatchKeyEvent(event);
            }
        };
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                Build.VERSION.SDK_INT >= 26 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(mRootView, params);
        mRootView.setVisibility(View.GONE);

        // Next, we create the icon view. This guy is who appears in the corner of the screen when you aren't using the floating view
        mInactiveButton = new FrameLayout(getContext());
        mInactiveButton.addView(inflateButton(mInactiveButton));
        params = mInactiveParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= 26 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowManager.addView(mInactiveButton, params);
        mInactiveButton.setOnTouchListener(this);

        // When you start dragging the inactive button, we move this guy instead. He lives in the root view where we're able
        // to animate smoothly and have greater control over our views.
        mDraggableIcon = inflateButton(mRootView);
        mDraggableIcon.setOnTouchListener(this);
        if (mDraggableIcon.getLayoutParams() == null) {
            mDraggableIcon.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
        }
        mRootView.addView(mDraggableIcon);
        Point startingPosition = getStartingPosition();
        updateIconPosition(startingPosition.x, startingPosition.y);
        adjustInactivePosition();

        // When the phone rotates, we want to make sure our icon stays visible on screen.
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent myIntent) {
                int x = mCurrentPosX <= 0 ? getIconHorizontalMargin() : getScreenWidth() - mIconSize - getIconHorizontalMargin();
                int y = mCurrentPosY;
                if (y <= 0) {
                    y = getIconVerticalMargin();
                }
                if (y >= getScreenHeight() - mDraggableIcon.getHeight()) {
                    y = getScreenHeight() - mDraggableIcon.getHeight() - getIconVerticalMargin();
                }
                updateIconPosition(x, y);
                adjustInactivePosition();
            }
        };
        registerReceiver(mBroadcastReceiver, filter);

        // We'll measure our icon now so that we can use it in various formulas later
        mRootView.measure(MeasureSpec.makeMeasureSpec(getScreenWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getScreenHeight(), MeasureSpec.EXACTLY));
        mIconSize = mDraggableIcon.getMeasuredWidth();

        // While debugging, it's helpful to see the View bounds
        if (DEBUG) {
            mDraggableIcon.setBackgroundColor(0x30ff0000);
            mInactiveButton.getChildAt(0).setBackgroundColor(0x30ff0000);
        }
    }

    @Override
    public void onDestroy() {
        mIsDestroyed = true;
        if (mRootView != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mRootView);
            mRootView = null;
        }
        if (mInactiveButton != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mInactiveButton);
            mInactiveButton = null;
        }
        if (mAnimationTask != null) {
            mAnimationTask.cancel();
            mAnimationTask = null;
        }
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // I was seeing this crash in the logs. It shouldn't be possible though,
        // because mRootView is created in onCreate and only destroyed in onDestroy
        if (mRootView == null) {
            return false;
        }

        mRootView.setVisibility(View.VISIBLE);
        mInactiveButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInactiveButton != null) mInactiveButton.setVisibility(View.INVISIBLE);
            }
        }, 30);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevDragX = mOrigX = event.getRawX();
                mPrevDragY = mOrigY = event.getRawY();

                mDragged = false;

                mDeltaXArray = new LimitedQueue<>(5);
                mDeltaYArray = new LimitedQueue<>(5);

                mDraggableIcon.setScaleX(0.92f);
                mDraggableIcon.setScaleY(0.92f);

                // Cancel any currently running animations
                if (mAnimationTask != null) {
                    mAnimationTask.cancel();
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsAnimationLocked = false;
                if (mAnimationTask != null) {
                    mAnimationTask.cancel();
                }

                if (mDragged) {
                    // Animate the icon to one of the sides of the phone
                    mAnimationTask = new AnimationTask();
                    mAnimationTask.run();
                } else if (mIsViewOpen) {
                    close();
                } else {
                    open();
                }

                if (mIsInDeleteMode) {
                    stop(true);
                } else {
                    hideDeleteBox(false);
                    mDraggableIcon.setScaleX(1f);
                    mDraggableIcon.setScaleY(1f);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) (event.getRawX() - mDraggableIcon.getWidth() / 2);
                int y = (int) (event.getRawY() - mDraggableIcon.getHeight());
                if (mDeleteIconHolder != null) {
                    calculateWiggle(x, y);
                    if (mEnableWiggle) {
                        mDeleteIconHolder.setTranslationX(mWiggle.x);
                        mDeleteIconHolder.setTranslationY(mWiggle.y);
                    }
                    if (mIsInDeleteMode && isDeleteMode(x, y) && !mIsAnimatingToDeleteMode) {
                        Point point = calculatorIconPositionInDeleteMode();
                        mDraggableIcon.setTranslationX(point.x);
                        mDraggableIcon.setTranslationY(point.y);
                    }
                }
                if (isDeleteMode(x, y)) {
                    if (!mIsInDeleteMode) animateToDeleteBoxCenter();
                } else if (isDeleteMode() && !mIsAnimationLocked) {
                    mIsInDeleteMode = false;
                    if (mAnimationTask != null) {
                        mAnimationTask.cancel();
                    }
                    mAnimationTask = new AnimationTask(x, y);
                    mAnimationTask.setDuration(50);
                    mAnimationTask.setInterpolator(new LinearInterpolator());
                    mAnimationTask.setAnimationFinishedListener(new AnimationFinishedListener() {
                        @Override
                        public void onAnimationFinished() {
                            mIsAnimationLocked = false;
                        }
                    });
                    mAnimationTask.run();
                    mIsAnimationLocked = true;
                    if (mDeleteIcon != null) {
                        mDeleteIcon.animate().scaleX(1f).scaleY(1f).setDuration(100);
                    }
                } else {
                    if (mIsInDeleteMode) {
                        if (mDeleteIcon != null) {
                            mDeleteIcon.animate().scaleX(1f).scaleY(1f).setDuration(100);
                        }
                        mIsInDeleteMode = false;
                    }
                    if (!mIsAnimationLocked && mDragged) {
                        if (mAnimationTask != null) {
                            mAnimationTask.cancel();
                        }
                        updateIconPosition(x, y);
                        mDontVibrate = false;
                    }
                }

                float deltaX = event.getRawX() - mPrevDragX;
                float deltaY = event.getRawY() - mPrevDragY;

                mDeltaXArray.add(deltaX);
                mDeltaYArray.add(deltaY);

                mPrevDragX = event.getRawX();
                mPrevDragY = event.getRawY();

                deltaX = event.getRawX() - mOrigX;
                deltaY = event.getRawY() - mOrigY;
                mDragged = mDragged || Math.abs(deltaX) > DRAG_DELTA || Math.abs(deltaY) > DRAG_DELTA;
                if (mDragged) {
                    close(false);
                    showDeleteBox();
                }
                break;
        }
        return true;
    }

    private void updateIconPosition(int x, int y) {
        mCurrentPosX = x;
        mCurrentPosY = y;
        if (!mIsDestroyed) {
            mDraggableIcon.setTranslationX(mCurrentPosX);
            mDraggableIcon.setTranslationY(mCurrentPosY);
        }
    }

    private boolean isDeleteMode() {
        return isDeleteMode(mCurrentPosX, mCurrentPosY);
    }

    private boolean isDeleteMode(int x, int y) {
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        int boxWidth = DELETE_BOX_WIDTH;
        int boxHeight = DELETE_BOX_HEIGHT;
        boolean horz = x + (mDraggableIcon == null ? 0 : mDraggableIcon.getWidth()) > (screenWidth / 2 - boxWidth / 2) && x < (screenWidth / 2 + boxWidth / 2);
        boolean vert = y + (mDraggableIcon == null ? 0 : mDraggableIcon.getHeight()) > (screenHeight - boxHeight);

        return mEnableWiggle && horz && vert;
    }

    private void showDeleteBox() {
        if (!mDeleteBoxVisible) {
            mDeleteBoxVisible = true;
            if (mDeleteView == null) {
                mDeleteView = new FrameLayout(getContext());
                View.inflate(getContext(), R.layout.floating_delete_box, mDeleteView);
                mDeleteIcon = mDeleteView.findViewById(R.id.delete_icon);
                mDeleteIconHolder = mDeleteView.findViewById(R.id.delete_icon_holder);
                mDeleteBoxView = mDeleteView.findViewById(R.id.box);

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.BOTTOM;
                mRootView.addView(mDeleteView, params);
            } else {
                mDeleteView.setVisibility(View.VISIBLE);
            }
            mEnableWiggle = false;
            mDeleteBoxView.setAlpha(0);
            mDeleteBoxView.animate().alpha(1);
            mDeleteIconHolder.setTranslationX(0);
            mDeleteIconHolder.setTranslationY(CLOSE_ANIMATION_DISTANCE);

            ValueAnimator animator = ValueAnimator.ofInt(0, 100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float percent = valueAnimator.getAnimatedFraction();
                    mDeleteIconHolder.setTranslationX(mWiggle.x * percent);

                    int destinationY = mWiggle.y;
                    float deltaY = destinationY - CLOSE_ANIMATION_DISTANCE;
                    mDeleteIconHolder.setTranslationY(CLOSE_ANIMATION_DISTANCE + (deltaY * percent));
                }
            });
            animator.addListener(new AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    mEnableWiggle = true;

                    if (isDeleteMode()) {
                        if (!mIsInDeleteMode) animateToDeleteBoxCenter();
                    }
                }
            });
            animator.start();
        }
    }

    private void hideDeleteBox(boolean shrink) {
        if (mDeleteBoxVisible) {
            mDeleteBoxVisible = false;
            if (mDeleteView != null) {
                mDeleteBoxView.animate()
                        .alpha(0)
                        .setDuration(DELETE_ANIM_DURATION);
                mDeleteIconHolder.animate()
                        .scaleX(shrink ? 0.3f : 1)
                        .scaleY(shrink ? 0.3f : 1)
                        .translationYBy(CLOSE_ANIMATION_DISTANCE)
                        .setDuration(DELETE_ANIM_DURATION)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimationFinishedListener() {
                            @Override
                            public void onAnimationFinished() {
                                if (mDeleteView != null) {
                                    mDeleteView.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        }
    }

    private void adjustInactivePosition() {
        if (mRootView == null) return;
        if (DEBUG) {
            Log.d(TAG, String.format("adjustInactivePosition() mCurrentPosX=%s, mCurrentPosY=%s screenWidth=%s, screenHeight=%s",
                    mCurrentPosX, mCurrentPosY, getScreenWidth(), getScreenHeight()));
        }

        mInactiveButton.setVisibility(View.VISIBLE);
        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRootView != null && !mIsViewOpen) {
                    mRootView.setVisibility(View.GONE);
                }
            }
        }, 30);

        // Our icon is made up of two parts. The outer shell, which uses mInactiveParams to position
        // itself within the phone screen's dimensions, and the inner shell, which uses translation
        // to slightly adjust the icon when it's supposed to appear slightly cropped
        int x = mCurrentPosX;
        int y = mCurrentPosY;
        View innerShell = mInactiveButton.getChildAt(0);
        innerShell.setTranslationX(0);
        innerShell.setTranslationY(0);

        if (x < 0) {
            innerShell.setTranslationX(x);
            x = 0;
        } else if (x > getScreenWidth() - mIconSize) {
            innerShell.setTranslationX(x - getScreenWidth() + mIconSize);
            x = getScreenWidth() - mIconSize;
        }
        if (DEBUG) {
            Log.d(TAG, "getTranslationX: " + innerShell.getTranslationX());
        }

        if (y < 0) {
            innerShell.setTranslationY(y);
            y = 0;
        } else if (y > getScreenHeight() - mIconSize) {
            innerShell.setTranslationY(y - getScreenHeight() + mIconSize);
            y = getScreenHeight() - mIconSize;
        }
        if (DEBUG) {
            Log.d(TAG, "getTranslationY: " + innerShell.getTranslationY());
        }

        mInactiveParams.x = x;
        mInactiveParams.y = y;
        if (!mIsDestroyed) mWindowManager.updateViewLayout(mInactiveButton, mInactiveParams);
    }

    private void vibrate() {
        if (mDontVibrate) return;
        Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (!vi.hasVibrator()) return;
        vi.vibrate(VIBRATION);
    }

    private void stop(boolean animate) {
        if (mIsBeingDestroyed) return;
        mIsBeingDestroyed = true;
        mDontVibrate = true;

        if (animate) {
            animateToDeleteBoxCenter(new AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    mDeleteIconHolder.setTranslationX(mWiggle.x);
                    mDeleteIconHolder.setTranslationY(mWiggle.y);
                    hideDeleteBox(true);
                    mDraggableIcon.animate()
                            .scaleX(0.3f)
                            .scaleY(0.3f)
                            .translationYBy(CLOSE_ANIMATION_DISTANCE)
                            .setDuration(DELETE_ANIM_DURATION)
                            .setInterpolator(new AccelerateInterpolator())
                            .setListener(new AnimationFinishedListener() {
                                @Override
                                public void onAnimationFinished() {
                                    stopSelf();
                                }
                            });
                }
            });
        } else {
            stopSelf();
        }
    }

    private Point calculatorIconPositionInDeleteMode() {
        mIconPositionInDeleteModePoint.x = mWiggle.x + getScreenWidth() / 2 - mDraggableIcon.getWidth() / 2;
        mIconPositionInDeleteModePoint.y = mWiggle.y + mRootView.getHeight() - DELETE_BOX_HEIGHT / 2 - mDraggableIcon.getHeight() / 2 + MAGIC_OFFSET;
        return mIconPositionInDeleteModePoint;
    }

    private void animateToDeleteBoxCenter() {
        animateToDeleteBoxCenter(null);
    }

    private void animateToDeleteBoxCenter(@Nullable final AnimationFinishedListener l) {
        if (mIsAnimationLocked || mRootView == null || mDraggableIcon == null)
            return;
        mIsInDeleteMode = true;
        mIsAnimatingToDeleteMode = true;
        if (mAnimationTask != null) mAnimationTask.cancel();
        final float initialX = mDraggableIcon.getTranslationX();
        final float initialY = mDraggableIcon.getTranslationY();
        mAnimationTask = new AnimationTask(new DynamicUpdate() {
            @Override
            public float getTranslationX(float percent) {
                int destinationX = calculatorIconPositionInDeleteMode().x;
                float delta = destinationX - initialX;
                return initialX + (delta * percent);
            }

            @Override
            public float getTranslationY(float percent) {
                int destinationY = calculatorIconPositionInDeleteMode().y;
                float delta = destinationY - initialY;
                return initialY + (delta * percent);
            }
        });
        mAnimationTask.setDuration(150);
        mAnimationTask.setAnimationFinishedListener(new AnimationFinishedListener() {
            @Override
            public void onAnimationFinished() {
                mIsAnimatingToDeleteMode = false;
                if (l != null) {
                    l.onAnimationFinished();
                }
            }
        });
        mAnimationTask.run();
        vibrate();
        mDeleteIcon.animate().scaleX(1.4f).scaleY(1.4f).setDuration(100);
    }

    private void calculateWiggle(int x, int y) {
        int closeIconX = getScreenWidth() / 2;
        int closeIconY = mRootView.getHeight() - DELETE_BOX_HEIGHT / 2;
        mWiggle.x = (x - closeIconX) / 10;
        mWiggle.y = Math.max(-1 * DELETE_BOX_HEIGHT / 8, (y - closeIconY) / 10);
    }

    public void open() {
        if (mRootView.getVisibility() == View.GONE) {
            mRootView.setVisibility(View.VISIBLE);
            mInactiveButton.setVisibility(View.INVISIBLE);
        }
        if (!mIsViewOpen) {
            if (mIsAnimationLocked) return;
            mIsViewOpen = true;
            Point openPosition = getOpenPosition();
            mAnimationTask = new AnimationTask(openPosition.x, openPosition.y);
            mAnimationTask.setAnimationFinishedListener(new AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    show();
                }
            });
            mAnimationTask.run();
            mRootView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    close();
                    return true;
                }
            });

            // Home and Recent Apps send ACTION_CLOSE_SYSTEM_DIALOGS so we can use that to hide our self.
            mHomeKeyReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    close();
                }
            };
            getContext().registerReceiver(mHomeKeyReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }

    /**
     * Returns the x,y coordinate of the top right corner of the icon when first launched
     */
    protected Point getStartingPosition() {
        mStartingPositionPoint.x = getIconHorizontalMargin();
        mStartingPositionPoint.y = STARTING_POINT_Y;
        return mStartingPositionPoint;
    }

    /**
     * Returns the x,y coordinate of the top right corner of the icon when opened
     */
    protected Point getOpenPosition() {
        mOpenPositionPoint.x = getScreenWidth() - mIconSize - getMargin();
        mOpenPositionPoint.y = STARTING_POINT_Y;
        return mOpenPositionPoint;
    }

    /**
     * Returns the margin around the floating view when open
     */
    protected int getMargin() {
        return (int) (MARGIN_VIEW * getDensity());
    }

    /**
     * Returns the margin of the icon when closed. Typically, this number is negative so that
     * the icon appears slightly off screen.
     */
    protected int getIconHorizontalMargin() {
        return (int) (MARGIN_HORIZONTAL * getDensity());
    }

    /**
     * Returns the margin of the icon when closed. This is typically a small margin so that when
     * the icon is flung to the top/bottom of the screen, it doesn't clip or appear off screen.
     */
    protected int getIconVerticalMargin() {
        return (int) (MARGIN_VERTICAL * getDensity());
    }

    private float getDensity() {
        return getResources().getDisplayMetrics().density;
    }

    public void close() {
        close(true);
    }

    public void close(boolean returnToOrigin) {
        close(returnToOrigin, false);
    }

    public void close(boolean returnToOrigin, boolean destroyView) {
        mRootView.setOnTouchListener(null);
        if (mIsViewOpen) {
            mIsViewOpen = false;
            if (returnToOrigin) {
                if (mIsAnimationLocked) return;
                mAnimationTask = new AnimationTask(mCurrentPosX, mCurrentPosY);
                mAnimationTask.setAnimationFinishedListener(new AnimationFinishedListener() {
                    @Override
                    public void onAnimationFinished() {
                        adjustInactivePosition();
                    }
                });
                mAnimationTask.run();
            }
            hide(destroyView);
            if (mHomeKeyReceiver != null) {
                getContext().unregisterReceiver(mHomeKeyReceiver);
                mHomeKeyReceiver = null;
            }
        }
    }

    @SuppressLint("RtlHardcoded")
    private void show() {
        Log.v(TAG, "show()");
        if (mView == null) {
            mView = inflateView(mRootView);
            mView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mView.getLayoutParams();
            params.leftMargin = getMargin();
            params.rightMargin = getMargin();
            params.gravity = getOpenPosition().x < getScreenWidth() / 2 ? Gravity.LEFT : Gravity.RIGHT;
            mRootView.addView(mView);
            mView.measure(MeasureSpec.makeMeasureSpec(mRootView.getWidth() - getMargin(), MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(mRootView.getHeight() - getMargin() - getOpenPosition().y, MeasureSpec.AT_MOST));
        } else {
            mView.setVisibility(View.VISIBLE);
        }
        // Adjust view location
        if (!mIsDestroyed) {
            mView.setTranslationY(getOpenPosition().y + mDraggableIcon.getHeight());
        }

        // Animate calc in
        mView.setAlpha(0);
        mView.animate().setDuration(150).alpha(1).setListener(null);

        onShow();
    }

    public void onShow() {
    }

    public void onHide() {
    }

    private void hide(final boolean destroyView) {
        Log.v(TAG, "hide()");
        if (mView != null) {
            mView.setAlpha(1);
            mView.animate().setDuration(150).alpha(0).setListener(new AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    mView.setVisibility(View.GONE);
                    if (destroyView) {
                        Log.d(TAG, "View destroyed");
                        mRootView.removeView(mView);
                        mView = null;
                    }
                }
            });

            onHide();
        }
    }

    private float calculateVelocityX() {
        int depreciation = mDeltaXArray.size() + 1;
        float sum = 0;
        for (Float f : mDeltaXArray) {
            depreciation--;
            if (depreciation > 5) continue;
            sum += f / depreciation;
        }
        return sum;
    }

    private float calculateVelocityY() {
        int depreciation = mDeltaYArray.size() + 1;
        float sum = 0;
        for (Float f : mDeltaYArray) {
            depreciation--;
            if (depreciation > 5) continue;
            sum += f / depreciation;
        }
        return sum;
    }

    protected Context getContext() {
        return this;
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels - getStatusBarHeight();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        close(true /* returnToOrigin */, true /* destroyView */);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private float sqr(float f) {
        return f * f;
    }

    // Timer for animation/automatic movement of the tray.
    private class AnimationTask {
        // Ultimate destination coordinates toward which the view will move
        private final int mDestX;
        private final int mDestY;
        @Nullable
        private final DynamicUpdate mDynamicUpdate;
        private long mDuration = 450;
        private float mTension = 1.4f;
        private Interpolator mInterpolator = new OvershootInterpolator(mTension);
        private AnimationFinishedListener mAnimationFinishedListener;

        AnimationTask(int x, int y) {
            if (mIsAnimationLocked)
                throw new RuntimeException("Returning to user's finger. Avoid animations while mIsAnimationLocked flag is set.");
            mDestX = x;
            mDestY = y;
            mDynamicUpdate = null;
        }

        AnimationTask(@NonNull DynamicUpdate dynamicUpdate) {
            if (mIsAnimationLocked)
                throw new RuntimeException("Returning to user's finger. Avoid animations while mIsAnimationLocked flag is set.");
            mDestX = -1;
            mDestY = -1;
            mDynamicUpdate = dynamicUpdate;
        }

        AnimationTask() {
            if (mIsAnimationLocked)
                throw new RuntimeException("Returning to user's finger. Avoid animations while mIsAnimationLocked flag is set.");
            mDestX = calculateX();
            mDestY = calculateY();
            mDynamicUpdate = null;

            setAnimationFinishedListener(new AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    adjustInactivePosition();
                }
            });

            float velocityX = calculateVelocityX();
            float velocityY = calculateVelocityY();
            mTension += Math.sqrt(sqr(velocityX) + sqr(velocityY)) / 200;
            mInterpolator = new OvershootInterpolator(mTension);

            mCurrentPosX = mDestX;
            mCurrentPosY = mDestY;
        }

        public void setDuration(long duration) {
            mDuration = duration;
        }

        void setAnimationFinishedListener(AnimationFinishedListener l) {
            mAnimationFinishedListener = l;
        }

        public void setInterpolator(Interpolator interpolator) {
            mInterpolator = interpolator;
        }

        private int calculateX() {
            float velocityX = calculateVelocityX();
            int screenWidth = getScreenWidth();
            int leftSide = getIconHorizontalMargin();
            int rightSide = screenWidth - mDraggableIcon.getWidth() - getIconHorizontalMargin();
            int destX = (mCurrentPosX + mIconSize / 2 > screenWidth / 2) ? rightSide : leftSide;
            if (Math.abs(velocityX) > 50) {
                destX = (velocityX > 0) ? rightSide : leftSide;
            }
            return destX;
        }

        private int calculateY() {
            float velocityY = calculateVelocityY();
            int screenHeight = getScreenHeight();
            int destY = mCurrentPosY + (int) (velocityY * 3);
            if (destY <= 0) {
                destY = getIconVerticalMargin();
            } else if (destY >= screenHeight - mIconSize) {
                destY = screenHeight - mIconSize - getIconVerticalMargin();
            }
            return destY;
        }

        void run() {
            if (mDynamicUpdate == null) {
                mDraggableIcon.animate()
                        .translationX(mDestX)
                        .translationY(mDestY)
                        .setDuration(mDuration)
                        .setInterpolator(mInterpolator)
                        .setListener(mAnimationFinishedListener);
            } else {
                ValueAnimator animator = ValueAnimator.ofInt(0, 100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float percent = valueAnimator.getAnimatedFraction();
                        mDraggableIcon.setTranslationX(mDynamicUpdate.getTranslationX(percent));
                        mDraggableIcon.setTranslationY(mDynamicUpdate.getTranslationY(percent));
                    }
                });
                animator.setDuration(mDuration);
                animator.setInterpolator(mInterpolator);
                animator.addListener(mAnimationFinishedListener);
                animator.start();
            }
        }

        void cancel() {
            mDraggableIcon.animate().cancel();
        }
    }

    public interface DynamicUpdate {
        float getTranslationX(float percent);
        float getTranslationY(float percent);
    }
}
