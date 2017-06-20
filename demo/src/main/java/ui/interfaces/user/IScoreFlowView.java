package demo.ui.interfaces.user;

import demo.base.IBaseView;
import demo.vo.response.store.ScoreFlow;

/**
 * Created by Administrator on 2017/5/27.
 */

public interface IScoreFlowView extends IBaseView {
    /**
     * 获取用户积分记录成功
     * @param scoreFlow
     */
    void uiScoreFlow(ScoreFlow scoreFlow);
}
