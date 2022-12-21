package com.dag.customer.model;

/**
 * 
 * The effort to standardize rest API error reports is support by ITEF (Internet
 * Engineering Task Force, open standard organization that which develop and
 * promotes voluntary internet standards) in RFC 7807 which created a
 * generalized error-handling schema composed by five parts.
 * 1-type — A URI identifier that categorizes the error
 * 2-title — A brief, human-readable message about the error
 * 3-code — The unique error code
 * 4-detail — A human-readable explanation of the error
 * 5-instance — A URI that identifies the specific occurrence of the error
 * Standarized is optional but have advantage, it is use for facebook and
 * twitter
 * ie https://graph.facebook.com/oauth/access_token?
 * https://api.twitter.com/1.1/statuses/update.json?include_entities=true
 */
public class ApiExceptionResponse extends Exception {

    // @ApiModelProperty(notes = "The unique uri identifier that categorizes the
    // error", name = "type", required = true, example =
    // "/errors/authentication/not-authorized")
    private final String type = "/errors/uncategorized";

    // @ApiModelProperty(notes = "A brief, human-readable message about the error",
    // name = "title", required = true, example = "The user does not have
    // authorization")
    private final String title;

    // @ApiModelProperty(notes = "The unique error code", name = "code", required =
    // false, example = "192")
    private final String code;

    // @ApiModelProperty(notes = "A human-readable explanation of the error", name =
    // "detail", required = true, example = "The user does not have the property
    // permissions to access the "
    // + "resource, please contact with ass
    // https://digitalthinking.biz/es/ada-enterprise-core#contactus")
    private final String detail;

    // @ApiModelProperty(notes = "A URI that identifies the specific occurrence of
    // the error", name = "detail", required = true, example =
    // "/errors/authentication/not-authorized/01")
    private final String instance = "/errors/uncategorized/bank";

    public ApiExceptionResponse(
            String title, String code, String detail) {
        this.title = title;
        this.code = code;
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

    public String getInstance() {
        return instance;
    }

}
