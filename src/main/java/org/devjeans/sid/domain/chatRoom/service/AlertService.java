package org.devjeans.sid.domain.chatRoom.service;

import lombok.RequiredArgsConstructor;
import org.devjeans.sid.domain.chatRoom.dto.AlertResponse;
import org.devjeans.sid.domain.chatRoom.entity.Alert;
import org.devjeans.sid.domain.chatRoom.repository.AlertRepository;
import org.devjeans.sid.global.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlertService {
    private final AlertRepository alertRepository;
    private final SecurityUtil securityUtil;

    // 안읽은거 모두 가져오기
    public List<AlertResponse> findAllUnread() {
        Long memberId = securityUtil.getCurrentMemberId();

        List<Alert> alerts = alertRepository.findAllByMemberIdAndIsRead(memberId, "N");
        return alerts.stream()
                .map(AlertResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
