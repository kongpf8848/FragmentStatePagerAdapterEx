# FragmentStatePagerAdapterEx
优雅彻底的解决Fragment动态添加，删除不生效的问题，自定义PagerAdapter，替代系统中的FragmentStatePagerAdapter，实现Fragment的动态添加，删除操作，而不需要ViewPager重新设置Adapter,ViewPager中的Fragment不需要重新走生命周期

### 问题
使用FragmentPagerAdapter或FragmentStatePagerAdapter管理Fragment时，添加或删除Fragment时，调用notifyDataSetChanged不生效，查看PagerAdapter源码可知，getItemPosition返回的值为POSITION_UNCHANGED，即位置没有改变，刷新自然就不生效了

```java
    /**
     * Called when the host view is attempting to determine if an item's position
     * has changed. Returns {@link #POSITION_UNCHANGED} if the position of the given
     * item has not changed or {@link #POSITION_NONE} if the item is no longer present
     * in the adapter.
     *
     * <p>The default implementation assumes that items will never
     * change position and always returns {@link #POSITION_UNCHANGED}.
     *
     * @param object Object representing an item, previously returned by a call to
     *               {@link #instantiateItem(View, int)}.
     * @return object's new position index from [0, {@link #getCount()}),
     *         {@link #POSITION_UNCHANGED} if the object's position has not changed,
     *         or {@link #POSITION_NONE} if the item is no longer present.
     */
    public int getItemPosition(@NonNull Object object) {
        return POSITION_UNCHANGED;
    }
```
如果我们覆盖getItemPosition方法，简单粗暴的返回POSITION_NONE，调用notifyDataSetChanged，确实可以实现Fragment添加删除生效，但会导致所有的Fragment重建走生命周期流程，也不太合理。


### 解决办法

覆盖PagerAdapter的getItemPosition方法，当Fragment对应的数据发生变化后，getItemPosition方法根据具体情况返回新的位置，而不是简单的返回POSITION_UNCHANGED或POSITION_NONE

```java
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
                Log.d(TAG, "++++++++++getItemPosition() called with: oldPosition:"+oldPosition+",POSITION_UNCHANGED");
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
                Log.d(TAG,"----------oldposition:"+oldPosition+",newposition:"+oldDataNewPosition);
                return oldDataNewPosition;
            }

        }

        return POSITION_UNCHANGED;
    }
```
方法中用到了几个抽象方法，如下：
```java

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
```

### 使用
简单的使用如下，具体使用可以参考demo

```java
public class TestAdapater extends FragmentStatePagerAdapterEx<Channel> {

    public TestAdapater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Channel getItemData(int position) {
        return null;
    }

    @Override
    public boolean dataEquals(Channel oldData, Channel newData) {
        return false;
    }

    @Override
    public int getDataPosition(Channel data) {
        return 0;
    }
}
```


