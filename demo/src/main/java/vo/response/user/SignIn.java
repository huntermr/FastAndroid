package demo.vo.response.user;

/**
 * Created by Administrator on 2017/6/7.
 */

public class SignIn {
    private String days;
    private String todScore;
    private String tmrScore;
    private boolean signed;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTodScore() {
        return todScore;
    }

    public void setTodScore(String todScore) {
        this.todScore = todScore;
    }

    public String getTmrScore() {
        return tmrScore;
    }

    public void setTmrScore(String tmrScore) {
        this.tmrScore = tmrScore;
    }

    public boolean getSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
}
