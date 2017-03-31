package com.vooda.world.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vooda.world.R;
import com.vooda.world.base.BaseActivity;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.base.BaseFragment;
import com.vooda.world.bean.UserInfoBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.eventbus.EventBusUtil;
import com.vooda.world.eventbus.EventObject;
import com.vooda.world.ui.fragment.FragmentFactory;
import com.vooda.world.utils.ActivityStackUtil;
import com.vooda.world.utils.SPUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.task_rb)
    RadioButton mRbTask;
    @BindView(R.id.excahnge_rb)
    RadioButton mRbExcahnge;
    @BindView(R.id.my_rb)
    RadioButton mRbMy;
    @BindView(R.id.main_rg)
    RadioGroup  mRgMain;
    @BindView(R.id.main_fl_layout)
    FrameLayout mFlContentFrame;

    private BaseFragment mContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
        EventBusUtil.register(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRgMain.setOnCheckedChangeListener(this);

    }

    @Override
    protected void loadData() {
        replaceFragment(FragmentFactory.FRAGMENT_TASK);
        saveForeverUserInfo();
        getForeverUserInfo();
    }

    //把每次登陆的用户信息持久化保存
    private void saveForeverUserInfo() {
        UserInfoBean bean = BaseApplication.getInstance().getUserInfoBean();
        if (bean != null) {
            //判断是否是首次登录，为0，则用户是首次登录，保存用户信息---因为没有用户信息修改需求，所以只需要保存用户信息一次即可
            int userID = (int) SPUtil.get(mContext, Contants.USER_ID, 0);
            if (userID == 0) {
                SPUtil.put(mContext, Contants.USER_ID, bean.getUserID());
                SPUtil.put(mContext, Contants.USER_NAME, bean.getUserName());
                SPUtil.put(mContext, Contants.USER_HEADURL, bean.getHeadUrl());
                SPUtil.put(mContext, Contants.USER_PHONE, bean.getPhone());
                SPUtil.put(mContext, Contants.USER_STATUS, bean.getIsStatus());
                SPUtil.put(mContext, Contants.USER_FOLLOW, bean.getIsfollow());
                SPUtil.put(mContext, Contants.USER_DEVICENUMBER, bean.getDeviceNumber());
                SPUtil.put(mContext, Contants.USER_OPENID, bean.getOpenID());
                SPUtil.put(mContext, Contants.USER_UNIONID, bean.getUnionID());
            } else {
                //因为用户是否关注微信公众号由后台返回，所以用户是否关注微信公众号的状态是变动的，这里重新保存一次
                SPUtil.put(mContext, Contants.USER_FOLLOW, bean.getIsfollow());
            }
        }
    }

    //取出保存的用户数据，主要是防止应用内存被系统回收后，重新打开应用时，读取持久化保存的用户信息
    private void getForeverUserInfo() {
        UserInfoBean bean = BaseApplication.getInstance().getUserInfoBean();
        if (bean == null) {
            bean = new UserInfoBean();
            bean.setUserID((int) SPUtil.get(mContext, Contants.USER_ID, 0));
            bean.setUserName((String) SPUtil.get(mContext, Contants.USER_NAME, ""));
            bean.setHeadUrl((String) SPUtil.get(mContext, Contants.USER_HEADURL, ""));
            bean.setPhone((String) SPUtil.get(mContext, Contants.USER_PHONE, ""));
            bean.setIsStatus((int) SPUtil.get(mContext, Contants.USER_STATUS, 0));
            bean.setIsfollow((int) SPUtil.get(mContext, Contants.USER_FOLLOW, 0));
            bean.setDeviceNumber((String) SPUtil.get(mContext, Contants.USER_DEVICENUMBER, ""));
            bean.setOpenID((String) SPUtil.get(mContext, Contants.USER_OPENID, ""));
            bean.setUnionID((String) SPUtil.get(mContext, Contants.USER_UNIONID, ""));
            BaseApplication.getInstance().setUserInfoBean(bean);
        }
    }


    /**
     * fragment切换
     */
    public void replaceFragment(int index) {
        BaseFragment to = FragmentFactory.getFragment(index);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mContent != to) {
            if (mContent == null) {
                transaction.add(R.id.main_fl_layout, to).commitAllowingStateLoss();
            } else {
                if (!to.isAdded()) {    // 先判断是否被add过
                    transaction.hide(mContent).add(R.id.main_fl_layout, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            mContent = to;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.task_rb:
                replaceFragment(FragmentFactory.FRAGMENT_TASK);
                break;
            case R.id.excahnge_rb:
                replaceFragment(FragmentFactory.FRAGMENT_EXCHANGE);
                break;
            case R.id.my_rb:
                replaceFragment(FragmentFactory.FRAGMENT_MY);
                break;
            default:
                break;
        }
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),
                        "再按一次退出",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityStackUtil.AppExit();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Subscribe
    public void onEventMainThread(EventObject eventObject) {
        if (eventObject.id == Contants.TWO) {  //申请成功提现-点击确定--返回到 我的 页面
            mRbMy.setChecked(true);
        }
    }
}
