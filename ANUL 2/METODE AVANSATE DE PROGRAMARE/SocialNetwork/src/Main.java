import Domain.Friendship;
import Domain.Pair;
import Domain.User;
import Repository.FriendshipDBRepository;
import Repository.InMemoryRepository;
import Repository.UserDBRepository;
import Service.FriendshipService;
import Service.UserService;
import UI.ConsoleUserInterface;
import Validator.FriendshipValidator;
import Validator.UserValidator;

import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws IOException {

        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserDBRepository userDBRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "karina19", userValidator);
        FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "karina19");
        UserService userService = new UserService(userDBRepository);
        FriendshipService friendshipService = new FriendshipService(friendshipDBRepository, userDBRepository);
        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface(userService, friendshipService);
        consoleUserInterface.runUI();
    }
}