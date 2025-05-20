package ubb.ro.socialnetworkgui.Service;

import ubb.ro.socialnetworkgui.DTO.FriendRequestsDTO;
import ubb.ro.socialnetworkgui.Domain.FriendRequest;
import ubb.ro.socialnetworkgui.Domain.Friendship;
import ubb.ro.socialnetworkgui.Domain.Pair;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Repository.AbstractRepository;
import ubb.ro.socialnetworkgui.Repository.FriendshipRequestDBRepository;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.UsefulTools.EntitiesThatChange;
import ubb.ro.socialnetworkgui.UsefulTools.TargetedWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class FriendRequestService extends AbstractService{
    private FriendshipRequestDBRepository friendRequests;

    private AbstractRepository<Pair<Long, Long>, Friendship> friendShips;

    private AbstractRepository<Long, User> userList;

    public FriendRequestService(FriendshipRequestDBRepository friendRequests, AbstractRepository<Pair<Long, Long>, Friendship> friendShips, AbstractRepository<Long, User> userList) {
        super(new ArrayList<>());
        this.friendRequests = friendRequests;
        this.friendShips = friendShips;
        this.userList = userList;
    }

    public void addFriendRequest(Long id1, Long id2, String status){
        FriendRequest friendRequest = new FriendRequest(id1, id2, status);
        friendRequest.setId(new Pair<>(id1,id2));
        Optional<FriendRequest> optionalFriendRequest = friendRequests.add(friendRequest);
        notifyAllObservers(new TargetedWindow(id2, EntitiesThatChange.friendRequests));
    }

    public void updateFriendRequest(Long id1, Long id2, String status){
        FriendRequest newFriendRequest = new FriendRequest(id1, id2, status);
        newFriendRequest.setId(new Pair<>(id1,id2));
        Optional<FriendRequest> optionalFriendRequest = friendRequests.update(newFriendRequest);
        notifyAllObservers(new TargetedWindow(id2, EntitiesThatChange.friendRequests));
    }

    public Iterable<FriendRequest> getAll(){
        return friendRequests.getAll();
    }

    public Long size(){
        return friendRequests.size();
    }

    public Page<FriendRequestsDTO> findAllOnPage(Pageable pageable, Long userID){
        Page<FriendRequest> friendRequestPage = friendRequests.getAllRequestForAUserWithGivenStatus(pageable, userID, "pending");

        List<FriendRequest> friendRequestList = StreamSupport.stream(friendRequestPage.getElementsOnPage().spliterator(), false).toList();

        Function<FriendRequest,FriendRequestsDTO> friendRequestsDTOFunction = friendRequest -> new FriendRequestsDTO(friendRequest.getIdUserFrom(), userList.find(friendRequest.getIdUserFrom()).get().getFirstname(), userList.find(friendRequest.getIdUserFrom()).get().getLastname());
        List<FriendRequestsDTO> friendRequestsDTOS = friendRequestList.stream().map(friendRequestsDTOFunction).toList();
        return new Page<>(friendRequestsDTOS, friendRequestPage.getTotalNumberOfElements());
    }

    public void delete(Long id1, Long id2){
        friendRequests.remove(new Pair<>(id1, id2));
        notifyAllObservers(new TargetedWindow(id2, EntitiesThatChange.friendRequests));
    }

    public void acceptAFriendRequest(Long idFrom, Long idTo){
        Friendship friendship = new Friendship(idFrom, idTo);
        Friendship friendship2 = new Friendship(idTo, idFrom);
        friendShips.add(friendship);
        friendShips.add(friendship2);

        FriendRequest newFriendRequest = new FriendRequest(idFrom, idTo, "approved");
        friendRequests.update(newFriendRequest);
        notifyAllObservers(new TargetedWindow(idTo, EntitiesThatChange.friendRequests));
        notifyAllObservers(new TargetedWindow(idTo, EntitiesThatChange.friendships));
        notifyAllObservers(new TargetedWindow(idFrom, EntitiesThatChange.friendships));
    }
    public void denyAFriendRequest(Long idFrom, Long idTo){
        FriendRequest newFriendRequest = new FriendRequest(idFrom, idTo, "rejected");
        delete(idFrom, idTo);
        notifyAllObservers(new TargetedWindow(idTo, EntitiesThatChange.friendRequests));
    }

    public Iterable<User> usersWhoAreNotMyFriends(Long userID){
        Predicate<User> notMe = user -> !Objects.equals(user.getId(), userID);
        Predicate<User> userIsNotMyFriend = user -> friendShips.find(new Pair<>(userID,user.getId())).isEmpty();
        Predicate<User> userIsNotFriendRequested = user -> friendRequests.find(new Pair<>(user.getId(), userID)).isEmpty();


        List<User> users = StreamSupport.stream(userList.getAll().spliterator(), false).toList();
        return users.stream().filter(notMe).filter(userIsNotMyFriend).filter(userIsNotFriendRequested).toList();
    }
}
