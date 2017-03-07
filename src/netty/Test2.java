package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xinle on 3/6/17.
 */
public class Test2  {


    private void test() throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024).childHandler(new ChildChannelHandler());
            ChannelFuture f = b.bind(8083).sync();
            f.channel().closeFuture().sync();
        }finally {

            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }




    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }
    public static void main(String[] args) {
        try {
            new Test2().test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }









}
