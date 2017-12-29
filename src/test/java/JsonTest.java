import com.alibaba.fastjson.JSONObject;
import com.chen.rpc.rest.client.result.DefaultRpcResult;
import com.chen.rpc.rest.client.result.RpcResult;
import com.chen.rpc.rest.client.utl.Util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 *         2017/11/13 19:00
 */
public class JsonTest {
    public static void main(String[] args) throws Exception {
        PersonInfo p = new PersonInfo();
        p.setName("wodiu");
        DefaultRpcResult<List<PersonInfo>> result = new DefaultRpcResult<>(Collections.singletonList(p));
        result.setResult(RpcResult.EXCEPTION);
        String s = JSONObject.toJSONString(result);
        DefaultRpcResult<List<PersonInfo>> eee = Util.parser(s, JsonTest.class.getMethod("m").getGenericReturnType());
        System.out.println(eee.getValue().get(0).getName());

        Map<String, PersonInfo> xxx = new HashMap<>();
        xxx.put("1", p);
        String ss = JSONObject.toJSONString(new DefaultRpcResult<>(xxx));
        DefaultRpcResult<Map<String, PersonInfo>> ddd = Util.parser(ss, JsonTest.class.getMethod("s").getGenericReturnType());
        System.out.println(ddd.getValue().get("1").getName());
    }

    public static DefaultRpcResult<Map<String,PersonInfo>> s() {
        return null;
    }

    public static DefaultRpcResult<List<PersonInfo>> m() {
        return null;
    }
}
