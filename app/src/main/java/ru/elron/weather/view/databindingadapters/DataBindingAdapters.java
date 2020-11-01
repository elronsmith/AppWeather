package ru.elron.weather.view.databindingadapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

public final class DataBindingAdapters {

    @BindingAdapter({"app:imageVectorDrawable"})
    public static void imageVectorDrawable(ImageView imageView, Integer drawableRes) {
        if (drawableRes == null || drawableRes == 0) return;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Context context = imageView.getContext();
        Drawable drawable = ContextCompat.getDrawable(context, drawableRes);
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter({"app:onClick"})
    public static void viewOnClick(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    @BindingAdapter({"app:onLongClick"})
    public static void viewOnLongClick(View view, View.OnLongClickListener listener) {
        view.setOnLongClickListener(listener);
    }

    @BindingAdapter({"app:visibleInvisible"})
    public static void viewVisibleInvisible(View view, boolean value) {
        view.setVisibility(value ?  View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter({"app:visibleGone"})
    public static void viewVisibleGone(View view, boolean value) {
        view.setVisibility(value ?  View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"app:enabled"})
    public static void viewEnabled(View view, boolean value) {
        view.setEnabled(value);
    }
}
