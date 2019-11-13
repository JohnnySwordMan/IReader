package com.abyte.wanandroid.ui;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.activity.BaseActivity;
import com.abyte.wanandroid.contract.RegisterContract;
import com.abyte.wanandroid.core.bean.LoginEntity;
import com.abyte.wanandroid.presenter.RegisterPresenter;

/**
 * Created by Jaygege on 2018/10/18
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, View.OnClickListener {

    private EditText mEtUserName;
    private EditText mEtPassword;
    private EditText mEtPasswordConfirm;
    private Button mBtnRegister;
    private RegisterPresenter mPresenter;

    @Override
    protected RegisterPresenter createPresenter() {
        mPresenter = new RegisterPresenter(this);
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void findViews() {
        super.findViews();
        mEtUserName = findViewById(R.id.et_user_name);
        mEtPassword = findViewById(R.id.et_password_1);
        mEtPasswordConfirm = findViewById(R.id.et_password_2);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        actionRegister();
    }

    private void actionRegister() {
        String userName = mEtUserName.getText().toString().trim();
        String passwordFirst = mEtPassword.getText().toString().trim();
        String passwordConfirm = mEtPasswordConfirm.getText().toString().trim();
        // 调用presenter的注册方法
        mPresenter.register(userName, passwordFirst, passwordConfirm);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        super.showErrorMsg(errorMsg);
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable e) {
        // 注册失败
        showErrorMsg("注册失败" + e.toString());
    }

    @Override
    public void onSuccess(LoginEntity loginEntity) {
        // 将注册成功返回的LoginEntity数据，传给LoginActivity，这里不用EventBus，试着setResult方式传值。
        // 注：项目中有可能看似很不简洁的实现方式，这些都是为了练习
        Intent intent = getIntent();
        intent.putExtra(LoginActivity.RESULT_LOGIN, loginEntity.toGson());
        setResult(LoginActivity.REQUEST_CODE_LOGIN, intent);
        finish();
    }
}
