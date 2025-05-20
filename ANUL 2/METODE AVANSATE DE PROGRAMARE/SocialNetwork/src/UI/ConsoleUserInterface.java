package UI;

import Domain.User;
import Exceptions.ExistingEntityException;
import Exceptions.InexistingEntityException;
import Exceptions.ValidationException;
import Service.FriendshipService;
import Service.UserService;

import java.time.Month;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class ConsoleUserInterface implements AbstractUserInterface {
    private UserService userService;
    private FriendshipService friendshipService;

    public ConsoleUserInterface(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    /**
     * This method starts and controls the user interface, displaying a menu of options and handling user input to execute the selected functionality.
     */
    @Override
    public void runUI() {
        showOptions();
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Give option: ");
            int optionNumber = in.nextInt();
            switch (optionNumber) {
                case 1:
                    showOptions();
                    break;
                case 2:
                    addUserUI();
                    break;
                case 3:
                    removeUserUI();
                    break;
                case 4:
                    addFriendshipUI();
                    break;
                case 5:
                    removeFriendshipUI();
                    break;
                case 6:
                    numberOfCommunitiesUI();
                    break;
                case 7:
                    mostSociableCommunityUI();
                    break;
                case 8:
                    getAllUsersUI();
                    break;
                case 9:
                    getAllFriendshipsUI();
                    break;
                case 10:
                    filterFriendshipsByMonthUI();
                    break;
                case 11:
                    return;
            }
        }
    }

    /**
     * This private method displays the available menu options to the user.
     */
    private void showOptions() {
        System.out.println("MENU");
        System.out.println("1  - Show menu options");
        System.out.println("2  - Add a user");
        System.out.println("3  - Remove a user");
        System.out.println("4  - Add a friendship");
        System.out.println("5  - Remove a friendship");
        System.out.println("6  - Show the number of communities");
        System.out.println("7  - Show the members of the most sociable community");
        System.out.println("8  - Show all users");
        System.out.println("9  - Show all friendships");
        System.out.println("10 - Show all friends of a user whom they met in a given month");
        System.out.println("11 - Exit");
        System.out.println();
    }

    /**
     * This private method handles the user interface for adding a new user. It prompts the user for user details (name, surname, password) and invokes the addUser method from the UserService to add a new user.
     */
    private void addUserUI() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = in.nextLine();
        System.out.println("Enter surname:");
        String surname = in.nextLine();
        System.out.println("Enter password:");
        String password = in.nextLine();

        try {
            userService.addUser(name, surname, password);
            System.out.println("User added successfully");
        } catch (ValidationException | IllegalArgumentException| ExistingEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This private method handles the user interface for removing a user. It prompts the user for the ID of the user to be removed and invokes the removeUser method from the UserService to delete the user.
     */
    private void removeUserUI() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter id: ");
        Long id = in.nextLong();

        try {
            User user = userService.find(id);
            friendshipService.removeFriends(user);
            userService.removeUser(id);
            System.out.println("User deleted successfully");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This private method handles the user interface for adding a friendship. It prompts the user for the IDs of two users and invokes the addFriendship method from the FriendshipService to create a friendship between them.
     */
    private void addFriendshipUI() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first id: ");
        Long id1 = in.nextLong();
        System.out.println("Enter second id: ");
        Long id2 = in.nextLong();

        try {
            friendshipService.addFriendship(id1, id2);
            System.out.println("Friendship added successfully");
        } catch (ValidationException | IllegalArgumentException |
                 InexistingEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This private method handles the user interface for removing a friendship. It prompts the user for the IDs of two users and invokes the removeFriendship method from the FriendshipService to delete a friendship between them.
     */
    private void removeFriendshipUI() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first id: ");
        Long id1 = in.nextLong();
        System.out.println("Enter second id: ");
        Long id2 = in.nextLong();

        try {
            friendshipService.removeFriendship(id1, id2);
            System.out.println("Friendship deleted successfully");
        } catch (ValidationException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This private method handles the user interface for displaying the number of communities in the social network. It invokes the numberOfCommunities method from the FriendshipService and provides appropriate output to the user.
     */
    private void numberOfCommunitiesUI() {
        Long numberOfCommunities = friendshipService.numberOfCommunities();
        if (numberOfCommunities != 1)
            System.out.println("There are " + numberOfCommunities + " communities");
        else
            System.out.println("There is " + numberOfCommunities + " community");
    }

    /**
     * This private method handles the user interface for displaying the most sociable community. It invokes the mostSociableCommunity method from the FriendshipService and displays the members of the most sociable community.
     */
    private void mostSociableCommunityUI() {
        List<User> mostSociableCommunity = friendshipService.mostSociableCommunity();
//        for (User user : mostSociableCommunity)
//            System.out.println(user);

        mostSociableCommunity.forEach(System.out::println);
    }

    /**
     * This private method handles the user interface for displaying all users in the social network.
     */
    private void getAllUsersUI() {
        Predicate<Long> noUsers = size -> size == 0;
        if (noUsers.test(userService.size()))
            System.out.println("There are no users!");
        else {
            userService.getAllUsers().forEach(System.out::println);
        }
    }

    /**
     * This private method handles the user interface for displaying all friendships in the social network
     */
    private void getAllFriendshipsUI() {
        if (friendshipService.size() == 0)
            System.out.println("There are no friendships!");
        else
            friendshipService.getAllFriendships().forEach(System.out::println);
    }

    /**
     * Show all friends of a user whom they met in a given month
     */
    private void filterFriendshipsByMonthUI(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter user id: ");
        Long id = in.nextLong();
        System.out.println("Enter month: ");
        Integer month = in.nextInt();
        Iterable<String> friends = friendshipService.filterFrienshipsByMonth(id, month);
        friends.forEach(System.out::println);
    }
}
