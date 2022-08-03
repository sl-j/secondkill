package com.lei.secondkill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 秒杀信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMsg {

    private TUser user;

    private Long goodId;
}
