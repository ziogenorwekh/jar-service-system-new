package com.jar.service.system.apporder.service.application.api;

import com.jar.service.system.apporder.service.application.AppOrderApplicationService;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateResponse;
import com.jar.service.system.apporder.service.application.dto.delete.AppOrderDeleteCommand;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderBriefResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderQuery;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackUserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * Register User's Application Information
     * @param userId
     * @param appOrderCreateCommand
     * @return
     */
    @RequestMapping(value = "/apporders", method = RequestMethod.POST)
    public ResponseEntity<AppOrderCreateResponse> createAppOrder(@RequestHeader("userId") UUID userId,
                                                                 @RequestBody AppOrderCreateCommand appOrderCreateCommand) {
        log.info("[CREATE] create AppOrder");
        appOrderCreateCommand.setUserId(userId);
        AppOrderCreateResponse appOrderCreateResponse = appOrderApplicationService
                .createAppOrder(appOrderCreateCommand);

        return ResponseEntity.ok().body(appOrderCreateResponse);
    }

    /**
     * Track User's Application Details
     * @param apporderId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/apporders/{apporderId}", method = RequestMethod.GET)
    public ResponseEntity<TrackAppOrderResponse> retrieveAppOrder(@PathVariable UUID apporderId,
                                                                  @RequestHeader("userId") UUID userId) {
        log.trace("[TRACE] view id : {} AppOrder", apporderId);
        TrackAppOrderQuery trackAppOrderQuery = TrackAppOrderQuery.builder()
                .userId(userId)
                .appOrderId(apporderId).build();
        TrackAppOrderResponse trackAppOrderResponse =
                appOrderApplicationService.TrackQueryAppOrder(trackAppOrderQuery);
        log.info("here is tracking -> ");
        log.info("{}", trackAppOrderResponse.toString());

        return ResponseEntity.ok().body(trackAppOrderResponse);
    }

    /**
     * Track User's All Application.
     * @param userId
     * @return
     */
    @RequestMapping(value = "/apporders/all", method = RequestMethod.GET)
    public ResponseEntity<List<TrackAppOrderBriefResponse>> retrieveAllAppOrders(
            @RequestHeader("userId") UUID userId) {
        log.info("[TRACE] view all AppOrders");
        List<TrackAppOrderBriefResponse> appOrders = appOrderApplicationService
                .findAllAppOrders(TrackUserQuery.builder().userId(userId).build());

        return ResponseEntity.ok().body(appOrders);
    }

    /**
     * Delete User's Application
     * @param apporderId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/apporders/{apporderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAppOrder(@PathVariable UUID apporderId,
                                               @RequestHeader("userId") UUID userId) {
        log.info("[DELETE] delete {} AppOrder", apporderId);
        AppOrderDeleteCommand appOrderDeleteCommand = AppOrderDeleteCommand.builder()
                .appOrderId(apporderId).userId(userId).build();
        log.info("appOrderDeleteCommand : {}", appOrderDeleteCommand.toString());
        appOrderApplicationService.deleteAppOrder(appOrderDeleteCommand);

        return ResponseEntity.noContent().build();
    }
}
