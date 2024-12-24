package tamhoang.ldpro4.ui.widget.bubbleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import tamhoang.ldpro4.R;
import tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable;

public class BubbleLinearLayout extends LinearLayout {
    private tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable bubbleDrawable;
    private float mArrowWidth;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowPosition;
    private tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.ArrowLocation mArrowLocation;
    private int bubbleColor;
    private boolean mArrowCenter;

    public BubbleLinearLayout(Context context) {
        super(context);
        initView(null);
    }

    public BubbleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
                    tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.Builder.DEFAULT_ARROW_WITH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
                    tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            mAngle = array.getDimension(R.styleable.BubbleView_angle,
                    tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.Builder.DEFAULT_ANGLE);
            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition,
                    tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
            bubbleColor = array.getColor(R.styleable.BubbleView_bubbleColor,
                    tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.ArrowLocation.mapIntToValue(location);
            mArrowCenter = array.getBoolean(R.styleable.BubbleView_arrowCenter, false);
            array.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setUp(w, h);
        }
    }

    private void setUp(int left, int right, int top, int bottom) {
        if (right < left || bottom < top)
            return;
        RectF rectF = new RectF(left, top, right, bottom);
        bubbleDrawable = new tamhoang.ldpro4.ui.widget.bubbleview.BubbleDrawable.Builder()
                .rect(rectF)
                .arrowLocation(mArrowLocation)
                .bubbleType(BubbleDrawable.BubbleType.COLOR)
                .angle(mAngle)
                .arrowHeight(mArrowHeight)
                .arrowWidth(mArrowWidth)
                .arrowPosition(mArrowPosition)
                .bubbleColor(bubbleColor)
                .arrowCenter(mArrowCenter)
                .build();
    }

    private void setUp(int width, int height) {
        setUp(getPaddingLeft(), width - getPaddingRight(),
                getPaddingTop(), height - getPaddingBottom());
        setBackgroundDrawable(bubbleDrawable);
    }

    public void setUpBubbleDrawable() {
        setBackgroundDrawable(null);
        post(new Runnable() {
            @Override
            public void run() {
                setUp(getWidth(), getHeight());
            }
        });
    }

}
