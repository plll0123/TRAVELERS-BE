package com.travelers.admin.controller;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.service.NotifyService;
import com.travelers.dto.BoardRequest;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import com.travelers.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class AdminNotifyController {

    private final NotifyService notifyService;

    @GetMapping("/notice")
    public ResponseEntity<PagingCorrespondence.Response<NotifyResponse.SimpleInfo>> findNoticeAll(
            final PagingCorrespondence.Request pagingInfo
    ) {
        return ResponseEntity.ok(
                notifyService.showNotifies(NotifyType.NOTICE, pagingInfo)
        );
    }

    @GetMapping("/notice/{notifyId}")
    public ResponseEntity<NotifyResponse.DetailInfo> findOne(
            @PathVariable final Long notifyId
    ) {
        return ResponseEntity.ok(
                notifyService.showOne(notifyId, NotifyType.NOTICE)
        );
    }

    @PostMapping("/notice")
    public ResponseEntity<Void> create(
            @RequestBody final BoardRequest.Write write
    ) {
        Long memberId = getCurrentMemberId();
        notifyService.write(memberId, NotifyType.NOTICE, write);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{notifyId}")
    public ResponseEntity<Void> update(
            @PathVariable final Long notifyId,
            @RequestBody final BoardRequest.Write write
    ){
        notifyService.update(notifyId, write);
        return ResponseEntity.noContent().build();
    }

    private static Long getCurrentMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

}
