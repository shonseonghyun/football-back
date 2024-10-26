package com.sunghyun.football.domain.noti.domain.enums;

import com.sunghyun.football.global.exception.ErrorCode;
import com.sunghyun.football.global.exception.exceptions.match.MatchStateNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum FreeSubType {
    MANAGER_FREE("M","Manager Free noti"),
    SUPER_SUB("S","SuperSub Free noti"),
    ALL_SUB("A","All Type Free noti");

    private final String type;
    private final String desc;

    public static FreeSubType getFreeSubType(String value){
        return Arrays.stream(FreeSubType.values())
                .filter(freeSubType -> freeSubType.getType().equals(value))
                .findFirst()
                .orElseThrow(()->new MatchStateNotFoundException(ErrorCode.MATCH_STATE_NOT_FOUND));
    }

}