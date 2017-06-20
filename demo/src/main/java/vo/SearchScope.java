package demo.vo;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SearchScope {
    private String name;
    private String distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public SearchScope(String name, String distance) {
        this.name = name;
        this.distance = distance;
    }

    public SearchScope(String name) {
        this.name = name;
    }
}
