package Validator;

import Domain.Entity;

public interface AbstractValidator<E> {
    public void validate(E entity);
}
