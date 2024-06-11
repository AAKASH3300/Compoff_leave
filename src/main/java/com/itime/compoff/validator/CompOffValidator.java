//package com.itime.compoff.validator;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.itime.compoff.exception.CommonException;
//import com.itime.compoff.exception.ErrorMessages;
//import com.itime.compoff.model.CompOffApplyRequest;
//import com.itime.compoff.utils.AppConstants;
//import com.itime.compoff.utils.ResourcesUtils;
//import jakarta.annotation.PostConstruct;
//import net.sf.jsqlparser.util.validation.ValidationError;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class CompOffValidator {
//    private static final Logger log = LoggerFactory.getLogger(CompOffValidator.class);
//
//    private RequestValidationConfig requestValidationConfig;
//
//    @Autowired
//    ApplicationContext applicationContext;
//
//    @PostConstruct
//    public void init() throws IOException {
//        Resource resource = applicationContext.getResource("classpath:support\\json\\compOff.json");
//        requestValidationConfig = new ObjectMapper().readValue(ResourcesUtils.getResourceContent(resource),
//                RequestValidationConfig.class);
//    }
//
//    public void validateSaveCompOff(CompOffApplyRequest compOffApplyRequest) throws CommonException {
//        Map<String, String> actualParameters = new HashMap<>();
//
//
//        actualParameters.put(AppConstants.EMPLOYEE_ID, String.valueOf(compOffApplyRequest.getEmployeeId()));
//        actualParameters.put(AppConstants.REQUESTED_DATE, compOffApplyRequest.getRequestedDate());
//        actualParameters.put(AppConstants.PUNCH_IN_PARAM, compOffApplyRequest.getPunchIn());
//        actualParameters.put(AppConstants.PUNCH_OUT_PARAM, compOffApplyRequest.getPunchOut());
//        actualParameters.put(AppConstants.WORK_HOURS_PARAM, compOffApplyRequest.getWorkHours());
//        actualParameters.put(AppConstants.REQUESTED_FOR, compOffApplyRequest.getRequestedFor());
//        actualParameters.put(AppConstants.REASON, compOffApplyRequest.getReason());
//
//        log.trace("validate mandatory field for create copOff transaction api for headers fields");
//        Set<String> mandatoryHeader = requestValidationConfig.getCreateCompOff().getHeader().stream()
//                .filter(RequestComponent::getRequired).map(RequestComponent::getName).collect(Collectors.toSet());
//        ValidationError validationError = CommonRequestValidator.validateMandatoryFields(actualParameters,
//                mandatoryHeader);
//        if (validationError != null) {
//            log.warn(ErrorMessages.MSG_VALIDATION, validationError);
//            throw ApplicationErrorCode.MISSING_MANDATORY_FIELD.getError().reqValidationError(validationError.field,
//                    HttpStatus.BAD_REQUEST.value());
//        }
//
//        log.trace("validate field size for create copOff transaction transaction api for headers fields");
//        Map<String, Integer> fieldSizeMapHeader = requestValidationConfig.getCreateCompOff().getHeader().stream()
//                .collect(Collectors.toMap(RequestComponent::getName, RequestComponent::getMaxLength));
//        validationError = CommonRequestValidator.validateFieldSize(actualParameters, fieldSizeMapHeader);
//        if (validationError != null) {
//            log.warn(ErrorMessages.MSG_VALIDATION, validationError);
//            throw ApplicationErrorCode.INVALID_FIELD_SIZE.getError().reqValidationError(validationError.field,
//                    HttpStatus.BAD_REQUEST.value());
//        }
//
//        log.trace("validate field format for create copOff transaction headers fields");
//        Map<String, String> formatMapHeader = requestValidationConfig.getCreateCompOff().getHeader().stream()
//                .filter(a -> a.getFormat() != null)
//                .collect(Collectors.toMap(RequestComponent::getName, RequestComponent::getFormat));
//        validationError = CommonRequestValidator.validateFieldValueFormat(actualParameters, formatMapHeader);
//        if (validationError != null) {
//            log.warn(ErrorMessages.MSG_VALIDATION, validationError);
//            throw ApplicationErrorCode.INVALID_INPUT_FORMAT.getError().reqValidationError(validationError.field,
//                    HttpStatus.BAD_REQUEST.value());
//        }
//
//        log.trace("validate mandatory field for create copOff transaction api body");
//        Set<String> mandatoryQueryParam = requestValidationConfig.getCreateCompOff().getBody().stream()
//                .filter(RequestComponent::getRequired).map(RequestComponent::getName).collect(Collectors.toSet());
//        validationError = CommonRequestValidator.validateMandatoryFields(actualParameters,
//                mandatoryQueryParam);
//        if (validationError != null) {
//            log.warn(ErrorMessages.MSG_VALIDATION, validationError);
//            throw ApplicationErrorCode.MISSING_MANDATORY_FIELD.getError().reqValidationError(validationError.field,
//                    HttpStatus.BAD_REQUEST.value());
//        }
//
//        log.trace("validate field size for create copOff transaction api body");
//        Map<String, Integer> fieldSizeMapQueryParam = requestValidationConfig.getCreateCompOff().getBody().stream()
//                .collect(Collectors.toMap(RequestComponent::getName, RequestComponent::getMaxLength));
//        validationError = CommonRequestValidator.validateFieldSize(actualParameters, fieldSizeMapQueryParam);
//        if (validationError != null) {
//            log.warn(ErrorMessages.MSG_VALIDATION, validationError);
//            throw ApplicationErrorCode.INVALID_FIELD_SIZE.getError().reqValidationError(validationError.field,
//                    HttpStatus.BAD_REQUEST.value());
//        }
//
//        log.trace("validate field format for create copOff transaction api body");
//        Map<String, String> formatMapQueryParam = requestValidationConfig.getCreateCompOff().getBody().stream()
//                .filter(a -> a.getFormat() != null)
//                .collect(Collectors.toMap(RequestComponent::getName, RequestComponent::getFormat));
//        validationError = CommonRequestValidator.validateFieldValueFormat(actualParameters, formatMapQueryParam);
//        if (validationError != null) {
//            log.warn(ErrorMessages.MSG_VALIDATION, validationError);
//            throw ApplicationErrorCode.INVALID_INPUT_FORMAT.getError().reqValidationError(validationError.field,
//                    HttpStatus.BAD_REQUEST.value());
//        }
//        this.otherCheck(compOffApplyRequest);
//
//        compOffApplyRequest.setWorkHours(compOffApplyRequest.getWorkHours().strip());
//    }
//}
