package cn.org.upthink.model.logger;

import cn.org.upthink.converter.String2MapConverter;
import cn.org.upthink.util.UserContext;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Getter@Setter@Builder@ToString
public class RequestLogger {

    @JsonIgnore
    private final HttpServletRequest request = UserContext.getRequest();

    @JsonProperty("url")
    private String url = request.getRequestURL().toString();

    @JsonProperty("method")
    private String method = request.getMethod();

    @JsonProperty("params_map")
    @JsonSerialize(using = String2MapConverter.class)
    private Map<String, Object> paramsMap;//todo  //= fetParamsMap(request);

    @JsonProperty("headers")
    @JsonSerialize(using = String2MapConverter.class)
    private Map<String, Object> headers;//todo // = fetchHttpHeaders(request);

    @JsonProperty("api_desc")
    private String apiDesc;

    @JsonProperty("request_body")
    @JsonSerialize(using = String2MapConverter.class)
    private String requestBody = (String) UserContext.getRequest().getAttribute(RequestLoggerAttribute.REQUEST_BODY_ID);

    @JsonProperty("request_time")
    @JsonFormat(pattern = "yyyy-MM-dd hh:ss:mm")
    private Date requestTime = new Date();

    @JsonProperty("response_time")
    @JsonFormat(pattern = "yyyy-MM-dd hh:ss:mm")
    private Date responseTime;

    @JsonProperty("character_encoding")
    private String characterEncoding = request.getCharacterEncoding();

    @JsonProperty("content_length")
    private long contentLength = request.getContentLengthLong();

    @JsonProperty("remote_host")
    private String remoteHost = request.getRemoteHost();

    @JsonProperty("remote_port")
    private int remotePort = request.getRemotePort();
}
