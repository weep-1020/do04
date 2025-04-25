package cn.md.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PackJsn {
    static Logger logger = LoggerFactory.getLogger(PackJsn.class);
    static ObjectMapper objectMapper = new ObjectMapper();

    //控制层每个方法， 统一使用的返回数据打包格式， 由 @ResponseBody 转化为 JSON
    public static XJson pack(int code, String msg, Object data) {
        return new XJson(code, msg, data);
    }

    /**
     * 将给定的状态码、消息和数据打包成一个JSON字符串
     * 此方法主要用于将API响应的所有部分（状态码、消息和数据）封装到一个JSON对象中，
     * 以便在API响应时使用该JSON字符串
     *
     * @param code 状态码，表示API响应的状态
     * @param msg 消息，描述API响应的更多信息
     * @param data 数据，包含API响应的具体数据
     * @return 包含状态码、消息和数据的JSON字符串
     */
    public static String packM(int code, String msg, Object data) {
        // 创建一个Map对象来存储响应的所有部分
        Map<String, Object> m = new HashMap<>();
        // 将状态码、消息和数据放入Map中
        m.put("code", code);
        m.put("msg", msg);
        m.put("data", data);
        // 把 m  转 JSON 字符串
        String json = "";
        try {
            // 使用ObjectMapper将Map对象转换为JSON字符串
            json = objectMapper.writeValueAsString(m);
        } catch (Exception e) {
            // 如果转换过程中发生异常，则记录错误日志
            logger.error(e.getMessage());
        }
        // 记录调试日志，显示生成的JSON字符串
        logger.debug("JSON=" + json);
        // 返回生成的JSON字符串
        return json;
    }


    //拼接 JSON 对象（字符串）
    private String pack2(int code, String msg, String data) {
        String strJson = "{" +
                "\"" + "code" + "\"" + ":" + code + "," +
                "\"" + "msg" + "\"" + ":" + "\"" + msg + "\"" + "," +
                "\"" + "data" + "\"" + ":" + "\"" + data + "\"" +
                "}";
        return strJson;
    }
}
