package com.ibingbo.netty.app.msgpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

/**
 * App
 *
 * @author zhangbingbing
 * @date 18/1/26
 */
public class App {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(list);

        List<String> dst = messagePack.read(raw, Templates.tList(Templates.TString));
        System.out.println(dst);
    }
}
