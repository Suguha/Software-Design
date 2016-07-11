package com.sysu.edgar.beethoven;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

/**
 * Created by Edgar on 2016/7/6.
 */
public class LoginActivity extends AppCompatActivity {
    private PopupWindow register_pop, login_pop;
    private Button btn_register = null;
    private Button btn_login = null;
    private Button btn_reg = null;
    private EditText text_name_2 = null;
    private EditText text_pwd_2 = null;
    private EditText text_name_1 = null;
    private EditText text_pwd_1 = null;
    private MainActivity _test;

    public void proc_login(final View loginView, final View regView, final MainActivity activity, final int w, final int h) {
//        _test = activity;
        text_name_1 = (EditText)loginView.findViewById(R.id.edit_text_account);
        text_pwd_1 = (EditText)loginView.findViewById(R.id.edit_text_password);
        text_name_1.setText("");
        text_pwd_1.setText("");
        login_pop = new PopupWindow(loginView, w, h, true);
        login_pop.setAnimationStyle(R.style.popup_anim_style);
        login_pop.setBackgroundDrawable(new ColorDrawable());
        login_pop.setOutsideTouchable(true);
        login_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                activity.backgroundAlpha(1.0f);
                login_pop.dismiss();
            }
        });
        if (login_pop != null || !login_pop.isShowing()) {
            activity.backgroundAlpha(0.3f);
            login_pop.showAtLocation(loginView.getRootView(), Gravity.CENTER, 0, 0);
        }
        process(loginView, regView, login_pop, w, h);
    }

    public void process(final View loginView, final View regView, final PopupWindow login_pop, final int w, final int h) {
        btn_register = (Button)loginView.findViewById(R.id.btn_signup);
        text_name_2 = (EditText)regView.findViewById(R.id.edit_text_account);
        text_pwd_2 = (EditText)regView.findViewById(R.id.edit_text_password);
        register_pop = new PopupWindow(regView, w, h, true);
        register_pop.setAnimationStyle(R.style.popup_anim_style);
        register_pop.setBackgroundDrawable(new ColorDrawable());
        register_pop.setOutsideTouchable(true);
        register_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                register_pop.dismiss();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (register_pop != null || !register_pop.isShowing()) {
                    text_name_2.setText("");
                    text_pwd_2.setText("");
                    register_pop.showAtLocation(regView.getRootView(), Gravity.CENTER, 0, 0);
                }
            }
        });

        //Login and Register Verify Here.
        btn_reg = (Button)regView.findViewById(R.id.btn_register);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo verify
                login_pop.dismiss();
                register_pop.dismiss();
            }
        });
        btn_login = (Button)loginView.findViewById(R.id.btn_signin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo verify
                login_pop.dismiss();
            }
        });
    }
}
