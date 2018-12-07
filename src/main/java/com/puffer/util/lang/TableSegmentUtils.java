package com.puffer.util.lang;

/**
 * 计算表id序号，用于分表策略
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2018下午4:38:36
 */
public class TableSegmentUtils {

	/**
	 * 计算分表落地序号
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午4:41:00
	 * @param tableSize
	 *            一共拆分多少表
	 * @param balanceId
	 *            分表源值
	 * @return 返回序号
	 */
	public static String getTableSegment(int tableSize, String balanceId) {
		String hashCode = String.valueOf(balanceId.hashCode());
		long segment = Math.abs(Long.parseLong(hashCode)) % tableSize;
		return segment + "";
	}

}
