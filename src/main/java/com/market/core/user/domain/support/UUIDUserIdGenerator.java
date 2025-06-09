package com.market.core.user.domain.support;

import com.market.core.user.domain.UserId;
import com.market.core.user.domain.UserIdGenerator;
import org.springframework.stereotype.Component;

/**
 * UUID 유저(User) 일련번호 생성기
 *
 * @author chan
 */
@Component
public class UUIDUserIdGenerator implements UserIdGenerator {

    @Override
    public UserId generatorId() {
        return null;
    }
}
