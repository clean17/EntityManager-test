package shop.mtcoding.hiberpc.config.dummy;

import java.sql.Timestamp;

import shop.mtcoding.hiberpc.model.board.Board;
import shop.mtcoding.hiberpc.model.user.User;

public class MyDummyEntity {
    
    protected User newUser(String username){
        return User.builder()
                .username(username)
                .password("1234")
                .email("ssar@nate.com")
                .build();
    }

    // protected User newMockUser(String username){
    //     return User.builder()
    //             .id(1L)
    //             .username(username)
    //             .password("1234")
    //             .email("ssar@nate.com")
    //             .build();
    // }

    protected Board newBoard(String title, User userPS){
        if(userPS.getId() == null){
            System.out.println("영속화 필요");
            return null;
        }
        return Board.builder()
                .title(title)
                .content("1234")
                .user(userPS)
                .build();
    }
}
