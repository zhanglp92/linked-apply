package com.github.zhanglp92.lomnok;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * For learning lombok.
 */
@Builder(toBuilder = true)
@Data
public class Message {

    private Long id;

    private String msg;

    @NonNull
    private Date sendTime;

    private boolean action;
}
