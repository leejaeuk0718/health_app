package healthApp_backend.validation;

import org.springframework.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

@Slf4j
public abstract class AbstractValidator<T> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors);
        } catch (IllegalStateException e) {
            log.error("중복검증 에러", e);

            throw e;
        }
    }

    protected abstract void doValidate(T dto, Errors errors);
}
