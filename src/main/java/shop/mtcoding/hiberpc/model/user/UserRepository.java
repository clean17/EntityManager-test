package shop.mtcoding.hiberpc.model.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public User findById(int id){
        return em.find(User.class, id);
    }
    public List<User> findAll(int id){
        return em.createQuery("select * from User u", User.class).getResultList();
    }

    @Transactional
    public User save(User user){
        // if(ObjectUtils.isEmpty(user.getId())){
        //     em.persist(user);
        // }else{
        //     em.merge(user);
        // }

        if (em.contains(user)) { // DB 에서 해당 user 객체가 있는지 영속상태를 확인해준다.
            em.merge(user);
        } else {
            em.persist(user);
        }
        // if( user.getId() == null){
        //     em.persist(user);
        // }else{
        //     em.merge(user);
        // }
        em.merge(user);
        return user;
    }
    
    @Transactional
    public void delete(User user){ // 삭제할때도 오브젝트를 넣어서 찾는다 !!
        em.remove(user);
    }

}
