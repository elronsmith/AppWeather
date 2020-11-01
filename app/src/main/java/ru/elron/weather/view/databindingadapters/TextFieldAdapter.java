package ru.elron.weather.view.databindingadapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.google.android.material.internal.TextWatcherAdapter;

public class TextFieldAdapter {
    @BindingAdapter(value = {"value"})
    public static void setValueB(EditText view, String value) {
        view.setText(value);
    }

    @InverseBindingAdapter(attribute = "value")
    public static String getValueB(EditText view) {
        return view.getText().toString();
    }

    @BindingAdapter(value = {"onValueChange", "valueAttrChanged"}, requireAll = false)
    public static void onValueChangeB(EditText view,
                                      final TextWatcherAdapter textWatcherAdapter,
                                      final InverseBindingListener attrChange) {
        if (attrChange == null)
            view.addTextChangedListener(textWatcherAdapter);
        else
            view.addTextChangedListener(new TextWatcher() {
                @SuppressLint("RestrictedApi")
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    textWatcherAdapter.beforeTextChanged(s, start, count, after);
                }

                @SuppressLint("RestrictedApi")
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    textWatcherAdapter.onTextChanged(s, start, before, count);
                    attrChange.onChange();
                }

                @SuppressLint("RestrictedApi")
                @Override
                public void afterTextChanged(Editable s) {
                    textWatcherAdapter.afterTextChanged(s);
                }
            });
    }

    @BindingAdapter({"app:editorActionListener"})
    public static void setOnEditorActionListener(EditText view, TextView.OnEditorActionListener listener) {
        view.setOnEditorActionListener(listener);
    }
}
