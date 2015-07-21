package com.nvurgaft.redmonk;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nvurgaft.redmonk.Dialogs.ConfirmDialog;
import com.nvurgaft.redmonk.Dialogs.EditContactDialog;
import com.nvurgaft.redmonk.Dialogs.EditDailyConsumptionDialog;
import com.nvurgaft.redmonk.Dialogs.EditReminderDialog;
import com.nvurgaft.redmonk.Entities.Contact;
import com.nvurgaft.redmonk.Entities.DailyConsumption;
import com.nvurgaft.redmonk.Entities.Reminder;
import com.nvurgaft.redmonk.Fragments.ContactsFragment;
import com.nvurgaft.redmonk.Fragments.DailyConsumptionFragment;
import com.nvurgaft.redmonk.Fragments.RemindersFragment;
import com.nvurgaft.redmonk.Model.ConnectionManager;
import com.nvurgaft.redmonk.Model.SqlAccess;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks, OnFragmentInteractionListener, EditContactDialog.NoticeDialogListener,
        ConfirmDialog.NoticeDialogListener, EditReminderDialog.NoticeDialogListener, EditDailyConsumptionDialog.NoticeDialogListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    private SharedPreferences sharedPreferences;

    protected SqlAccess sqlAccess;
    protected SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        db = ConnectionManager.getConnection(this);
        sqlAccess = new SqlAccess(this);

        sharedPreferences = getSharedPreferences(Values.PREFS, MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "anon");
        String email = sharedPreferences.getString("email", "anon@gmail.com");

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(username, email, BitmapFactory.decodeResource(getResources(), R.mipmap.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_exit:
                ConfirmDialog confirmDialog = new ConfirmDialog();
                Bundle bundle = new Bundle();
                bundle.putString("content", "Really Exit ?");
                bundle.putString("identifier", "exitPrompt");
                bundle.putString("tag", "exitPrompt");
                confirmDialog.setArguments(bundle);
                confirmDialog.show(getFragmentManager(), "ExitPrompt");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Contact contact, boolean isEdit) {
        db = ConnectionManager.getConnection(this);

        if (!isEdit) {
            sqlAccess.insertNewContact(db, contact);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        } else {
            sqlAccess.updateContact(db, contact);
            Toast.makeText(this, "Contact updated : " + contact.toString(), Toast.LENGTH_SHORT).show();
        }

        ((ContactsFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
        db.close();
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Reminder reminder, boolean isEdit) {
        db = ConnectionManager.getConnection(this);

        if (!isEdit) {
            sqlAccess.insertNewReminder(db, reminder);
            Toast.makeText(this, "Reminder saved", Toast.LENGTH_SHORT).show();
        } else {
            sqlAccess.updateReminder(db, reminder);
            Toast.makeText(this, "Reminder updated : " + reminder.getTodo(), Toast.LENGTH_SHORT).show();
        }

        ((RemindersFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
        db.close();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, DailyConsumption dailyConsumption, boolean isEdit) {
        db = ConnectionManager.getConnection(this);

        if (!isEdit) {
            sqlAccess.insertDailyConsumption(db, dailyConsumption);
            Toast.makeText(this, "Daily consumption saved", Toast.LENGTH_SHORT).show(); // TODO: remove after testing
        } else {
            sqlAccess.updateDailyConsumptionByDate(db, dailyConsumption);
            Toast.makeText(this, "Daily consumption updated : " + dailyConsumption.toString(), Toast.LENGTH_SHORT).show();
        }

        ((DailyConsumptionFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
        db.close();
    }
    /**
     * Accepts confirmation of confirm dialogs
     * @param dialog
     * @param value
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String value, String tag) {
        switch (tag) {
            case "exitPrompt":
                finish();
                break;
            case "consumptionPrompt":
                db = ConnectionManager.getConnection(this);
                Toast.makeText(this, "value : " + value, Toast.LENGTH_SHORT).show();
                sqlAccess.deleteConsumptionLogByDate(db, Long.valueOf(value));
                ((DailyConsumptionFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
                db.close();
                break;
            case "contactPrompt":
                db = ConnectionManager.getConnection(this);
                sqlAccess.removeContactByName(db, value);
                ((ContactsFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
                db.close();
                break;
            case "reminderPrompt":
                db = ConnectionManager.getConnection(this);
                sqlAccess.removeReminder(db, Integer.valueOf(value));
                ((RemindersFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
                db.close();
                break;
            default:
                Log.d(Values.LOG, "Invalid confirm dialog response");
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, Reminder reminder, boolean isEdit) {
        ((RemindersFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, DailyConsumption dailyConsumption, boolean isEdit) {
        ((DailyConsumptionFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, Contact contact, boolean isEdit) {
        ((ContactsFragment) mNavigationDrawerFragment.getCurrentFragment()).refreshFragmentView();
    }



}
