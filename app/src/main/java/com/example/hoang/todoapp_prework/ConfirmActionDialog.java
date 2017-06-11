package com.example.hoang.todoapp_prework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;



public class ConfirmActionDialog {

    /* @@@@@@@ DELETE  BUTTON @@@@@@@ */

	public static void showConfirmDeleteDialogForTask(Activity sourceActivity, Task task, Database database){
		Dialog confirmCancelDialog;
		confirmCancelDialog = new AlertDialog.Builder(sourceActivity)
            .setIcon(R.drawable.warning_icon)
            .setTitle("Are you sure to want to delete this task?")
            .setPositiveButton("Yes",
                    new YesDeleteButtonListener(sourceActivity, task, database))
            .setNegativeButton("No",
                    new CloseDialogButtonListener())
            .create();
		confirmCancelDialog.show();
	}

	// An inner class to handle event when user select Yes button on Confirm Cancel dialog
	private static class YesDeleteButtonListener implements OnClickListener {

		private Activity sourceActivity; // the activity that called the dialog
		private Task task;
		private Database database;

		public YesDeleteButtonListener(Activity sourceActivity, Task task, Database database){
			this.sourceActivity = sourceActivity;
			this.task = task;
			this.database = database;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			database.deleteTask(task);
			sourceActivity.finish();
		}

	}

	private static class CloseDialogButtonListener implements OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss(); // just close the dialog box
		}

	}

    /* @@@@@@@ CANCEL BUTTON @@@@@@@ */

    public static void showConfirmCancelDialog(Activity sourceActivity){
        Dialog confirmCancelDialog;
        confirmCancelDialog = new AlertDialog.Builder(sourceActivity)
                .setIcon(R.drawable.warning_icon)
                .setTitle(R.string.confirm_cancel)
                .setPositiveButton(R.string.yes,
                        new CancelButtonListener(sourceActivity))
                .setNegativeButton(R.string.no,
                        new CloseDialogButtonListener())
                .create();
        confirmCancelDialog.show();
    }

    private static class CancelButtonListener implements OnClickListener {

        private Activity sourceActivity;

        public CancelButtonListener(Activity sourceActivity){
            this.sourceActivity = sourceActivity;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            sourceActivity.finish();
        }

    }

}
