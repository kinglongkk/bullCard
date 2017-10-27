package g.web.api.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by tony on 2016/11/7.
 */

@Documented
@Constraint(validatedBy = ApiLegalValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLegal {
    String message() default "非法请求";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
