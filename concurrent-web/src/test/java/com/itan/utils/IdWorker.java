package com.itan.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  id 生成器
 *  000 -000 -000000000000
 * @author ye.yanbin
 * @date 2019-08-31
 */
public class IdWorker {

	private static IdWorker WORKER = new IdWorker();

	private long sequence = 0L;

	private long lastTimestamp = -1L;

	/** 序列在id中占的位数 */
	private final long sequenceBits = 12L;

	/** 机器id所占的位数 */
	private final long workerIdBits = 3L;

	/** 数据标识id所占的位数 */
	private final long dataCenterIdBits = 3L;

	/** 机器ID向左移12位 */
	private final long workerIdShift = sequenceBits;

	/** 数据标识id向左移15位(12+3) */
	private final long dataCenterIdShift = sequenceBits + workerIdBits;

	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);
	/** 支持的最大机器id，结果是7  */
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	/** 支持的最大数据标识id，结果是7 */
	private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
	/** 工作机器ID(0~7) */
	private long workerId = 0L;
	/** 数据中心ID(0~7) */
	private long dataCenterId = 0L;

	private IdWorker() {
		workerId = getWorkId();
		dataCenterId = getDataCenterId();
	}


	private long getDataCenterId(){
		int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
		int sums = 0;
		if (ints != null) {
			for (int i : ints) {
				sums += i;
			}
			return (sums % maxDataCenterId);
		}else {
			return ThreadLocalRandom.current().nextLong(0, maxDataCenterId);
		}
	}

	private long getWorkId() {
		try {
			String hostAddress = Inet4Address.getLocalHost().getHostAddress();
			int[] ints = StringUtils.toCodePoints(hostAddress);
			if (ints != null) {
				int sums = 0;
				for (int b : ints) {
					sums += b;
				}
				return (sums % maxWorkerId);
			}else {
				return ThreadLocalRandom.current().nextLong(0, maxWorkerId);
			}
		} catch (UnknownHostException e) {
			// 如果获取失败，则使用随机数备用
			return ThreadLocalRandom.current().nextLong(0, maxWorkerId);
		}
	}

	public static String getIdStr() {
		long id = WORKER.nextId();
		return WORKER.getDateTimeNow() + id;
	}

	public  String getDateTimeNow() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		Date nowTime = new Date(lastTimestamp);
		LocalDateTime localDateTime = TimeUtils.DateToLDT(nowTime);
		return localDateTime.format(formatter);
	}

	protected long tilNextMillis(long lastTimestamp) {
		long timestamp;
		for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
		}

		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis();
	}

	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (timestamp < this.lastTimestamp) {
			long offset = this.lastTimestamp - timestamp;
			if (offset > 5L) {
				throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
			}

			try {
				this.wait(offset << 1);
				timestamp = this.timeGen();
				if (timestamp < this.lastTimestamp) {
					throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
				}
			} catch (Exception var6) {
				throw new RuntimeException(var6);
			}
		}

		if (this.lastTimestamp == timestamp) {
			this.sequence = this.sequence + 1L & sequenceMask;
			if (this.sequence == 0L) {
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = ThreadLocalRandom.current().nextLong(1L,sequenceMask>>1);
		}
		this.lastTimestamp = timestamp;
		return  dataCenterId << dataCenterIdShift | workerId << workerIdShift | this.sequence;
	}

}
