package com.example.libertyformapiserver.filter;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

// Swagger local, remote 설정을 위한 스웨거 필터 클래스
@Component
public class WorkaroundFilter implements WebMvcOpenApiTransformationFilter {

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openAPI = context.getSpecification();

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8080");

        Server remoteServer = new Server();
        remoteServer.setDescription("remote");
        remoteServer.setUrl("https://libertyform.shop");
        openAPI.setServers(Arrays.asList(localServer, remoteServer));
        return openAPI;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return delimiter.equals(DocumentationType.OAS_30);
    }
}
