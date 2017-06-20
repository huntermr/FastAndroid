package demo.vo.response.store;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ScoreFlow {
    private List<Flow> flow;
    private String totalScore;

    public List<Flow> getFlow() {
        return flow;
    }

    public void setFlow(List<Flow> flow) {
        this.flow = flow;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public class Flow{
        private String occurDate;
        private String score;
        private String type;

        public String getOccurDate() {
            return occurDate;
        }

        public void setOccurDate(String occurDate) {
            this.occurDate = occurDate;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
