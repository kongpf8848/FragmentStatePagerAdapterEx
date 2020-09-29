package com.github.kongpf8848.extablayout.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import butterknife.ButterKnife;

public abstract class BaseLazyFragment extends BaseFragment {

    private boolean isFristVisible;
    protected Context context;
    protected View mRootView;
    private View view;

    public abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    private void initVariable() {
        isFristVisible=true;
        mRootView=null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mRootView==null){
            return;
        }
        if(isFristVisible && isVisibleToUser){
            isFristVisible=false;
            onFragmentFirstVisible();
            return;
        }

        onFragmentVisibleChange(isVisibleToUser);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mRootView==null){
            mRootView=view;
            if(getUserVisibleHint()){
                if(isFristVisible) {
                    isFristVisible = false;
                    onFragmentFirstVisible();
                    return;
                }
                onFragmentVisibleChange(true);
            }
        }

    }


    protected void onFragmentVisibleChange(boolean isVisibleToUser) {

    }

    protected void onFragmentFirstVisible() {
        Log.d("JACK8","onFragmentFirstVisible:"+getClass().getSimpleName());
    }

    protected <T> T findViewById(int id){
        if(mRootView!=null){
            return (T)mRootView.findViewById(id);
        }
        return null;
    }
}
