package com.jar.service.system.apporder.service.application.api;

import com.jar.service.system.apporder.service.application.AppOrderApplicationService;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateResponse;
import com.jar.service.system.apporder.service.application.dto.delete.AppOrderDeleteCommand;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderQuery;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class AppOrderResource {


    private final AppOrderApplicationService appOrderApplicationService;

    @Autowired
    public AppOrderResource(AppOrderApplicationService appOrderApplicationService) {
        this.appOrderApplicationService = appOrderApplicationService;
    }

    @RequestMapping(value = "/apporders",method = RequestMethod.POST)
    public ResponseEntity<AppOrderCreateResponse> createAppOrder(
            @RequestBody AppOrderCreateCommand appOrderCreateCommand) {
        AppOrderCreateResponse appOrderCreateResponse = appOrderApplicationService
                .createAppOrder(appOrderCreateCommand);

        return ResponseEntity.ok().body(appOrderCreateResponse);
    }

    @RequestMapping(value = "/apporders/{apporderId}", method = RequestMethod.GET)
    public ResponseEntity<TrackAppOrderResponse> retrieveAppOrder(@PathVariable UUID apporderId) {

        TrackAppOrderQuery trackAppOrderQuery = TrackAppOrderQuery.builder().appOrderId(apporderId).build();
        TrackAppOrderResponse trackAppOrderResponse =
                appOrderApplicationService.TrackQueryAppOrder(trackAppOrderQuery);

        return ResponseEntity.ok().body(trackAppOrderResponse);
    }

    @RequestMapping(value = "/apporders/{apporderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAppOrder(@PathVariable UUID apporderId,
                                               @RequestBody AppOrderDeleteCommand appOrderDeleteCommand) {
        appOrderDeleteCommand.setAppOrderId(apporderId);
        log.info("appOrderDeleteCommand : {}" ,appOrderDeleteCommand.toString());
        appOrderApplicationService.deleteAppOrder(appOrderDeleteCommand);

        return ResponseEntity.noContent().build();
    }
}
