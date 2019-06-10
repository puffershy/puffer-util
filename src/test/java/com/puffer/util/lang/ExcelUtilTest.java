package com.puffer.util.lang;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelUtilTest {

    @Test
    public void testReadFile() throws IOException {
        String path = "E:\\document\\pay\\生产\\支付数据.xlsx";

        int skipLine = 1;

        String selectSql = "select * from yyfax_pay.fuiou_charge where rsp_code = %s and rsp_msg = '%s' and state = '%s' and order_id = '%s' limit 1;";
        String sql = "update yyfax_pay.fuiou_charge set state = '1',rsp_code = 0 ,rsp_msg = '成功', remark = '手动修复' "
                + "where rsp_code = %s and rsp_msg = '%s' and state = '%s' and order_id = '%s' limit 1; ";

        String selectNULLSql = "select * from yyfax_pay.fuiou_charge where rsp_code is NULL and rsp_msg is NULL and state = '%s' and order_id = '%s' limit 1;";
        String sqlNULL = "update yyfax_pay.fuiou_charge set state = '1',rsp_code = 0 ,rsp_msg = '成功', remark = '手动修复' "
                + "where rsp_code is NULL and rsp_msg is NULL and state = '%s' and order_id = '%s' limit 1; ";

        List<Object[]> lines = ExcelUtil.readFile(path, skipLine);

        Set<String> orderSet = new HashSet<>();

        lines.forEach(strs -> {

            String select = "";
            String format = "";
            String rspCode = String.valueOf(strs[5]);
            rspCode = "NULL".equals(rspCode) ? rspCode : rspCode.substring(0, rspCode.lastIndexOf("."));
            String rspMsg = String.valueOf(strs[6]);
            String state = String.valueOf(strs[2]);
            state = state.substring(0, state.lastIndexOf("."));
            String orderId = String.valueOf(strs[0]);

            if (orderSet.contains(orderId)) {
                return;
            }

            orderSet.add(orderId);

            if ("NULL".equals(rspCode)) {

                select = String.format(selectNULLSql, state, orderId);
                format = String.format(sqlNULL, state, orderId);

            } else {
                select = String.format(selectSql, rspCode, rspMsg, state, orderId);
                format = String.format(sql, rspCode, rspMsg, state, orderId);

            }

            System.out.println(select);
            System.out.println(format);

        });

        System.out.println(orderSet.size());
    }
}