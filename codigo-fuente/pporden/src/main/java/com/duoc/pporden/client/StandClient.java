package com.duoc.pporden.client;

import com.duoc.pporden.dto.StandDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "stand-client",
        url = "${stand.service.url}"
)
public interface StandClient {

    @GetMapping("/api/v2/stands")
    StandDTO getStandById(@PathVariable("id") Long id);
}