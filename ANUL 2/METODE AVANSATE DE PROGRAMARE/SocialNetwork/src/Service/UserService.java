package Service;

import Domain.Friendship;
import Domain.Pair;
import Domain.User;
import Exceptions.ExistingEntityException;
import Exceptions.InexistingEntityException;
import Repository.AbstractRepository;

import java.util.Optional;
import java.util.function.Predicate;

public class UserService {
    private AbstractRepository<Long, User> userList;
//    private AbstractRepository<Pair<Long,Long>, Friendship> friendshipList;
    private Long id;

    public UserService(AbstractRepository<Long, User> userList/*, AbstractRepository<Pair<Long,Long>, Friendship> friendshipList*/) {
        this.userList = userList;
//        this.friendshipList = friendshipList;

         Long max = -1L;
        for (User user: userList.getAll())
            if (user.getId() > max)
                max = user.getId();

        this.id = max + 1;
    }

    /**
     * Add a user to the list
     * @param name String
     * @param surname String
     * @param password String
     */
    public void addUser(String name, String surname, String password){
        User user = new User(name, surname, password);
        user.setId(id);
        Optional<User> optionalUser = userList.add(user);
        id++;

        if (optionalUser.isPresent())
            throw new ExistingEntityException("Entity exists already\n");

    }

    /**
     * Remove a user from the list
     * @param id Long
     * @return removed User
     */
    public User removeUser(Long id){
        Optional<User> optionalUser = userList.remove(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        throw new InexistingEntityException("Entity doesn't exist\n");
    }

    public User find(Long id){
        Optional<User> user = userList.find(id);
        if (user.isPresent())
            return user.get();
        throw new InexistingEntityException("Entity doesn't exist\n");
    }

    /**
     *
     * @return all the users
     */
    public Iterable<User> getAllUsers(){
        return userList.getAll();
    }

    /**
     *
     * @return number of users
     */
    public Long size (){
        return userList.size();
    }

}
