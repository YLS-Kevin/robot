package com.yls.projects.robot.utils;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @date 2018年1月10日下午5:06:38
 * @author Alex Lee 李璐
 * @version 2018-01
 * @since 1.8
 */
public class GsonAdatper {

	// 自定义Strig适配器
	public static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
		public String read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return "";
			}
			return reader.nextString();
		}

		public void write(JsonWriter writer, String value) throws IOException {
			if (value == null) {
				// 在这里处理null改为空字符串
				writer.value("");
				return;
			}
			writer.value(value);
		}
	};
}
