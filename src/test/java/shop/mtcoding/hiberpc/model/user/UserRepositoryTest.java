package shop.mtcoding.hiberpc.model.user;

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
import shop.mtcoding.hiberpc.model.board.MyRepository;

@DataJpaTest // 이녀석은 JpaRepository 를 구현한 인터페이스만 힙에 띄워준다.
@Import(MyRepository.class) // 따라서 EntityManager를 의존한 레파지토리는 임포트를 해줘야 한다.
public class UserRepositoryTest extends MyDummyEntity{
    
    @Autowired // 테스트는 이녀석을 이용
    private MyRepository<User, Long> myRepository;
    @Autowired
    private EntityManager em;
    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }


    @Test
    @Transactional
    public void save_test() throws Exception {
        // given
        User user = newUser("ssar");
    
        // when
        User userPS = myRepository.save(user);
    
        // then
        assertThat(userPS.getId()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void update_test() throws Exception {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = myRepository.save(user);
        // given2 - request 데이터 만들기
        String password = "5678";
        String email = "sar@nate.com";
        // when
        userPS.update(password, email);
        User updateUserPS = myRepository.save(userPS);
    
        // then
        assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }

    @Test
    @Transactional
    public void update_dutty_checking_test() throws Exception {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = myRepository.save(user);
        // given2 - request 데이터 만들기
        String password = "5678";
        String email = "sar@nate.com";

        // when 영속성 컨텍스트에 변경된 오브젝트 설정
        userPS.update(password, email);
        em.flush();
        
        // then
        User updateUserPS = myRepository.findById(User.class, 1L);
        assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }

    @Test
    @Transactional
    public void delete_test() throws Exception {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        myRepository.save(user);
        // given2 - request 데이터 만들기
        Long id = 1L;
        User findUserPS = myRepository.findById(User.class, id);

        // when
        myRepository.delete(findUserPS);    

        // then
        User deleteUserPS = myRepository.findById(User.class, id);
        assertThat(deleteUserPS).isNull();

    }

    @Test
    @Transactional
    public void findById_test() throws Exception {
        // given
        User user = newUser("ssar");
        User userPS = myRepository.save(user);
        Long id = 1L;
        
        // when
        User findUserPS = myRepository.findById(User.class, id);
    
        // then
        assertThat(findUserPS.getUsername()).isEqualTo("ssar");
    
    }

    @Test
    @Transactional
    public void findAll_test() throws Exception {
        // given
        List<User> userList = Arrays.asList(newUser("ssar"), newUser("cos"));
        userList.stream().forEach((user1)->{
            myRepository.save(user1);
        });
        // when
        List<User> userListPS = myRepository.findAll(User.class);
    
        // then
        assertThat(userListPS.size()).isEqualTo(2);
    }
}
