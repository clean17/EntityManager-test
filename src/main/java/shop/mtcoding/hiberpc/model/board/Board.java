package shop.mtcoding.hiberpc.model.board;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.hiberpc.model.user.User;

@Setter @Getter
@NoArgsConstructor
@Entity
@Table(name="board_tb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne // FK 키 설정을 자동적으로 해준다
    // @ManyToOne(fetch = FetchType.LAZY) // lazy 전략 - 필요할때만 사용
    private User user; // hibernate 를 이용하면 오브젝트를 넣어주면 FK로 인식한다.
    private String title;
    private String content;
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Board(Long id, User user, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Board [id=" + id + ", user=" + user + ", title=" + title + ", content=" + content + ", createdAt="
                + createdAt + "]";
    }

}
