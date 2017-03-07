package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by xinle on 3/6/17.
 */
public class Client {



    public void connet(int port, String host) throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture f = b.connect(host,port).sync();
        f.channel().closeFuture().sync();
        group.shutdownGracefully();
    }


    public static void main(String[] args) {
        try {
            new Client().connet(8083,"127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
