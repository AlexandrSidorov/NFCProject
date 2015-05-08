package ru.avsidorov.sampleprojectmaterial;

import android.app.Activity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Сидоров on 23.04.2015.
 */
public class Dialogs {
    public static void showRetrofitErrorDialog(Activity activity,SpiceException spiceException){
        new MaterialDialog.Builder(activity)
                .title(R.string.rf_error_title)
                .content(spiceException.getLocalizedMessage())
                .positiveText(R.string.ok)
                .show();

    }

    public static void showInputIsEmpty(Activity activity){
        new MaterialDialog.Builder(activity)
                .title(R.string.input_error)
                .content(R.string.input_error_text)
                .positiveText(R.string.ok)
                .show();
    }

}
