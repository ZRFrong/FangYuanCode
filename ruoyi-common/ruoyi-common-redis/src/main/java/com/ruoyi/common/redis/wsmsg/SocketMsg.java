
package com.ruoyi.common.redis.wsmsg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketMsg {
	private String msg;
	private String msgType;
	private String userId;
}
