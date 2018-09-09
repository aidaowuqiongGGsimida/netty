package com.zj.netty.client.handler;

import com.alibaba.fastjson.JSON;
import com.zj.netty.entity.MsgHeader;
import com.zj.netty.entity.PackageData;
import com.zj.netty.entity.PrimaryLinkLoginReplyMsg;
import com.zj.netty.service.codec.MsgDecoder;
import com.zj.netty.utils.HexStringUtils;
import com.zj.netty.utils.MsgIdConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class ClientHandler extends ChannelHandlerAdapter {

    private static Logger LOG = Logger.getLogger(ClientHandler.class);

    private ChannelHandlerContext ctx;


    /**
     * 启动客户端控制台
     */
    private void session() throws IOException {
        new Thread() {
            public void run() {
                LOG.info("你好，请在控制台输入消息内容");
                Scanner scanner = new Scanner(System.in);
                String message = null;
                do {
                    if (scanner.hasNext()) {
                        String input = scanner.nextLine();

                        if ("exit".equals(input)) {

                            message = "exitMsg";

                        } else if ("login".equals(input)) {
                            message = "loginMsg";
                        } else {
                            message = input;
                        }
                    }
                }
                while (sendMsg(message));
                scanner.close();
            }
        }.start();
    }

    /**
     * tcp链路建立成功后调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

        LOG.info("成功连接服务器,已执行登录动作");
        session();
    }

    /**
     * 发送消息
     *
     * @param msg
     * @return
     * @throws IOException
     */
    private boolean sendMsg(String msg) {
        try {
            byte[] bs = HexStringUtils.hexStr2Bytes(msg);
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(bs)).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("已发送至聊天面板,请继续输入");
        return true;
    }

    /**
     * 收到消息后调用
     *
     * @throws IOException
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        ByteBuf buf = (ByteBuf) msg;
        if (buf.readableBytes() <= 0) {
            return;
        }
        byte[] bs = new byte[buf.readableBytes()];

        buf.readBytes(bs);
        processMsg(ctx, bs);

    }

    /**
     * 处理消息
     * @param ctx
     * @param bs
     */
    private void processMsg(ChannelHandlerContext ctx, byte[] bs) {
        String str = HexStringUtils.toHexStringFormat(bs);
        LOG.info("接收到服务器发送的消息:" + str);
        MsgDecoder msgDecoder = new MsgDecoder();
        try {
            PackageData packageData = msgDecoder.getPackageData(bs);
            MsgHeader msgHeader = packageData.getMsgHeader();
            LOG.info(JSON.toJSONString(msgHeader));
            int msgId = msgHeader.getMsgId();
            System.out.println("msgId: 0x" + Integer.toHexString(msgId));
            switch (msgId) {
                case MsgIdConstants.LOGIN_REPLY_MSG_ID:
                    //登录应答
                    PrimaryLinkLoginReplyMsg decode = msgDecoder.decodeEntityFromBytes(packageData.getBodyData(), PrimaryLinkLoginReplyMsg.class);
                    System.out.println(JSON.toJSONString(decode));
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 发生异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.info("与服务器断开连接:" + cause.getMessage());
        ctx.close();
    }


}
