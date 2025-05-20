package Validator;

import Domain.Friendship;
import Exceptions.ValidationException;

public class FriendshipValidator implements AbstractValidator<Friendship>{

    @Override
    public void validate(Friendship friendship) {
        if (friendship.getMeetingDate() == null)
            throw new ValidationException("Invalid Meeting Date\n");
    }
}
