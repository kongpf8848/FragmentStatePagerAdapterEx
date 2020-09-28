package com.github.kongpf8848.extablayout.demo.base;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 通用的RecyclerView Adapter
 * @param <T>
 */

public abstract class BaseRecyclerViewAdapter<T extends BaseEntity> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseRecyclerViewHolder> {
    protected Context context;
    protected List<T> list = new ArrayList<T>();
    protected OnItemClickListener onItemClickListener = null;
    protected SparseIntArray layouts;
    protected SparseArray<Class<?>> viewholders;

    //获取指定Item的ViewType
    public abstract int getItemViewType(final int position,final T t);
    //添加ViewType
    public abstract void initViewType();

    public interface OnItemClickListener<T> {
        void onRecyclerViewItemClick(int position, final T t);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        initViewType();
    }

    protected void addViewType(int viewType, @LayoutRes int layoutResId, Class<?>cls) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(viewType, layoutResId);
        if (viewholders == null) {
            viewholders = new SparseArray<Class<?>>();
        }
        viewholders.put(viewType,cls);
    }

    public BaseRecyclerViewHolder getViewHolder(final View view,final int viewType){
        Class<?>cls=(Class<?>)viewholders.get(viewType);
        try {
            Constructor constructor;
            if (cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers())) {
                constructor = cls.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (BaseRecyclerViewHolder) constructor.newInstance(this,view);

            } else {
                constructor = cls.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (BaseRecyclerViewHolder) constructor.newInstance(view);
            }


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected int getLayoutId(final int viewType){
        return layouts.get(viewType,0);
    }

    @Override
    public int getItemViewType(int position) {
        if(list!=null && list.size()>0){
            return getItemViewType(position,list.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(getLayoutId(viewType), parent, false);
        return getViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder,final int position) {
        if(list!=null && list.size()>0){
            final T t = list.get(position);
            holder.fill(position, t);
            if(onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onRecyclerViewItemClick(position, t);
                    }
                });
            }
        }
    }

    public List<T> getList(){
        return list;
    }
    public void add(T t) {
        if(list!=null) {
            list.add(t);
            notifyDataSetChanged();
        }

    }
    public void add(List<T> ll) {
        if(list!=null){
            list.addAll(ll);
            notifyDataSetChanged();
        }

    }

    public void add(int index,T t){
        if(list!=null){
            list.add(index,t);
            notifyDataSetChanged();
        }
    }

    public void remove(int position){
        if(list!=null && list.size()>0){
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    public void remove(List<T> datas){
        if(list!=null && list.size()>0){
            list.removeAll(datas);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if(list!=null && list.size()>0) {
            list.clear();
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        if (list != null && list.size()>0) {
            return list.size();
        }
        return 0;
    }

    public int getItemCount(int viewType){
        int num=0;
        if(list!=null && list.size()>0){
            int count=getItemCount();
            for(int i=0;i<count;i++){
               if(getItemViewType(i)==viewType){
                   num++;
               }
            }
        }
        return num;
    }

    public T getItem(final int position) {
        if (list != null && list.size()>0) {
            return list.get(position);
        }
        return null;
    }


    public static abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

        protected int position=0;
        public abstract void bindView(final int position, final T t);

        public BaseRecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void fill(final int position, final T t){
             this.position=position;
             bindView(position,t);
        }


    }

}
