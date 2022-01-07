package com.example.habitstodotracker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class PickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private PickerFragment.DialogTimeListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean formatHour24 = true;
        return (Dialog) (new TimePickerDialog(this.getContext(), (TimePickerDialog.OnTimeSetListener)this, hour, minute, formatHour24));

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        PickerFragment.DialogTimeListener var10000 = this.mListener;
        if (var10000 != null) {
            var10000.onDialogTimeSet(this.getTag(), i, i1);
        }

    }

    public interface DialogTimeListener {
        void onDialogTimeSet(@org.jetbrains.annotations.Nullable String var1, int var2, int var3);
    }

    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.mListener = (PickerFragment.DialogTimeListener)context;
    }

    public void onDetach() {
        super.onDetach();
        if (this.mListener != null) {
            this.mListener = (PickerFragment.DialogTimeListener)null;
        }

    }
}
