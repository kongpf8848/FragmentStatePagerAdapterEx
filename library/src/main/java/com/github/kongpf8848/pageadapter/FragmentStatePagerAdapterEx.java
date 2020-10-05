package com.github.kongpf8848.pageadapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public abstract class FragmentStatePagerAdapterEx<T> extends PagerAdapter {
    private static final String TAG = "FragmentStatePagerAdapt";
    private static final boolean DEBUG = false;

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;

    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();
    private ArrayList<ItemInfo<T>> mItemInfos = new ArrayList<>();
    private Fragment mCurrentPrimaryItem = null;
    private boolean mNeedProcessCache = false;

    public FragmentStatePagerAdapterEx(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    /**
     * 获取指定位置的Fragment
     * @param position
     * @return
     */
    public abstract Fragment getItem(int position);

    /**
     * 获取指定位置对应的数据
     * @param position
     * @return
     */
    public abstract T getItemData(int position);

    /**
     * 判断新旧数据是否相等
     * @param oldData
     * @param newData
     * @return
     */
    public abstract boolean dataEquals(T oldData, T newData);

    /**
     * 获取指定数据对应的位置
     * @param data
     * @return
     */
    public abstract int getDataPosition(T data);

    @Override
    public void startUpdate(ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // If we already have this item instantiated, there is nothing
        // to do.  This can happen when we are restoring the entire pager
        // from its saved state, where the fragment manager has already
        // taken care of restoring the fragments we previously had instantiated.
        Log.d(TAG, "instantiateItem() called with:position = [" + position + "]");
        if (mItemInfos.size() > position) {
            ItemInfo ii = mItemInfos.get(position);
            if (ii != null) {
                //判断位置是否相等，如果不相等说明新数据有增加或删除(导致了ViewPager那边有空位)，
                // 而这时notifyDataSetChanged方法还没有完成，ViewPager会先调用instantiateItem来获取新的页面
                //所以为了不取错页面，我们需要对缓存进行检查和调整位置：checkProcessCacheChanged
                if (ii.position == position) {
                    return ii;
                } else {
                    checkProcessCacheChanged();
                }
            }
        }

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        Fragment fragment = getItem(position);
        if (DEBUG) Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
        if (mSavedState.size() > position) {
            Fragment.SavedState fss = mSavedState.get(position);
            if (fss != null) {
                fragment.setInitialSavedState(fss);
            }
        }
        while (mItemInfos.size() <= position) {
            mItemInfos.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        ItemInfo<T> iiNew = new ItemInfo<>(fragment, getItemData(position), position);
        mItemInfos.set(position, iiNew);
        mCurTransaction.add(container.getId(), fragment);

        return iiNew;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(DEBUG) {
            Log.d(TAG, "destroyItem() called with:position = [" + position + "]");
        }
        ItemInfo ii = (ItemInfo) object;

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        while (mSavedState.size() <= position) {
            mSavedState.add(null);
        }
        mSavedState.set(position, ii.fragment.isAdded()
                ? mFragmentManager.saveFragmentInstanceState(ii.fragment) : null);
        mItemInfos.set(position, null);

        mCurTransaction.remove(ii.fragment);
    }

    @Override
    @SuppressWarnings("ReferenceEquality")
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        ItemInfo ii = (ItemInfo) object;
        Fragment fragment = ii.fragment;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Fragment fragment = ((ItemInfo) object).fragment;
        return fragment.getView() == view;
    }

    @Override
    public int getItemPosition(Object object) {
        mNeedProcessCache = true;
        ItemInfo<T> itemInfo = (ItemInfo) object;
        //获取数据对应的旧位置
        int oldPosition = mItemInfos.indexOf(itemInfo);
        if (oldPosition >= 0) {
            //获取旧数据
            T oldData = itemInfo.data;
            //获取旧位置对应的新数据
            T newData = getItemData(oldPosition);
            //如果旧数据和新数据相等，则位置不需要调整，返回POSITION_UNCHANGED
            if (dataEquals(oldData, newData)) {
                if(DEBUG) {
                    Log.d(TAG, "++++++++++getItemPosition() called with: oldPosition:" + oldPosition + ",POSITION_UNCHANGED");
                }
                return POSITION_UNCHANGED;
            } else {
                /**
                 *  如果旧数据和新数据不相等，则位置需要调整，此时有两种情况：
                 *  1.旧数据已经不存在，则表示Fragment已经被删除，此时应返回POSITION_NONE
                 *  2.旧数据存在，只是位置发生了变动，则应返回新的位置
                 */
                ItemInfo<T> oldItemInfo = mItemInfos.get(oldPosition);
                int oldDataNewPosition = getDataPosition(oldData);
                if (oldDataNewPosition < 0) {
                    oldDataNewPosition = POSITION_NONE;
                }
                //把新的位置赋值到缓存的itemInfo中，以便调整时使用
                if (oldItemInfo != null) {
                    oldItemInfo.position = oldDataNewPosition;
                }
                if(DEBUG) {
                    Log.d(TAG, "----------oldposition:" + oldPosition + ",newposition:" + oldDataNewPosition);
                }
                return oldDataNewPosition;
            }

        }

        return POSITION_UNCHANGED;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //通知ViewPager更新完成后对缓存的ItemInfo List进行调整
        checkProcessCacheChanged();
    }

    private void checkProcessCacheChanged() {
        //只有调用过getItemPosition(也就是有notifyDataSetChanged)才进行缓存的调整
        if (!mNeedProcessCache) return;
        mNeedProcessCache = false;
        ArrayList<ItemInfo<T>> pendingItemInfos = new ArrayList<>(mItemInfos.size());
        //先存入空数据
        for (int i = 0; i < mItemInfos.size(); i++) {
            pendingItemInfos.add(null);
        }
        //根据缓存的itemInfo中的新position把itemInfo入正确的位置
        for (ItemInfo<T> itemInfo : mItemInfos) {
            if (itemInfo != null) {
                if (itemInfo.position >= 0) {
                    while (pendingItemInfos.size() <= itemInfo.position) {
                        pendingItemInfos.add(null);
                    }
                    pendingItemInfos.set(itemInfo.position, itemInfo);
                }
            }
        }
        mItemInfos = pendingItemInfos;
    }

    @Override
    public Parcelable saveState() {
        Bundle state = null;
        if (mSavedState.size() > 0) {
            state = new Bundle();
            Fragment.SavedState[] fss = new Fragment.SavedState[mSavedState.size()];
            mSavedState.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < mItemInfos.size(); i++) {
            ItemInfo itemInfo=mItemInfos.get(i);
            if(itemInfo!=null) {
                Fragment f = itemInfo.fragment;
                if (f != null && f.isAdded()) {
                    if (state == null) {
                        state = new Bundle();
                    }
                    String key = "f" + i;
                    mFragmentManager.putFragment(state, key, f);
                }
            }
        }
        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            mSavedState.clear();
            mItemInfos.clear();
            if (fss != null) {
                for (int i = 0; i < fss.length; i++) {
                    mSavedState.add((Fragment.SavedState) fss[i]);
                }
            }
            Iterable<String> keys = bundle.keySet();
            for (String key : keys) {
                if (key.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    Fragment f = mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        while (mItemInfos.size() <= index) {
                            mItemInfos.add(null);
                        }
                        f.setMenuVisibility(false);
                        ItemInfo<T> iiNew = new ItemInfo<>(f, getItemData(index), index);
                        mItemInfos.set(index, iiNew);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + key);
                    }
                }
            }
        }
    }

    public Fragment getCurrentPrimaryItem() {
        return mCurrentPrimaryItem;
    }

    public Fragment getFragmentByPosition(int position) {
        if (position < 0 || position >= mItemInfos.size()) return null;
        if(mItemInfos.get(position)==null)return null;
        return mItemInfos.get(position).fragment;
    }

    static class ItemInfo<T> {
        Fragment fragment;
        T data;
        int position;

        public ItemInfo(Fragment fragment, T data, int position) {
            this.fragment = fragment;
            this.data = data;
            this.position = position;
        }
    }
}
