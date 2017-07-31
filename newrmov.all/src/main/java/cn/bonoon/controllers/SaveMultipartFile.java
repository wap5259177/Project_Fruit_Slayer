package cn.bonoon.controllers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import cn.bonoon.kernel.events.OperateEvent;

public final class SaveMultipartFile {

	public static String saveFile(OperateEvent event) {
		DefaultMultipartHttpServletRequest rq = (DefaultMultipartHttpServletRequest)event.getRequest();
//		String _rpath = event.getRequest().getSession().getServletContext().getRealPath("/upload") + "\\"; //
//		String _path = event.getRequest().getContextPath() + "/upload/";//
		
		String ss = "/upload";
		String ph = event.getRequest().getSession().getServletContext().getRealPath(ss);
		String rpath = ph + File.separator; //
		String path = event.getRequest().getContextPath() + File.separator + "upload"+File.separator;//
		
		
		
		// 最大文件大小
		long maxSize = 10485760;// 10M
		// 检查目录
		File uploadDir = new File(rpath);
		if (!uploadDir.isDirectory()) {
			throw new RuntimeException("上传目录不存在。");
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			throw new RuntimeException("上传目录没有写权限。");
		}
		
		MultipartFile multipartFile = rq.getFile("enclosure-file");
	
		if (multipartFile.getSize() > maxSize) {
			throw new RuntimeException("上传文件大小超过 10M 限制。");
		}
		String newFileName = null;
		if (multipartFile.getSize() != 0 ) {
			String fileName = multipartFile.getOriginalFilename();
			String fileExt = check(fileName);
			newFileName = generateFileName(fileExt);
			
			try {
				File uploadedFile = new File(rpath, newFileName);
				multipartFile.transferTo(uploadedFile);
				newFileName = path + newFileName;
			} catch (Exception e) {
				throw new RuntimeException("上传文件失败。");
			}
		}
		
		if(newFileName == null){
			String deletedFile = rq.getParameter("enclosure-deleted");
			if(deletedFile != null){//if("true".equals(deletedFile))
				newFileName = "";
			}
		}
		
		return newFileName;
	}
	
	/**
	 * 检查文件的后缀名是否合法
	 * 
	 * @param fileName
	 * @return
	 */
	private static String check(String fileName) {
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		boolean pass = false;
		for (int i = 0; i < EXT_MAP.length; i++) {
			if(fileExt.equals(EXT_MAP[i])){
				pass = true;
				break;
			}
 		}
		
		if (!pass) {
			throw new RuntimeException("上传文件扩展名是不允许的扩展名。");
		}
		return fileExt;
	}

	// 文件类型集合
	static String[] EXT_MAP = {"doc","docx","jpg","bmp","png","rar","zip"};

	// 服务器中的上传文件存放的地址
	private static String generateFileName(String fileExt) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + "." + fileExt;
		return newFileName;
	}
	
}
