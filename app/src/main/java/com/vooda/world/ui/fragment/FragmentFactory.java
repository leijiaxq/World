package com.vooda.world.ui.fragment;

import com.vooda.world.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: TODO
 * @author: zhujun
 * @date: 2016/12/21 0021  下午 3:09
 */
public class FragmentFactory {
    public static final int FRAGMENT_TASK = 0;
    public static final int FRAGMENT_EXCHANGE = 1;
    public static final int FRAGMENT_MY = 2;

    private static Map<Integer, BaseFragment> fragments = new HashMap<>();


    public static BaseFragment getFragment(int position) {
        BaseFragment fragment = null;
        if (fragments.containsKey(position)) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        switch (position) {
            case FRAGMENT_TASK:
                fragment = new TaskHomeFragment();       //任务
                break;
            case FRAGMENT_EXCHANGE:
                fragment = new ExchangeFragment();  //兑换
                break;
            case FRAGMENT_MY:
                fragment = new MyFragment();  //我的
                break;
            default:
                break;
        }
        fragments.put(position, fragment);
        return fragment;
    }

}
