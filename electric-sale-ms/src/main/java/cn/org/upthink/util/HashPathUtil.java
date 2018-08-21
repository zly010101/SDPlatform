/**
 * $Id: HashPathUtil.java Nov 18, 2014 11:03:41 AM hdp
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.org.upthink.util;

import cn.org.upthink.persistence.mybatis.util.StringUtils;

import java.util.UUID;

/**
 * 
 */
public class HashPathUtil {
	public static String getHashPath(String prefix, String uuid) {
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder("static/");
		StringBuilder tmp = new StringBuilder(Integer.toHexString(hashCode));
		int len = tmp.length();
		if (len != 8) {
			for (int i = 0; i < 8 - len; i++) {
				tmp.insert(0, "0");
			}
		}
		if(!StringUtils.isEmpty(prefix)){
			sb.append(prefix).append("/");
		}
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}

	public static String getHashPath(String prefix) {
		String uuid = UUID.randomUUID().toString().replace("\\-", "");
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder("static/");
		StringBuilder tmp = new StringBuilder(Integer.toHexString(hashCode));
		int len = tmp.length();
		if (len != 8) {
			for (int i = 0; i < 8 - len; i++) {
				tmp.insert(0, "0");
			}
		}
		if(!StringUtils.isEmpty(prefix)){
			sb.append(prefix).append("/");
		}
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}
	
	public static String getHashPath() {
		String uuid = UUID.randomUUID().toString().replace("\\-", "");
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder("static/");
		StringBuilder tmp = new StringBuilder(Integer.toHexString(hashCode));
		int len = tmp.length();
		if (len != 8) {
			for (int i = 0; i < 8 - len; i++) {
				tmp.insert(0, "0");
			}
		}
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String domain = "http://tangguoyuan.cn";
		String uuid = UUID.randomUUID().toString().replaceAll("\\-", "");
		String url = domain+ "/" + getHashPath() + uuid + ".jpg";
		System.out.println("<<<" + domain);
		System.out.println("<<<" + getHashPath());
		System.out.println("<<<" + uuid);
		System.out.println(url);
		System.out.println("<<<" + getHashPath("images"));
	}

}
