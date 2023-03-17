package shop.mtcoding.hiberpc.model.board;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MyRepository<T, D> {
    private final EntityManager em;

    public T findById(Class<T> clazz, D d){
        return em.find(clazz, d);
    }

    public List<T> findAll(Class<T> clazz){
        return em.createQuery("select u from "+clazz.getSimpleName()+" u", clazz).getResultList();
    }

    public T save(T entity){
        // if(ObjectUtils.isEmpty(entity.getId())){
        //     em.persist(entity);
        // }else{
        //     em.merge(entity);
        // }
        if (em.contains(entity)) { // DB 에서 해당 entity 객체가 있는지 영속상태를 확인해준다.
            em.merge(entity);
        } else {
            em.persist(entity);
        }
        // if( entity.getId() == null){
        //     em.persist(entity);
        // }else{
        //     em.merge(entity);
        // }
        em.merge(entity);
        return entity;
    }
    public void delete(T entity){ // 삭제할때도 오브젝트를 넣어서 찾는다 !!
        em.remove(entity);
    }
}
