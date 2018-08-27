package com.hunter.fastandroid.ui.interfaces;

import com.hunter.fastandroid.base.IBaseView;
import com.hunter.fastandroid.vo.Book;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface ITestView extends IBaseView {
    void showData(List<Book> datas);
}
