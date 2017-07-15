package com.ibingbo.netty.app.common;

import java.util.Random;

/**
 * Created by bing on 17/6/13.
 */
public class MemcachedRequest {
    private static final Random rand = new Random();
    private final int magic = 0x80;
    private final byte opCode;
    private final String key;
    private final int flags = 0xdeadbeef;
    private final int expires;
    private final String body;
    private final int id=rand.nextInt();
    private final long cas = 0;
    private final boolean hasExtras;

    public MemcachedRequest(byte opCode, String key, String body) {
        this.opCode = opCode;
        this.key = key;
        this.body = body == null ? "" : body;
        this.expires = 0;
        hasExtras = opCode == Opcode.SET;
    }

    public MemcachedRequest(byte opCode, String key) {
        this(opCode, key, null);
    }

    public int magic() {
        return this.magic;
    }

    public int opCode() {
        return this.opCode;
    }

    public String key() {
        return this.key;
    }

    public int flags() {
        return this.flags;
    }

    public int expires() {
        return this.expires;
    }

    public String body() {
        return this.body;
    }

    public int id() {
        return this.id;
    }

    public long cas() {
        return this.cas;
    }

    public boolean hasExtras() {
        return this.hasExtras;
    }
}
