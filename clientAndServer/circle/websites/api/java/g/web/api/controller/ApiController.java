package g.web.api.controller;

import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.Params;
import g.model.api.result.ListResults;
import g.model.api.result.Results;
import g.web.api.exception.ApiValidateException;
import g.web.api.model.ApiListResponse;
import g.web.api.model.ApiResponse;
import org.springframework.validation.BindingResult;

/**
 * Created by tony on 2016/11/10.
 */
public abstract class ApiController{

    protected void throwLegalException(String error){

        throw new ApiValidateException(ResultStatusEnums.VALIDATE_PARAMS_FAILED.getCode(),ResultStatusEnums.VALIDATE_PARAMS_FAILED.getMsg(),error);
    }

    protected String getBindingResultErrorMsg(BindingResult bindingResult){

        return bindingResult.getAllErrors().get(0).getDefaultMessage();
    }

    protected ApiResponse responseResults(Params params, Results results){

        ApiResponse response = new ApiResponse();
        response.setCode(ResultStatusEnums.SUCCESS.getCode());
        response.setCodeDesc(ResultStatusEnums.SUCCESS.getMsg());
        response.setResult(results.getFinalResults());
        response.setAttachParams(params.getAttachParams());
        response.setAdditionalResult(results.getAdditionalResult());
        return response;
    }

    protected ApiResponse responseListResults(Params params,ListResults results){

        ApiListResponse response = new ApiListResponse();
        response.setCode(ResultStatusEnums.SUCCESS.getCode());
        response.setCodeDesc(ResultStatusEnums.SUCCESS.getMsg());
        response.setPageNo(results.getPageNo());
        response.setPageSize(results.getPageSize());
        response.setPageTotalSize(results.getPageTotalSize());
        response.setResult(results.getFinalResults());
        response.setAttachParams(params.getAttachParams());
        response.setAdditionalResult(results.getAdditionalResult());
        return response;
    }

}
