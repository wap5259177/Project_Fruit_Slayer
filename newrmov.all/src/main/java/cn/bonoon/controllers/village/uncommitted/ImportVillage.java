//package cn.bonoon.controllers.village.uncommitted;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import cn.bonoon.core.plugins.PlaceService;
//import cn.bonoon.entities.plugins.PlaceEntity;
//
//public class ImportVillage {
//	private Map<String, PlaceEntity> map; 
//	
//	public void readVillage(PlaceService service) {
//		if(map == null)
//			map = readTown(service);
//		try {
//			StringBuffer sb = new StringBuffer();
//			String encoding = "UTF8";
//			File file = new File("d:\\desktop\\village.sql");
//			if (file.isFile() && file.exists()) { // 判断文件是否存在
//				InputStreamReader read = new InputStreamReader(
//						new FileInputStream(file), encoding);// 考虑到编码格式
//				BufferedReader bufferedReader = new BufferedReader(read);
//				String lineTxt = null;
//				Date now = new Date();
//				while ((lineTxt = bufferedReader.readLine()) != null) {
//					//System.out.println(lineTxt);
//					String[] fields = lineTxt.split(",");
//					String id = fields[0].substring(2, fields[0].length()-1);
//					String name = fields[1].substring(2, fields[1].length()-1);
//					String countyId = fields[2].substring(2, fields[2].length()-1);
//					String code = fields[3].substring(2, fields[3].length()-1);
//					PlaceEntity village = creatPlace(now, name, code,6);
//					PlaceEntity town = map.get(countyId);
//					if(town != null){
//						village.setShortCode(town.getShortCode());
//						village.setFullName(town.getFullName()+name);
//						village.setParent(town);
//						try{
//							service.update(village);
//						}catch(Exception ex){
//							ex.printStackTrace();
//						}
//					}else{
//						sb.append(lineTxt+" | ");
//					}
//				}
//				read.close();
//			} else {
//				System.out.println("找不到指定的文件");
//			}
//		} catch (Exception e) {
//			System.out.println("读取文件内容出错");
//			e.printStackTrace();
//		}
//	}
//
//	private PlaceEntity creatPlace(Date now, String name, String code, int level) {
//		PlaceEntity village = new PlaceEntity();
//		village.setId(Long.parseLong(code));
//		village.setCreateAt(now);
//		village.setCreatorId(1L);
//		village.setCreatorName("admin");
//		village.setDeleted(false);
//		village.setName(name);
//		village.setOwnerId(0);
//		village.setAbbreviationName("粤");
//		village.setCode(code);
//		village.setDisabled(false);
//		village.setLevel(level);
//		return village;
//	}
//
//	private Map<String,PlaceEntity> readTown(PlaceService service) {
//		Map<String,PlaceEntity> map = new HashMap<String,PlaceEntity>();
//		Map<String,PlaceEntity> counties = readCountries(service);
//		try {
//			String encoding = "UTF-8";
//			File file = new File("d:\\desktop\\town.sql");
//			Date now = new Date();
//			if (file.isFile() && file.exists()) { // 判断文件是否存在
//				InputStreamReader read = new InputStreamReader(
//						new FileInputStream(file), encoding);// 考虑到编码格式
//				BufferedReader bufferedReader = new BufferedReader(read);
//				String lineTxt = null;
//				StringBuffer sb = new StringBuffer();
//				while ((lineTxt = bufferedReader.readLine()) != null) {
//					//System.out.println(lineTxt);
//					try{
//					String[] fields = lineTxt.split(",");
//					String id = fields[0].substring(2, fields[0].length()-1);
//					String name = fields[1].substring(2, fields[1].length()-1);
//					String countyId = fields[2].substring(2, fields[2].length()-1);
//					String code = fields[3].substring(2, fields[3].length()-1);
//					PlaceEntity town = creatPlace(now , name, code,5);
//					town.setParent(counties.get(countyId));
//					service.update(town);
//					map.put(id, town);
//					
//					}catch(Exception e){
//						sb.append(lineTxt);
//						sb.append("|");
//					}
//				}
//				read.close();
//			} else {
//				System.out.println("找不到指定的文件");
//			}
//		} catch (Exception e) {
//			System.out.println("读取文件内容出错");
//			e.printStackTrace();
//		}
//		return map;
//	}
//	
//	private Map<String,PlaceEntity> readCountries(PlaceService service) {
//		Map<String,PlaceEntity> map = new HashMap<String,PlaceEntity>();
//		try {
//			String encoding = "UTF-8";
//			File file = new File("d:\\desktop\\country.sql");
//			if (file.isFile() && file.exists()) { // 判断文件是否存在
//				InputStreamReader read = new InputStreamReader(
//						new FileInputStream(file), encoding);// 考虑到编码格式
//				BufferedReader bufferedReader = new BufferedReader(read);
//				String lineTxt = null;
//				StringBuffer sb = new StringBuffer();
//				while ((lineTxt = bufferedReader.readLine()) != null) {
//					//System.out.println(lineTxt);
//					try{
//					String[] fields = lineTxt.split(",");
//					String id = fields[0].substring(2, fields[0].length()-1);
//					String code = fields[3].substring(2, fields[3].length()-1);
//					List<PlaceEntity> list = service.byCode(code);
//					PlaceEntity county = (PlaceEntity) list.get(0);
//					map.put(id, county);
//					}catch(Exception e){
//						sb.append(lineTxt);
//						sb.append("|");
//					}
//				}
//				read.close();
//			} else {
//				System.out.println("找不到指定的文件");
//			}
//		} catch (Exception e) {
//			System.out.println("读取文件内容出错");
//			e.printStackTrace();
//		}
//		return map;
//	}
//}
