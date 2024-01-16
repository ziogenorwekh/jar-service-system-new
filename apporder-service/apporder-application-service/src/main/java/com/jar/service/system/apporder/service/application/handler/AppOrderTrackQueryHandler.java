package com.jar.service.system.apporder.service.application.handler;

import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderQuery;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderBriefResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.apporder.service.application.exception.AppOrderNotOwnerException;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.application.exception.AppOrderNotFoundException;
import com.jar.service.system.apporder.service.application.mapper.AppOrderDataMapper;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppOrderTrackQueryHandler {

    private final AppOrderRepository appOrderRepository;
    private final AppOrderDataMapper appOrderDataMapper;

    public AppOrderTrackQueryHandler(AppOrderRepository appOrderRepository,
                                     AppOrderDataMapper appOrderDataMapper) {
        this.appOrderRepository = appOrderRepository;
        this.appOrderDataMapper = appOrderDataMapper;
    }

    @Transactional(readOnly = true)
    public TrackAppOrderResponse trackQueryAppOrderById(TrackAppOrderQuery trackAppOrderQuery) {
        AppOrder appOrder = appOrderRepository
                .findByAppOrderId(new AppOrderId(trackAppOrderQuery.getAppOrderId()))
                .orElseThrow(() -> new AppOrderNotFoundException(String.format(
                        "appOrder is not found by id : %s ", trackAppOrderQuery.getAppOrderId())));
        log.trace("appOrder userId {}-> ", appOrder.getUserId());
        log.trace("trackQuery userId {} -> ", appOrder.getUserId());
        if (!appOrder.getUserId().getValue().equals(trackAppOrderQuery.getUserId())) {
            throw new AppOrderNotOwnerException("this appOrder is not your application.");
        }
        return appOrderDataMapper.convertAppOrderToTrackAppOrderResponse(appOrder);
    }

    @Transactional(readOnly = true)
    public List<TrackAppOrderBriefResponse> trackQueryAppOrders(TrackUserQuery trackUserQuery) {
        List<AppOrder> appOrders = appOrderRepository
                .findAllByUserId(appOrderDataMapper.convertTrackUserQueryToUser(trackUserQuery));
        return appOrders.stream()
                .map(appOrderDataMapper::convertAppOrderToTrackAppOrderBriefResponse)
                .collect(Collectors.toList());

    }


}
