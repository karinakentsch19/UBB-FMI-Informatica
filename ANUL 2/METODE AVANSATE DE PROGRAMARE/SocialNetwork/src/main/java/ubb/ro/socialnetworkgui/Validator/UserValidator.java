package ubb.ro.socialnetworkgui.Validator;

import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Exceptions.ValidationException;

public class UserValidator implements AbstractValidator<User>{

    @Override
    public void validate(User user) {
        String errors = "";
        if (user.getFirstname().isEmpty())
            errors += "Invalid name\n";

        if (user.getLastname().isEmpty())
            errors += "Invalid surname\n";

        if (user.getPassword().isEmpty())
            errors += "Invalid password\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
