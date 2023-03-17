package shop.mtcoding.hiberpc.model.board;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import shop.mtcoding.hiberpc.config.dummy.MyDummyEntity;
import shop.mtcoding.hiberpc.model.user.User;

@DataJpaTest // 이녀석은 JpaRepository 를 구현한 인터페이스만 힙에 띄워준다.
@Import(MyRepository.class) // 따라서 EntityManager를 의존한 레파지토리는 임포트를 해줘야 한다.
public class BoardRepositoryTest extends MyDummyEntity{
    
    @Autowired 
    private MyRepository<Board, Long> boardRepository;
    @Autowired 
    private MyRepository<User, Long> userRepository;
    @Autowired
    private EntityManager em;
    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }


    @Test
    @Transactional
    public void save_test() throws Exception {
        // given
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
    
        // given 2
        Board board = newBoard("제목1", userPS);
        
        // when
        Board boardPS = boardRepository.save(board);
        System.out.println("테스트 : "+boardPS);
    
        // then
        assertThat(boardPS.getId()).isEqualTo(1);
        assertThat(boardPS.getUser().getId()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void update_test() throws Exception {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        Board board = newBoard("제목1", userPS);
        Board boardPS = boardRepository.save(board);

        // given2 - request 데이터 만들기
        String title = "제목2";
        String content = "내용2";

        // when
        boardPS.update(title, content);
        em.flush();

        // then
        Board findBoardPS = boardRepository.findById(Board.class, 1L);
        assertThat(findBoardPS.getContent()).isEqualTo("내용2");
    }

    @Test
    @Transactional
    public void delete_test() throws Exception {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        Board board = newBoard("제목1", userPS);
        Board boardPS = boardRepository.save(board);
        em.clear(); // PC 를 비우니까 다음줄에서 다시 select해서 채워놓음
        // lazy 전략을 이용하면 개발자가 판단해서 페이지에 필요한 데이터가 필요할때도 잇고 필요하지 않을때도 잇으면 lazy 로 설정해서 부하를 줄일 수 있다.
        // given2 - request 데이터 만들기
        Long id = 1L;
        Board findBoardPS = boardRepository.findById(Board.class, id);

        // when
        boardRepository.delete(findBoardPS);

        // then
        Board deleteBoardPS = boardRepository.findById(Board.class, id);
        assertThat(deleteBoardPS).isNull();
        
        // PC 는 clear() 를 하고 toString() 하면 PC 에 객체가 없더라도 해당 toString()이 모든 필드를 출력하려고 할때 해당 객체가 (user)없다면 PC는 자동적으로 DB에서 데이터를 가져온다.
        // lazy 로딩을 사용할때 tostring을 조심해야하는 이유 검색해볼래 ?
    }

    @Test
    @Transactional
    public void findById_test() throws Exception {
        // given
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        Board board = newBoard("제목1", userPS);
        Board boardPS = boardRepository.save(board);
        Long id = 1L;
        
        // when
        Board findBoardPS = boardRepository.findById(Board.class, id);
    
        // then
        assertThat(findBoardPS.getUser().getUsername()).isEqualTo("ssar");
        assertThat(findBoardPS.getTitle()).isEqualTo("제목1");
    
    }

    @Test
    @Transactional
    public void findAll_test() throws Exception {
        // given
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        List<Board> boardList = Arrays.asList(newBoard("제목", userPS), newBoard("제목2", userPS));
        boardList.stream().forEach((board2)->{
            boardRepository.save(board2);
        });
        // when
        List<Board> userListPS = boardRepository.findAll(Board.class);
    
        // then
        assertThat(userListPS.size()).isEqualTo(2);
    
    }
}
