package com.paopao.demo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.ByteBuffer;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/12/22
 */
@SpringBootTest
public class NettyTest {

    @Test
    public void byteBufferTest() {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        this.split(buffer);
        buffer.put("w are you?\n".getBytes());
        this.split(buffer);
    }

    private void split(ByteBuffer source) {
        // 读模式
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source .get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                System.out.println(new String(target.array()));
            }
        }
        source.compact();
    }
}
