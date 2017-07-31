package cn.bonoon.util;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Temporal;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ControllerUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static JsonFactory jsonFactory = new JsonFactory();

	public static String getJSONString(Map<?, ?> map) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = null;

		try {
			jsonString = objectMapper.writeValueAsString(map);
			System.out.println(jsonString);
			return jsonString;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	public static String getJSONString(List<?> list) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = null;

		try {
			jsonString = objectMapper.writeValueAsString(list);
			System.out.println(jsonString);
			return jsonString;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	public static String getJSONString(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = null;

		try {
			jsonString = objectMapper.writeValueAsString(object);
			System.out.println(jsonString);
			return jsonString;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * Object对象转json 2015年4月3日上午10:41:53 auther:shijing
	 * 
	 * @param pojo
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	public static String toJson(Object pojo) throws JsonMappingException,
			JsonGenerationException, IOException {
		return toJson(pojo, false);
	}

	public static String toJson(Object pojo, boolean prettyPrint)
			throws JsonMappingException, JsonGenerationException, IOException {
		StringWriter sw = new StringWriter();
		JsonGenerator jg = jsonFactory.createJsonGenerator(sw);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		objectMapper.writeValue(jg, pojo);
		return sw.toString();
	}

	/**
	 *  获取某个月最后的一天
	 * @param year
	 * @param month
	 * @return
	 */

	public static int getLastMonthDay(int year, int month) {
		if (year < 1 || month < 0 || month > 11)
			return -1;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, year);
		ca.set(Calendar.MONTH, month);
		//getActualMaximum()是获取给定日历字段的可能最大值
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		System.out.println("===============last:" + last);
		return ca.get(Calendar.DAY_OF_MONTH);
	}

	public static void main(String[] args) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
//		System.out.println(format.format(new Date()));
		ControllerUtil.getLastMonthDay(2016, 3);
	}
}
