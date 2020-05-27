package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Helper {
    @NotNull
    public static String getString(TextInputLayout inputLayout) {
        return Objects.requireNonNull(inputLayout.getEditText())
                .getText().toString().trim();
    }

}
