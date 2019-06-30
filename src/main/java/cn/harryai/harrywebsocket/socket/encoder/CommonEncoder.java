package cn.harryai.harrywebsocket.socket.encoder;

import com.alibaba.fastjson.JSON;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author Harry
 * @since 2019/6/30 10:39
 */
public class CommonEncoder implements Encoder.Text<Object> {
    @Override
    public String encode(Object object) throws EncodeException {
        return JSON.toJSONString(object);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
