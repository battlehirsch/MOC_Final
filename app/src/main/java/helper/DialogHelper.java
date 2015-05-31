package helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.norbe_000.moc_final.R;

import activities.studiActivity.StudiActivity;

/**
 * Created by Battlehirsch on 31.05.2015.
 */
public class DialogHelper extends DialogFragment{

    public interface DialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    DialogListener dListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            dListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bitte auswählen")
                .setPositiveButton("Master", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dListener.onDialogPositiveClick(DialogHelper.this);
                    }
                })
                .setNegativeButton("Bachelor", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dListener.onDialogNegativeClick(DialogHelper.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
