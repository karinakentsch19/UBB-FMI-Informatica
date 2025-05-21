package ubb.ro.socialnetworkgui.Validator;

import ubb.ro.socialnetworkgui.Domain.Friendship;
import ubb.ro.socialnetworkgui.Exceptions.ValidationException;

public class FriendshipValidator implements AbstractValidator<Friendship>{

    @Override
    public void validate(Friendship friendship) {
        if (friendship.getMeetingDate() == null)
            throw new ValidationException("Invalid Meeting Date\n");
    }
}
