package com.example.twiter.dto.Request;

import com.example.twiter.entity.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class MemberInfoRequestDto {

    private String memberName;
    private Date DOB;
}
