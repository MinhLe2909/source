package tamhoang.ldpro4.ui.widget.rangeseekbar;

import tamhoang.ldpro4.ui.widget.rangeseekbar.RangeSeekBar;

public interface OnRangeChangedListener {
    void onRangeChanged(tamhoang.ldpro4.ui.widget.rangeseekbar.RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser);

    void onStartTrackingTouch(tamhoang.ldpro4.ui.widget.rangeseekbar.RangeSeekBar view, boolean isLeft);

    void onStopTrackingTouch(RangeSeekBar view, boolean isLeft);
}