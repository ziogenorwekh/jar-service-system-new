package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.delete.AppOrderDeleteCommand;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderBriefResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderQuery;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderResponse;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.apporder.service.application.handler.AppOrderCreateHandler;
import com.jar.service.system.apporder.service.application.handler.AppOrderDeleteHandler;
import com.jar.service.system.apporder.service.application.handler.AppOrderTrackQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppOrderApplicationServiceImpl implements AppOrderApplicationService {

    private final AppOrderCreateHandler appOrderCreateHandler;
    private final AppOrderTrackQueryHandler appOrderTrackQueryHandler;
    private final AppOrderDeleteHandler appOrderDeleteHandler;


    @Autowired
    public AppOrderApplicationServiceImpl(AppOrderCreateHandler appOrderCreateHandler,
                                          AppOrderTrackQueryHandler appOrderTrackQueryHandler,
                                          AppOrderDeleteHandler appOrderDeleteHandler) {
        this.appOrderCreateHandler = appOrderCreateHandler;
        this.appOrderTrackQueryHandler = appOrderTrackQueryHandler;
        this.appOrderDeleteHandler = appOrderDeleteHandler;
    }

    @Override
    public AppOrderCreateResponse createAppOrder(AppOrderCreateCommand appOrderCreateCommand) {
        return appOrderCreateHandler.saveAppOrder(appOrderCreateCommand);
    }

    @Override
    public TrackAppOrderResponse TrackQueryAppOrder(TrackAppOrderQuery trackAppOrderQuery) {
        return appOrderTrackQueryHandler.trackQueryAppOrderById(trackAppOrderQuery);
    }

    @Override
    public void deleteAppOrder(AppOrderDeleteCommand appOrderDeleteCommand) {
        appOrderDeleteHandler.deleteAppOrder(appOrderDeleteCommand);
    }

    @Override
    public List<TrackAppOrderBriefResponse> findAllAppOrders(TrackUserQuery trackUserQuery) {
        return appOrderTrackQueryHandler.trackQueryAppOrders(trackUserQuery);
    }
}
