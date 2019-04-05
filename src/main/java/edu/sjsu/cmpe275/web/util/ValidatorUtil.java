package edu.sjsu.cmpe275.web.util;

import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import org.springframework.util.StringUtils;

import java.util.Map;

public class ValidatorUtil {
    public static void validate(final Map<String, String> param, final String parameter) throws ConstraintViolationException {
        if (!param.containsKey(parameter) || StringUtils.isEmpty(param.get(parameter))) {
            throw new ConstraintViolationException();
        }
    }
}
