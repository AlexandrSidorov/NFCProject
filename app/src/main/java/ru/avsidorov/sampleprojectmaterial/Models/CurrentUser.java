package ru.avsidorov.sampleprojectmaterial.Models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flurry.android.FlurryAgent;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;

import retrofit.client.Response;
import ru.avsidorov.sampleprojectmaterial.Api.Request;
import ru.avsidorov.sampleprojectmaterial.BaseActivity;
import ru.avsidorov.sampleprojectmaterial.Dialogs;
import ru.avsidorov.sampleprojectmaterial.R;

/**
 * Created by Сидоров on 24.04.2015.
 */
public class CurrentUser {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    String mLogin, mPassword, mToken;


    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    //Getters and Setters


    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    private static CurrentUser ourInstance = new CurrentUser();

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    private CurrentUser() {

    }

    /**Save Null for Login,Password and Token.
     * Save this to SharedPreferences.
     *
     * @param preferences
     */
    public void clear(SharedPreferences preferences) {
        mLogin = null;
        mPassword = null;
        mToken = null;
        saveToSharedPreferences(preferences);

    }


    public void saveToSharedPreferences(SharedPreferences preferences) {

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(LOGIN, mLogin);
        edit.putString(PASSWORD, mPassword);
        edit.putString(TOKEN, mToken);
        edit.apply();

    }

    /**
     * TODO Некорректно работает загрузка из настроек
     */
    public boolean loadFromSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);

        if (preferences != null) {
            mLogin = preferences.getString(LOGIN, null);
            mPassword = preferences.getString(PASSWORD, null);
            mToken = preferences.getString(TOKEN, null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Show authorization dialog and init Singleton {@link ru.avsidorov.sampleprojectmaterial.Models.CurrentUser}
     * @param activity extends Robospice Activity. {@link ru.avsidorov.sampleprojectmaterial.BaseActivity}
     */
    public void authorizationDialog(final Activity activity) {
        final SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        new MaterialDialog.Builder(activity)
                .title(R.string.auth_need)
                .customView(R.layout.auth_view, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .autoDismiss(false)
                .cancelable(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        activity.finish();
                    }

                    @Override
                    public void onPositive(final MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        final ProgressBarCircularIndeterminate authProgress = (ProgressBarCircularIndeterminate) dialog.findViewById(R.id.progressBarAuthDialog);
                        final EditText etLogin = (EditText) dialog.findViewById(R.id.loginET);
                        final EditText etPassword = (EditText) dialog.findViewById(R.id.passwordET);
                        authProgress.setVisibility(View.VISIBLE);
                        etLogin.setEnabled(false);
                        etPassword.setEnabled(false);
                        setLogin(etLogin.getText().toString());
                        setPassword(etPassword.getText().toString());
                        setToken(encodeCredentialsForBasicAuthorization(mLogin, mPassword));
                        FlurryAgent.setUserId(etLogin.getText().toString());
                        if (mLogin.isEmpty() || mPassword.isEmpty()) {
                            Dialogs.showInputIsEmpty(activity);

                        } else {
                            saveToSharedPreferences(preferences);
                            Request.ValuesRequest valuesRequest = new Request.ValuesRequest(mToken);

                            ((BaseActivity) activity).getSpiceManager().execute(valuesRequest, new PendingRequestListener<Response>() {
                                @Override
                                public void onRequestFailure(SpiceException spiceException) {

                                    etLogin.setEnabled(true);
                                    etPassword.setEnabled(true);
                                    Dialogs.showRetrofitErrorDialog(activity, spiceException);
                                    etLogin.setText("");
                                    etPassword.setText("");
                                    clear(preferences);
                                    dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                    authProgress.setVisibility(View.GONE);

                                }

                                @Override
                                public void onRequestSuccess(Response response) {
                                    FlurryAgent.setUserId(mLogin);
                                    new MaterialDialog.Builder(activity).content(R.string.auth_success).positiveText(R.string.ok).show();
                                    saveToSharedPreferences(preferences);
                                    dialog.dismiss();
                                    authProgress.setVisibility(View.GONE);

                                }

                                @Override
                                public void onRequestNotFound() {

                                }
                            });


                        }
                    }
                })
                .show();
    }

    /**Function for encode login and password for Basic authorization
     *
     * @param login User Login
     * @param password User Password
     * @return "Basic:" + Base64 String
     */
    private String encodeCredentialsForBasicAuthorization(String login, String password) {
        return "Basic " + Base64.encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
    }


}
