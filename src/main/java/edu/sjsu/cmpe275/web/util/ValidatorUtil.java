package edu.sjsu.cmpe275.web.util;

import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ValidatorUtil {
    public static void validateParam(final Map<String, String> param, final String parameter) throws ConstraintViolationException {
        if (!param.containsKey(parameter) || StringUtils.isEmpty(param.get(parameter))) {
            throw new ConstraintViolationException(parameter);
        }
    }

    public static void validateParams(final Map<String, String> params, final List<String> parameters) throws ConstraintViolationException {
        for (final String parameter : parameters) {
            if (!params.containsKey(parameter) || StringUtils.isEmpty(params.get(parameter))) {
                throw new ConstraintViolationException(parameter);
            }
        }
    }

    public static void validateRestrictedParam(final Map<String, String> params, final List<String> restrictedParams) throws ConstraintViolationException {
        for (final String parameter : restrictedParams) {
            if (params.containsKey(parameter))
                throw new ConstraintViolationException();
        }

    }
}
