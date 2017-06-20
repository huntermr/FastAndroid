package demo.vo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/5/11.
 */
@Entity
public class SearchHistory {
    @Id(autoincrement = true)
    private Long id;

    private String keyword;

    @Generated(hash = 98012354)
    public SearchHistory(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
