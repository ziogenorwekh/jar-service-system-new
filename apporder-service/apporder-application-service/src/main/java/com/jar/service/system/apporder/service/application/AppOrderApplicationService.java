package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.delete.AppOrderDeleteCommand;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderCurtResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderQuery;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderResponse;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackUserQuery;
import org.springframework.validation.annotation.Validated;

import java.util.List;


public interface AppOrderApplicationService {

    /**
     * users's create application information
     * @param appOrderCreateCommand
     * @return
     */
    AppOrderCreateResponse createAppOrder(@Validated AppOrderCreateCommand appOrderCreateCommand);

    /**
     * user's application default information
     * @param trackAppOrderQuery
     * @return
     */
    TrackAppOrderResponse TrackQueryAppOrder(@Validated TrackAppOrderQuery trackAppOrderQuery);

    void deleteAppOrder(@Validated AppOrderDeleteCommand appOrderDeleteCommand);


    List<TrackAppOrderCurtResponse> findAllAppOrders(@Validated TrackUserQuery trackUserQuery);
}
