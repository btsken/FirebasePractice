package com.biginnov.syncnote;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ken on 2015/12/14.
 */
public class BaseActivity extends AppCompatActivity {

    protected ProgressDialogFragment mProgressDialogFragment;
    protected boolean mPaused;
    protected boolean mDismissProgressOnResume;

    protected final <E extends View> E getView(int id) {
        return (E) findViewById(id);
    }

    public void showProgressDialog() {
        showProgressDialog(false);
    }

    public void showProgressDialog(boolean cancelable) {
        if (mProgressDialogFragment == null) {
            mProgressDialogFragment = ProgressDialogFragment.getInstance(cancelable);
            mProgressDialogFragment.setCancelable(cancelable);
            mProgressDialogFragment.show(
                    getSupportFragmentManager(), ProgressDialogFragment.getFragmentTAG());
        }
    }

    public void dismissProgressDialog() {
        if (mPaused) {
            mDismissProgressOnResume = true;
        } else if (mProgressDialogFragment != null) {
            mProgressDialogFragment.dismiss();
            mProgressDialogFragment = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPaused = false;
        if (mDismissProgressOnResume) {
            mDismissProgressOnResume = false;
            dismissProgressDialog();
        }
    }

    @Override
    protected void onPause() {
        mPaused = true;
        super.onPause();
    }
}
