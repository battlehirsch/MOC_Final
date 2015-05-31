package helper;

import android.app.DialogFragment;

/**
 * Created by matthias rohrmoser on 31.05.2015.
 * Interface for the communication between a dialog and activity
 */
public interface IDialogListener {
    void onDialogPositiveClick(DialogFragment dialog);
    void onDialogNegativeClick(DialogFragment dialog);
}
