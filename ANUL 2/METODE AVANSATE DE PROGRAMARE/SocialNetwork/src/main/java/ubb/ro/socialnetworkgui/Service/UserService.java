package ubb.ro.socialnetworkgui.Service;

import java.util.ArrayList;
import java.util.Optional;

import ubb.ro.socialnetworkgui.Domain.Pair;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Exceptions.ExistingEntityException;
import ubb.ro.socialnetworkgui.Exceptions.InexistingEntityException;
import ubb.ro.socialnetworkgui.Repository.AbstractRepository;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.Repository.Paging.PagingRepository;
import ubb.ro.socialnetworkgui.Repository.UserDBRepository;
import ubb.ro.socialnetworkgui.UsefulTools.EntitiesThatChange;
import ubb.ro.socialnetworkgui.UsefulTools.TargetedWindow;

public class UserService extends AbstractService{
    private UserDBRepository userList;
    private Long id;

    public UserService(UserDBRepository userList) {
        super(new ArrayList<>());
        this.userList = userList;

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
        notifyAllObservers(new TargetedWindow(null, EntitiesThatChange.users));
    }

    /**
     * Remove a user from the list
     * @param id Long
     * @return removed User
     */
    public User removeUser(Long id){
        Optional<User> optionalUser = userList.remove(id);
        if (optionalUser.isPresent()) {
            notifyAllObservers(new TargetedWindow(null, EntitiesThatChange.users));
            notifyAllObservers(new TargetedWindow(null, EntitiesThatChange.friendships));
            return optionalUser.get();
        }
        throw new InexistingEntityException("Entity doesn't exist\n");
    }

    public User find(Long id){
        Optional<User> user = userList.find(id);
        if (user.isPresent())
            return user.get();
        throw new InexistingEntityException("Entity doesn't exist\n");
    }

    public void update(Long id, String firstname, String lastname, String password){
        User newUser = new User(firstname, lastname, password);
        newUser.setId(id);
        Optional<User> user = userList.update(newUser);
        if (user.isPresent())
            throw new InexistingEntityException("Entity doesn't exist\n");
        notifyAllObservers(new TargetedWindow(null, EntitiesThatChange.users));
    }

    public User findUserByFirstnameLastnamePassword(String firstname, String lastname, String password){
        Optional<User> user = userList.findUserByFirstnameLastnamePassword(firstname, lastname, password);
        if (user.isPresent())
            return user.get();
        else
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


    public Page<User> findAllOnPage(Pageable pageable){
        return userList.findAllOnPage(pageable);
    }
}
