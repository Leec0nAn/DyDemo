package com.example.dydemo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dydemo.bean.TiktokBean;

public class VideoActivityViewModel extends ViewModel {
    private MutableLiveData<TiktokBean> data = new MutableLiveData<>();

    private LiveData<TiktokBean> tikodata = data;

    public LiveData<TiktokBean> getTikodata() {
        return tikodata;
    }

    public void updateTikodata(TiktokBean t) {
        data.setValue(t);
    }

}
