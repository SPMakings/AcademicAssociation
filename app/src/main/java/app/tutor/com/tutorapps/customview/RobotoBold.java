package app.tutor.com.tutorapps.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import app.tutor.com.tutorapps.helper.FontCache;


/**
 * Created by apple on 28/12/15.
 */
public class RobotoBold extends TextView {

    public RobotoBold(Context context) {
        super(context);
        init(context);
    }

    public RobotoBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RobotoBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        super.setTypeface(FontCache.get("Roboto-Bold.ttf", context));
    }
}

