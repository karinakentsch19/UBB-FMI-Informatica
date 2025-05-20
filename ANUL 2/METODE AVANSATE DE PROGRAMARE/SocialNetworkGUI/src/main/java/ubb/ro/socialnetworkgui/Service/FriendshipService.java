package ubb.ro.socialnetworkgui.Service;

import ubb.ro.socialnetworkgui.DTO.FriendshipDTO;
import ubb.ro.socialnetworkgui.Domain.Friendship;
import ubb.ro.socialnetworkgui.Domain.Pair;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Repository.AbstractRepository;
import ubb.ro.socialnetworkgui.Repository.FriendshipDBRepository;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.UsefulTools.EntitiesThatChange;
import ubb.ro.socialnetworkgui.UsefulTools.TargetedWindow;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class FriendshipService extends AbstractService{
    private FriendshipDBRepository friendshipList;

    private AbstractRepository<Long, User> userList;
    private Map<User, Integer> discoveredTime = new HashMap<>();

    public FriendshipService(FriendshipDBRepository friendshipList, AbstractRepository<Long, User> userList) {
        super(new ArrayList<>());
        this.friendshipList = friendshipList;
        this.userList = userList;
    }

    /**
     * Get all friends of a given user
     * @param user User
     * @return list of friends
     */
    public Iterable<User> getAllFriendsForAUser(User user){
        List<User> users = new ArrayList<>();
        friendshipList.getAll().forEach(friendship -> {
            if (user.getId() == friendship.getUser1()){
                User friend = userList.find(friendship.getUser2()).get();
                users.add(friend);
            }
        });
        return users;
    }

    /**
     * Add a friendship between the users with the given ids
     *
     * @param id1 Long
     * @param id2 Long
     */
    public void addFriendship(Long id1, Long id2) {
        Optional<User> optionalUser1 = userList.find(id1);
        Optional<User> optionalUser2 = userList.find(id2);

        Predicate<Optional<User>> isEmpty = Optional::isEmpty;
        Predicate<Optional<User>> isNotEmpty = isEmpty.negate();

        if (isNotEmpty.test(optionalUser1) && isNotEmpty.test(optionalUser2)) {
            User user1 = optionalUser1.get();
            User user2 = optionalUser2.get();
            Friendship friendship = new Friendship(id1, id2);
            Friendship friendship1 = new Friendship(id2, id1);
            friendship.setId(new Pair<>(id1, id2));
            friendship1.setId(new Pair<>(id2, id1));
            friendshipList.add(friendship);
            friendshipList.add(friendship1);
        }
        notifyAllObservers(new TargetedWindow(id1, EntitiesThatChange.friendships));
        notifyAllObservers(new TargetedWindow(id2, EntitiesThatChange.friendships));
    }

    /**
     * Remove the friendship between the users with the given ids
     *
     * @param id1 Long
     * @param id2 Long
     * @return removed friendship
     */
    public void removeFriendship(Long id1, Long id2) {
        Optional<User> optionalUser1 = userList.find(id1);
        Optional<User> optionalUser2 = userList.find(id2);

        Predicate<Optional<User>> isEmpty = Optional::isEmpty;
        Predicate<Optional<User>> isNotEmpty = isEmpty.negate();

        if (isNotEmpty.test(optionalUser1) && isNotEmpty.test(optionalUser2)) {
            User user1 = optionalUser1.get();
            User user2 = optionalUser2.get();
            friendshipList.remove(new Pair<>(id1, id2));
            friendshipList.remove(new Pair<>(id2, id1));
        }
        notifyAllObservers(new TargetedWindow(id1, EntitiesThatChange.friendships));
        notifyAllObservers(new TargetedWindow(id2, EntitiesThatChange.friendships));
    }

    public void removeFriends(User user) {

        Iterable<User> friendList = getAllFriendsForAUser(user);
        friendList.forEach(friend -> {
            Pair<Long, Long> friendshipId = new Pair<>(user.getId(), friend.getId());
            Pair<Long, Long> friendshipId2 = new Pair<>(friend.getId(), user.getId());
            friendshipList.remove(friendshipId);
            friendshipList.remove(friendshipId2);
        });
    }

    /**
     * @return all friendships
     */
    public Iterable<Friendship> getAllFriendships() {
        return friendshipList.getAll();
    }

    /**
     * @return number of friendships
     */
    public Long size() {
        return friendshipList.size();
    }

    /**
     * This method recursively explores a user's friend network to identify and mark visited users in a depth-first manner. It ensures that the users are added to userList1 in the correct order.
     *
     * @param user        (User): The user whose friends are being visited.
     * @param visitedUser (Map<User, Integer>): A map that keeps track of visited users. Initially, all users have a value of 0, and after visiting, they are marked as 1 to prevent revisiting.
     * @param userList1   (List<User>): A list that collects the visited users in the order of their visit.
     */
    private void visit(User user, Map<User, Integer> visitedUser, List<User> userList1, Integer time) {
        Predicate<User> isNotVisited = user1 -> visitedUser.get(user1) == 0;

        if (isNotVisited.test(user)) {
            visitedUser.put(user, 1);
            discoveredTime.put(user, time);
//            for (User friend : user.getFriendList())
//                visit(friend, visitedUser, userList1, time + 1);
            Iterable<User> friendList = getAllFriendsForAUser(user);
            Consumer<User> visitUsersFriends = u -> visit(u, visitedUser, userList1, time + 1);
            friendList.forEach(visitUsersFriends);
            userList1.add(0, user);
        }
    }

    /**
     * This method assigns users to communities, with the condition that a user can only belong to one community. It recursively explores a user's friend network and assigns them to the community of the rootUser.
     *
     * @param user1     (User): The user to be assigned to a community.
     * @param rootUser  (User): The user who serves as the root of the community being formed.
     * @param community (Map<User, List<User>>): A map that associates each root user with a list of users in their community.
     */
    private void assign(User user1, User rootUser, Map<User, List<User>> community) {
        boolean contains = false;

        for (User userKey : community.keySet())
            if (community.get(userKey).contains(user1))
                contains = true;

        Consumer<User> assignToCommunity = user -> assign(user, rootUser, community);

        if (!contains) {
            if (!community.containsKey(rootUser))
                community.put(rootUser, new ArrayList<>());
            community.get(rootUser).add(user1);
//            for (User friend : user1.getFriendList())
//                assign(friend, rootUser, community);

            Iterable<User> friendList = getAllFriendsForAUser(user1);
            friendList.forEach(assignToCommunity);
        }
    }

    /**
     * This method orchestrates the process of identifying communities within a social network by iterating through all users and invoking the visit and assign methods.
     *
     * @return community (Map<User, List<User>>): A map where each user who serves as the root of a community is associated with a list of users in their respective community.
     */

    private Map<User, List<User>> communities() {
        int time = 0;
        Map<User, Integer> visitedUser = new HashMap<>();
        List<User> userList1 = new ArrayList<>();
        Map<User, List<User>> community = new HashMap<>();

        Consumer<User> visitUser = u -> visit(u, visitedUser, userList1, time);
        Consumer<User> assignUserToCommunity = user -> assign(user, user, community);

//        for (User user : userList.getAll())
//            visitedUser.put(user, 0);
//
//        for (User user : userList.getAll())
//            visit(user, visitedUser, userList1, time);
//
//        for (User user : userList1)
//            assign(user, user, community);

        Iterable<User> users = userList.getAll();
        users.forEach(user -> visitedUser.put(user,0));

        users.forEach(visitUser);
        userList1.forEach(assignUserToCommunity);

        return community;
    }

    /**
     * This method calculates the number of distinct communities in the social network
     *
     * @return (Long) number of communities
     */
    public Long numberOfCommunities() {
        Map<User, List<User>> community = communities();
        return Long.valueOf(community.size());
    }

    /**
     * This method identifies the most sociable community in the social network by examining the communities established in the communities method. It determines the most sociable community as the one with the largest number of users.
     *
     * @return List<User>: A list of users representing the most sociable community in the social network. This is the community with the highest number of users.
     */
    public List<User> mostSociableCommunity() {
        Map<User, List<User>> community = communities();
        List<User> mostSociableCom = new ArrayList<>();
        Integer maximumChainSize = Integer.MIN_VALUE;

        for (User user : community.keySet()) {
            List<User> userList1 = community.get(user);
            Integer min = Integer.MAX_VALUE;
            Integer max = Integer.MIN_VALUE;

            for (User user1 : userList1) {
                Integer time = discoveredTime.get(user1);
                if (time > max)
                    max = time;
                if (time < min)
                    min = time;
                if (max - min > maximumChainSize) {
                    maximumChainSize = max - min;
                    mostSociableCom = userList1;
                }
            }
        }

        return mostSociableCom;
    }

    public Iterable<String> filterFrienshipsByMonth(Long userId, Integer month){
        List<Friendship> friendships = (List<Friendship>) friendshipList.getAll();

        Predicate<Friendship> userFrienships = user -> Objects.equals(userId, user.getUser1());
        Predicate<Friendship> friendshipsStartedInAMonth = friendship -> month == friendship.getMeetingDate().getMonthValue();
        Function<Friendship, Pair<Long, LocalDateTime>> getFriendIdAndDate = friendship -> new Pair<>(friendship.getUser2(), friendship.getMeetingDate());
        Function<Pair<Long, LocalDateTime>, Pair<User, LocalDateTime>> getFriendAndDate = longLocalDateTimePair -> new Pair<>(userList.find(longLocalDateTimePair.getElem1()).get(), longLocalDateTimePair.getElem2());
        Function<Pair<User, LocalDateTime>, String> getFriendString = pair -> pair.getElem1().getFirstname() + " " + pair.getElem1().getLastname() + " | " + pair.getElem2().toString();

        return friendships.stream().filter(userFrienships)
                            .filter(friendshipsStartedInAMonth)
                            .map(getFriendIdAndDate)
                            .map(getFriendAndDate)
                            .map(getFriendString)
                            .toList();
    }

    public Page<FriendshipDTO> findAllOnPageforUser(Pageable pageable, Long userID){
        Page<Friendship> friendshipPage = friendshipList.findAllOnPageForUser(pageable, userID);
        List<Friendship> friendships = StreamSupport.stream(friendshipPage.getElementsOnPage().spliterator(), false).toList();


        Function<Long, String> fromIDtoFullName = id -> userList.find(id).get().getFirstname() + " " + userList.find(id).get().getLastname();
        Function<Friendship, FriendshipDTO> toFriendshipDTO = friendship -> new FriendshipDTO(friendship.getUser2(), friendship.getMeetingDate(), fromIDtoFullName.apply(friendship.getUser2()));
        List<FriendshipDTO> friendshipDTOList = friendships.stream().map(toFriendshipDTO).toList();

        return new Page<>(friendshipDTOList, friendshipPage.getTotalNumberOfElements());
    }
}
