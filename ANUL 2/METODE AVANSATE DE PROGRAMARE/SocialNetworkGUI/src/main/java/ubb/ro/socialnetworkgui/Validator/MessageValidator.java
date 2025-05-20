package ubb.ro.socialnetworkgui.Validator;

import ubb.ro.socialnetworkgui.Domain.Message;
import ubb.ro.socialnetworkgui.Exceptions.ValidationException;

import java.util.Objects;

public class MessageValidator implements AbstractValidator<Message> {
    @Override
    public void validate(Message message) {
        String errors = "";
        if (Objects.equals(message.getMessage(), ""))
            errors += "Empty message\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
