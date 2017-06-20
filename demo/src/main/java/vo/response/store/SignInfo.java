package demo.vo.response.store;

/**
 * Created by Administrator on 2017/5/26.
 */

public class SignInfo {
    private String days;
    private String score;
    private boolean signed;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
}
