package com.jaygege.smartx.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jaygege.smartx.R;
import com.jaygege.smartx.base.activity.BaseActivity;
import com.jaygege.smartx.contract.LoginContract;
import com.jaygege.smartx.core.bean.LoginEntity;
import com.jaygege.smartx.presenter.LoginPresenter;
import com.jaygege.smartx.ui.me.LoginMessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 登录页面
 * Created by geyan on 2018/10/18
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    public static final int REQUEST_CODE_LOGIN = 1;
    public static final String RESULT_LOGIN = "result_login";
    private EditText mEtUserName;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private LoginPresenter mPresenter;
    private String userName;

    @Override
    protected LoginPresenter createPresenter() {
        mPresenter = new LoginPresenter(this);
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findViews() {
        super.findViews();
        mEtUserName = findViewById(R.id.et_user_name);
        mEtPassword = findViewById(R.id.et_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                actionLogin();
                break;
            case R.id.btn_register:
                actionRegister();
                break;
        }
    }

    /**
     * 跳转到注册页面
     */
    private void actionRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    /**
     * 登录逻辑
     */
    private void actionLogin() {
        userName = mEtUserName.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        // 判空等操作全部交给Presenter，Activity只是用来显示页面的，尽量减少Activity中的逻辑操作
        mPresenter.login(userName, password);
    }

    @Override
    public void onLoginSuccess() {
        // 登录成功，告诉MeFragment，这里使用EventBus(EventBus最好还是少用，项目复杂的话，你都不知道哪是哪)
        EventBus.getDefault().post(new LoginMessageEvent(200,userName));
        finish();
    }

    @Override
    public void onLoginFailure(String errorMsg) {
        mEtUserName.getText().clear();
        mEtPassword.getText().clear();
        showErrorMsg(errorMsg);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        super.showErrorMsg(errorMsg);
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (data != null) {
                String loginEntityStr = data.getExtras().getString(RESULT_LOGIN);
                LoginEntity loginEntity = LoginEntity.buildObject(loginEntityStr, new TypeToken<LoginEntity>() {
                });
                String username = loginEntity.username;
                String password = loginEntity.password;
                mEtUserName.setText(username);
                mEtPassword.setText(password);
                mPresenter.login(username, password);
            }
        }
    }
}
