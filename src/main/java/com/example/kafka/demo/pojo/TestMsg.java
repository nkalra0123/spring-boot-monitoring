package com.example.kafka.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestMsg {
    private Integer id;

    public TestMsg(Integer id) {
        this.id = id;
    }
}
