package com.zj.netty.server.handler;

import com.zj.netty.utils.HexStringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class ServerHandler extends ChannelHandlerAdapter {

    private static Logger LOG = Logger.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        if (buf.readableBytes() <= 0) {
            // ReferenceCountUtil.safeRelease(msg);
            return;
        }
        byte[] bs = new byte[buf.readableBytes()];

        buf.readBytes(bs);
        String str = HexStringUtils.toHexStringFormat(bs);
        LOG.info("收到的消息：" + str);
    }
}
