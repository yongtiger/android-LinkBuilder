package cc.brainbook.android.linkbuilder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

public class TouchableSpan extends TouchableBaseSpan {

    private final Link link;
    private int textColor;
    private int textColorOfHighlightedLink;

    /**
     * Construct new TouchableSpan using the link
     * @param link
     */
    public TouchableSpan(@NonNull Context context, @NonNull Link link) {
        this.link = link;

        if (link.getTextColor() == 0) {
            this.textColor = getDefaultColor(context, R.styleable.LinkBuilder_defaultLinkColor);
        } else {
            this.textColor = link.getTextColor();
        }

        if (link.getTextColorOfHighlightedLink() == 0) {
            this.textColorOfHighlightedLink = getDefaultColor(context, R.styleable.LinkBuilder_defaultTextColorOfHighlightedLink);

            if (this.textColorOfHighlightedLink == Link.DEFAULT_COLOR) {
                // don't use the default of light blue for this color
                this.textColorOfHighlightedLink = textColor;
            }
        } else {
            this.textColorOfHighlightedLink = link.getTextColorOfHighlightedLink();
        }
    }

    /**
     * Finds the default color for links based on the current theme.
     * @param context activity
     * @param index index of attribute to retrieve based on current theme
     * @return color as an integer
     */
    private int getDefaultColor(Context context, int index) {
        TypedArray array = obtainStyledAttrsFromThemeAttr(context, R.attr.linkBuilderStyle, R.styleable.LinkBuilder);
        int color = array.getColor(index, Link.DEFAULT_COLOR);
        array.recycle();

        return color;
    }

    /**
     * This TouchableSpan has been clicked.
     * @param widget TextView containing the touchable span
     */
    public void onClick(View widget) {
        // handle the click
        if (link.getClickListener() != null) {
            link.getClickListener().onClick(link.getText());
        }
        super.onClick(widget);
    }

    /**
     * This TouchableSpan has been long clicked.
     * @param widget TextView containing the touchable span
     */
    public void onLongClick(View widget) {
        // handle the long click
        if (link.getLongClickListener() != null) {
            link.getLongClickListener().onLongClick(link.getText());
        }
        super.onLongClick(widget);
    }

    /**
     * Set the alpha for the color based on the alpha factor
     * @param color original color
     * @param factor how much we want to scale the alpha to
     * @return new color with scaled alpha
     */
    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Draw the links background and set whether or not we want it to be underlined or bold
     * @param ds the link
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setUnderlineText(link.isUnderlined());
        ds.setFakeBoldText(link.isBold());
        ds.setColor(touched ? textColorOfHighlightedLink : textColor);
        ds.bgColor = touched ? adjustAlpha(textColor, link.getHighlightAlpha()) : Color.TRANSPARENT;
        if(link.getTypeface() != null)
            ds.setTypeface(link.getTypeface());
    }

    protected static TypedArray obtainStyledAttrsFromThemeAttr(@NonNull Context context, int themeAttr, int[] styleAttrs) {
        // Need to get resource id of style pointed to from the theme attr
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(themeAttr, outValue, true);
        final int styleResId = outValue.resourceId;

        // Now return the values (from styleAttrs) from the style
        return context.obtainStyledAttributes(styleResId, styleAttrs);
    }
}